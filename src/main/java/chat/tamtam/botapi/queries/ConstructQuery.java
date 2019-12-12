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

package chat.tamtam.botapi.queries;

import chat.tamtam.botapi.client.TamTamClient;

import chat.tamtam.botapi.model.ConstructorAnswer;
import chat.tamtam.botapi.model.SimpleQueryResult;

public class ConstructQuery extends TamTamQuery<SimpleQueryResult> { 
    private final QueryParam<String> sessionId = new QueryParam<String>("session_id", this).required();

    public ConstructQuery(TamTamClient client, ConstructorAnswer constructorAnswer, String sessionId) {
        super(client, "/answers/constructor", constructorAnswer, SimpleQueryResult.class, Method.POST);
        this.sessionId.setValue(sessionId);
    }

}
