package chat.tamtam.api.requests.queries;

/*-
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

import org.jetbrains.annotations.Nullable;

import chat.tamtam.api.client.impl.RequestParams;

/**
 * @author alexandrchuprin
 */
public class TamTamQuery<T> extends RequestParams {
    private final String url;
    private final Class<T> responseType;
    private final Object body;
    private final boolean isPost;

    public TamTamQuery(String url, Class<T> responseType) {
        this(url, null, responseType, true);
    }

    public TamTamQuery(String url, Class<T> responseType, boolean isPost) {
        this(url, null, responseType, isPost);
    }

    public TamTamQuery(String url, Object body, Class<T> responseType, boolean isPost) {
        this.url = url;
        this.responseType = responseType;
        this.body = body;
        this.isPost = isPost;
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    public String getURL() {
        return url;
    }

    @Nullable
    public Object getBody() {
        return body;
    }

    public boolean isPost() {
        return isPost;
    }
}
