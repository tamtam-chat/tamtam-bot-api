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

package chat.tamtam.botapi.queries.upload;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Future;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.TransportClientException;
import chat.tamtam.botapi.queries.TamTamQuery;

/**
 * @author alexandrchuprin
 */
public abstract class TamTamUploadQuery<T> extends TamTamQuery<T> {
    private final TamTamClient tamTamClient;
    private final String url;
    private final UploadExec uploadExec;

    public TamTamUploadQuery(TamTamClient tamTamClient, Class<T> responseType, String url, File file) {
        super(tamTamClient, url, responseType);
        this.tamTamClient = tamTamClient;
        this.url = url;
        this.uploadExec = new FileUploadExec(file);
    }

    public TamTamUploadQuery(TamTamClient tamTamClient, Class<T> responseType, String url, String fileName,
                             InputStream input) {
        super(tamTamClient, url, responseType);
        this.tamTamClient = tamTamClient;
        this.url = url;
        this.uploadExec = new StreamUploadExec(fileName, input);
    }

    @Override
    protected Future<ClientResponse> call() throws ClientException {
        try {
            return uploadExec.call();
        } catch (InterruptedException e) {
            throw new ClientException(e);
        }
    }

    interface UploadExec {
        Future<ClientResponse> call() throws ClientException, InterruptedException;
    }

    private class FileUploadExec implements UploadExec {
        private final File file;

        private FileUploadExec(File file) {
            this.file = file;
        }

        @Override
        public Future<ClientResponse> call() throws ClientException, InterruptedException {
            try {
                return tamTamClient.getTransport().post(url, file);
            } catch (TransportClientException e) {
                throw new ClientException(e);
            }
        }
    }

    private class StreamUploadExec implements UploadExec {
        private final String fileName;
        private final InputStream input;

        StreamUploadExec(String fileName, InputStream input) {
            this.fileName = fileName;
            this.input = input;
        }

        @Override
        public Future<ClientResponse> call() throws ClientException {
            try {
                return tamTamClient.getTransport().post(url, fileName, input);
            } catch (TransportClientException e) {
                throw new ClientException(e);
            }
        }
    }
}
