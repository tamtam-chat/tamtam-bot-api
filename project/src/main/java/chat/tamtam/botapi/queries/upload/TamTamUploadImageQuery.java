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
import java.io.FileNotFoundException;
import java.io.InputStream;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.model.PhotoTokens;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadImageQuery extends TamTamUploadQuery<PhotoTokens> {
    public TamTamUploadImageQuery(TamTamClient tamTamClient, String url, File file) throws FileNotFoundException {
        super(tamTamClient, PhotoTokens.class, url, file);
    }

    public TamTamUploadImageQuery(TamTamClient tamTamClient, String url, String fileName, InputStream input) {
        super(tamTamClient, PhotoTokens.class, url, fileName, input);
    }
}
