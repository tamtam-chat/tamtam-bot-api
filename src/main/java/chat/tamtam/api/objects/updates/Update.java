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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import chat.tamtam.api.TamTamSerializable;

/**
 * @author alexandrchuprin
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "update_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageCreatedUpdate.class, name = "message_created"),
        @JsonSubTypes.Type(value = MessageCallbackUpdate.class, name = "message_callback"),
})
public abstract class Update implements TamTamSerializable {
}
