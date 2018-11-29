package chat.tamtam.api.objects.updates;

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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import chat.tamtam.api.objects.Callback;

/**
 * @author alexandrchuprin
 */
public class MessageCallbackUpdate extends Update {
    private final Callback callback;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public MessageCallbackUpdate(Callback callback) {
        this.callback = callback;
    }

    @JsonUnwrapped
    public Callback getCallback() {
        return callback;
    }
}
