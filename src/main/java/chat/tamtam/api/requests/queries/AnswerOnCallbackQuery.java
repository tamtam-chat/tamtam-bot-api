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

import chat.tamtam.api.client.impl.RequestParam;
import chat.tamtam.api.requests.CallbackAnswer;
import chat.tamtam.api.requests.results.SimpleQueryResult;

/**
 * @author alexandrchuprin
 */
public class AnswerOnCallbackQuery extends TamTamQuery<SimpleQueryResult> {
    private final RequestParam<String> callbackId = new RequestParam<String>("callback_id", this).required();

    public AnswerOnCallbackQuery(String callbackId, CallbackAnswer answer) {
        super("/me/answer", answer, SimpleQueryResult.class, true);
        this.callbackId.setValue(callbackId);
    }
}
