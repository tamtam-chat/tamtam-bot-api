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

package chat.tamtam.botapi.client;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Future;

import org.jetbrains.annotations.Nullable;

import chat.tamtam.botapi.exceptions.TransportClientException;

/**
 * @author alexandrchuprin
 */
public interface TamTamTransportClient {
    Future<ClientResponse> get(String url) throws TransportClientException;

    Future<ClientResponse> post(String url, @Nullable byte[] body) throws TransportClientException;

    Future<ClientResponse> post(String url, String filename, InputStream inputStream) throws TransportClientException;

    Future<ClientResponse> put(String url, @Nullable byte[] requestBody) throws TransportClientException;

    Future<ClientResponse> delete(String url) throws TransportClientException;

    Future<ClientResponse> patch(String url, @Nullable byte[] requestBody) throws TransportClientException;
}
