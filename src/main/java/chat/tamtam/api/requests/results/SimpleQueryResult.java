package chat.tamtam.api.requests.results;

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

/**
 * @author alexandrchuprin
 */
public class SimpleQueryResult implements TamTamSerializable {
    protected static final String SUCCESS = "success";

    private final boolean isSuccessful;

    @JsonCreator
    public SimpleQueryResult(@JsonProperty(SUCCESS) boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    @JsonProperty(SUCCESS)
    public boolean isSuccessful() {
        return isSuccessful;
    }
}
