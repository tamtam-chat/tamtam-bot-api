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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * It can be either message (text/attachments) or button callback
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "input_type", visible = true, defaultImpl = ConstructorInput.class, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value = CallbackConstructorInput.class, name = ConstructorInput.CALLBACK),
  @JsonSubTypes.Type(value = MessageConstructorInput.class, name = ConstructorInput.MESSAGE),
})
@KnownInstance(ofClass = ConstructorInput.class, discriminator = "input_type")
public class ConstructorInput implements TamTamSerializable {
    public static final String CALLBACK = "callback";
    public static final String MESSAGE = "message";
    public static final Set<String> TYPES = new HashSet<>(Arrays.asList(
        CALLBACK, 
        MESSAGE
    ));




    public void visit(Visitor visitor) {
        visitor.visitDefault(this);
    }

    public <T> T map(Mapper<T> mapper) {
        return mapper.mapDefault(this);
    }

    @JsonProperty("input_type")
    public String getType() {
        throw new UnsupportedOperationException("Model has no concrete type.");
    }

    @Override
    public String toString() {
        return "ConstructorInput{"
            + '}';
    }

    public interface Visitor {
        void visit(CallbackConstructorInput model);
        void visit(MessageConstructorInput model);
        void visitDefault(ConstructorInput model);
    }

    public interface Mapper<T> {
        T map(CallbackConstructorInput model);
        T map(MessageConstructorInput model);
        T mapDefault(ConstructorInput model);
    }
}
