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

package chat.tamtam.botapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.queries.upload.TamTamUploadAVQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadFileQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadImageQuery;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadAPI {
    private final TamTamClient client;

    public TamTamUploadAPI(TamTamClient client) {
        this.client = client;
    }

    public TamTamUploadFileQuery uploadFile(String url, File file) throws FileNotFoundException {
        return new TamTamUploadFileQuery(client, url, file);
    }

    public TamTamUploadFileQuery uploadFile(String url, String fileName, InputStream inputStream) {
        return new TamTamUploadFileQuery(client, url, fileName, inputStream);
    }

    public TamTamUploadImageQuery uploadImage(String url, File file) throws FileNotFoundException {
        return new TamTamUploadImageQuery(client, url, file.getName(), new FileInputStream(file));
    }

    public TamTamUploadImageQuery uploadImage(String url, String fileName, InputStream inputStream) {
        return new TamTamUploadImageQuery(client, url, fileName, inputStream);
    }

    public TamTamUploadAVQuery uploadAV(String url, File file) {
        return new TamTamUploadAVQuery(client, url, file);
    }

    public TamTamUploadAVQuery uploadAV(String url, String fileName, InputStream inputStream) {
        return new TamTamUploadAVQuery(client, url, fileName, inputStream);
    }
}
