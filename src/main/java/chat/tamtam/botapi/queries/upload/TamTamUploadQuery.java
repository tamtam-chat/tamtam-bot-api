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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.queries.TamTamQuery;

/**
 * @author alexandrchuprin
 */
public abstract class TamTamUploadQuery<T> extends TamTamQuery<T> {
    private final UploadExec uploadExec;
    private final TamTamClient tamTamClient;

    public TamTamUploadQuery(TamTamClient tamTamClient, Class<T> responseType, String url, File file) {
        super(tamTamClient, url, responseType);
        this.tamTamClient = tamTamClient;
        this.uploadExec = new FileUploadExec(url, file);
    }

    public TamTamUploadQuery(TamTamClient tamTamClient, Class<T> responseType, String url, String fileName,
                             InputStream input) {
        super(tamTamClient, url, responseType);
        this.tamTamClient = tamTamClient;
        this.uploadExec = new StreamUploadExec(url, fileName, input);
    }

    public UploadExec getUploadExec() {
        return uploadExec;
    }

    @Override
    public T execute() throws APIException, ClientException {
        try {
            return tamTamClient.newCall(this).get();
        } catch (InterruptedException e) {
            throw new ClientException("Current request was interrupted", e);
        } catch (ExecutionException e) {
            return unwrap(e);
        }
    }

    public Future<T> enqueue() throws ClientException {
        return tamTamClient.newCall(this);
    }
}
