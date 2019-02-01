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

package chat.tamtam.botapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.Nullable;

/**
 * After pressing this type of button client sends new message with attachment of curent user contact
 */
public class RequestContactButton extends Button implements TamTamSerializable {


    @JsonCreator
    public RequestContactButton(@JsonProperty("text") String text, @Nullable @JsonProperty("intent") Intent intent) { 
        super(text, intent);
    }



    @Override
    public String toString() {
        return "RequestContactButton{"+ super.toString()
            + '}';
    }
}

