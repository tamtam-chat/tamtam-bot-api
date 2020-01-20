/*
 * ------------------------------------------------------------------------
 * TamTam chat Bot API
 * ------------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ------------------------------------------------------------------------
 */

package chat.tamtam.botapi.client.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.TransportClientException;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author alexandrchuprin
 */
public class OkHttpTransportClient implements TamTamTransportClient {
    private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final RequestBody NO_REQUEST_BODY = RequestBody.create(null, new byte[0]);
    private static final String USER_AGENT = "TamTam Java Client/0.0.1";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType BINARY_CONTENT_TYPE = MediaType.parse("application/octet-stream");
    private static final int FOUR_KB = 4 * 1024;
    private static final int TWO_MB = 2_048_000;

    private final OkHttpClient httpClient;

    public OkHttpTransportClient() {
        this(new OkHttpClient.Builder()
                .addInterceptor(new AddUserAgent())
                .addInterceptor(new LoggingInterceptor())
                .readTimeout(100, TimeUnit.SECONDS)
                .callTimeout(100, TimeUnit.SECONDS)
                .followRedirects(true)
                .build());
    }

    public OkHttpTransportClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Future<ClientResponse> get(String url) {
        Request request = new Request.Builder().url(url).build();
        return newCall(request);
    }

    @Override
    public Future<ClientResponse> post(String url, @Nullable byte[] body) {
        return newCall(new Request.Builder().url(url).post(wrapBody(body)).build());
    }

    @Override
    public Future<ClientResponse> post(String url, File file) throws TransportClientException, InterruptedException {
        Objects.requireNonNull(url, "Filename must not be null");
        Objects.requireNonNull(file, "inputStream must not be null");
        LOG.info("Started uploading to url {}", url);

        String filename = file.getName();
        long total = file.length();
        Future<ClientResponse> response = null;

        MediaType mediaType;
        try {
            mediaType = guessMediaType(file);
        } catch (IOException e) {
            throw new TransportClientException("Failed to guess content type of file", e);
        }

        try (BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[TWO_MB];
            int read;
            long pos = -1;
            while ((read = fileInput.read(buffer)) != -1) {
                RequestBody body = RequestBody.create(mediaType, buffer, 0, read);
                String range = String.format("bytes %d-%d/%d", pos + 1, pos = pos + read, total);
                Request request = new Request.Builder()
                        .header("Content-Range", range)
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Content-Disposition", "attachment; filename=" + filename)
                        .url(url)
                        .post(body)
                        .build();

                response = newCall(request);
                // server does not support parallel upload so we should block here
                response.get();
            }
        } catch (IOException | ExecutionException e) {
            throw new TransportClientException(e);
        } finally {
            LOG.info("Finished uploading to url {}", url);
        }

        if (response == null) {
            throw new TransportClientException("File is empty");
        }

        return response;
    }

    @Override
    public Future<ClientResponse> post(String url, String filename, InputStream inputStream) throws
            TransportClientException {
        Objects.requireNonNull(filename, "Filename must not be null");
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[FOUR_KB];
            int n;
            while ((n = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, n);
            }

            byte[] data = out.toByteArray();
            MultipartBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("v1", filename, RequestBody.create(BINARY_CONTENT_TYPE, data))
                    .build();

            Request request = new Request.Builder().url(url).post(body).build();
            return newCall(request);
        } catch (IOException e) {
            throw new TransportClientException("Failed to execute POST request", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOG.error("Failed to close resource {}", filename, e);
            }
        }
    }

    @Override
    public Future<ClientResponse> put(String url, @Nullable byte[] requestBody) {
        return newCall(new Request.Builder().url(url).put(wrapBody(requestBody)).build());
    }

    @Override
    public Future<ClientResponse> delete(String url) {
        return newCall(new Request.Builder().url(url).delete().build());
    }

    @Override
    public Future<ClientResponse> patch(String url, @Nullable byte[] requestBody) {
        return newCall(new Request.Builder().url(url).patch(wrapBody(requestBody)).build());
    }

    @Override
    public void close() throws IOException {
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
        Cache cache = httpClient.cache();
        if (cache != null) {
            cache.close();
        }
    }

    private static MediaType guessMediaType(File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        if (contentType == null) {
            return BINARY_CONTENT_TYPE;
        }

        return MediaType.parse(contentType);
    }

    private static RequestBody wrapBody(@Nullable byte[] requestBody) {
        return requestBody == null ? NO_REQUEST_BODY : RequestBody.create(JSON, requestBody);
    }

    private static ClientResponse toClientResponse(Response response) throws IOException {
        int statusCode = response.code();
        byte[] body = response.body() == null ? null : response.body().bytes();
        Map<String, String> headers = new HashMap<>();
        for (int i = 0; i < response.headers().size(); i++) {
            String name = response.headers().name(i);
            String value = response.headers().value(i);
            headers.put(name, value);
        }

        return new ClientResponse(statusCode, body, headers);
    }

    private Future<ClientResponse> newCall(Request request) {
        CallbackFuture future = new CallbackFuture();
        httpClient.newCall(request).enqueue(future);
        return future;
    }

    private static class AddUserAgent implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("User-Agent", USER_AGENT)
                    .build();

            return chain.proceed(request);
        }
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Executing request: {}", request);
            }

            return chain.proceed(request);
        }
    }

    private static class CallbackFuture extends CompletableFuture<ClientResponse> implements Callback {
        public void onResponse(Call call, Response response) {
            try {
                super.complete(toClientResponse(response));
            } catch (IOException e) {
                onFailure(call, e);
            }
        }

        public void onFailure(Call call, IOException e) {
            super.completeExceptionally(new TransportClientException(e));
        }
    }
}
