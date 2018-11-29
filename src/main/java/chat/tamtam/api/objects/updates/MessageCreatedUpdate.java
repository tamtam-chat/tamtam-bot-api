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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import chat.tamtam.api.objects.Message;

/**
 * @author alexandrchuprin
 */
public class MessageCreatedUpdate extends Update {
    private final Message message;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public MessageCreatedUpdate(Message message) {
        this.message = message;
    }

    @JsonProperty
    @JsonUnwrapped
    public Message getMessage() {
        return message;
    }
}
