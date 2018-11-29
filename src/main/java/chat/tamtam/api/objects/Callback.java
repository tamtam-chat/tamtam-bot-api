package chat.tamtam.api.objects;

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

import chat.tamtam.api.TamTamSerializable;
import chat.tamtam.api.objects.User;

/**
 * @author alexandrchuprin
 */
public class Callback implements TamTamSerializable {
    private static final String ID = "id";
    private static final String PAYLOAD = "payload";
    private static final String USER = "user";

    private final User user;
    private final String id;
    private final String payload;

    @JsonCreator
    public Callback(@JsonProperty(USER) User user, @JsonProperty(ID) String id,
                    @JsonProperty(PAYLOAD) String payload) {
        this.user = user;
        this.id = id;
        this.payload = payload;
    }

    @JsonProperty(USER)
    public User getUser() {
        return user;
    }

    @JsonProperty(ID)
    public String getId() {
        return id;
    }

    @JsonProperty(PAYLOAD)
    public String getPayload() {
        return payload;
    }
}
