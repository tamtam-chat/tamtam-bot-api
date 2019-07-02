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
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * After pressing this type of button client sends to server payload it contains
 */
public class CallbackButton extends Button implements TamTamSerializable {

    @NotNull
    @Size(max = 1024)
    private final String payload;
    private Intent intent;

    @JsonCreator
    public CallbackButton(@JsonProperty("payload") String payload, @JsonProperty("text") String text) { 
        super(text);
        this.payload = payload;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * Button payload
    * @return payload
    **/
    @JsonProperty("payload")
    public String getPayload() {
        return payload;
    }

    public CallbackButton intent(Intent intent) {
        this.setIntent(intent);
        return this;
    }

    /**
    * Intent of button. Affects clients representation.
    * @return intent
    **/
    @JsonProperty("intent")
    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return Button.CALLBACK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        CallbackButton other = (CallbackButton) o;
        return Objects.equals(this.payload, other.payload) &&
            Objects.equals(this.intent, other.intent) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (intent != null ? intent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CallbackButton{"+ super.toString()
            + " payload='" + payload + '\''
            + " intent='" + intent + '\''
            + '}';
    }
}
