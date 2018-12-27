package chat.tamtam.botapi.model;

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

import java.util.Objects;
import java.util.Arrays;
import chat.tamtam.botapi.model.Intent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import chat.tamtam.botapi.TamTamSerializable;
import org.jetbrains.annotations.Nullable;

/**
 * Button
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = CallbackButton.class, name = "callback"),
  @JsonSubTypes.Type(value = LinkButton.class, name = "link"),
  @JsonSubTypes.Type(value = RequestGeoLocationButton.class, name = "request_geo_location"),
  @JsonSubTypes.Type(value = RequestContactButton.class, name = "request_contact"),
})

public class Button implements TamTamSerializable {
    @JsonProperty("type")
    private String type;

    @JsonProperty("text")
    private final String text;

    @JsonProperty("intent")
    private final Intent intent;

    @JsonCreator
    public Button(@JsonProperty("text") String text, @Nullable @JsonProperty("intent") Intent intent) { 
        this.text = text;
        this.intent = intent;
    }

    /**
    * @return type
    **/
    public String getType() {
        return type;
    }

    /**
    * Visible text of button
    * @return text
    **/
    public String getText() {
        return text;
    }

    /**
    * Intent of button. Affects clients representation.
    * @return intent
    **/
    @Nullable
    public Intent getIntent() {
        return intent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        Button other = (Button) o;
        return Objects.equals(this.type, other.type) &&
            Objects.equals(this.text, other.text) &&
            Objects.equals(this.intent, other.intent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text, intent);
    }

    @Override
    public String toString() {
        return "Button{"
            + " type='" + type + '\''
            + " text='" + text + '\''
            + " intent='" + intent + '\''
            + '}';
    }
}

