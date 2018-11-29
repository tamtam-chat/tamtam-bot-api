package chat.tamtam.api.objects.keyboard;

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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import chat.tamtam.api.TamTamEnum;

/**
 * @author alexandrchuprin
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, fieldVisibility = JsonAutoDetect.Visibility.NONE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = Button.TYPE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CallbackButton.class, name = Button.CALLBACK),
        @JsonSubTypes.Type(value = LinkButton.class, name = Button.LINK),
        @JsonSubTypes.Type(value = RequestContactButton.class, name = Button.REQUEST_CONTACT),
        @JsonSubTypes.Type(value = RequestGeoLocationButton.class, name = Button.REQUEST_GEO_LOCATION),
})
public abstract class Button implements Serializable {
    static final String TEXT = "text";
    static final String INTENT = "intent";
    static final String TYPE = "type";
    static final String CALLBACK = "callback";
    static final String LINK = "link";
    static final String REQUEST_CONTACT = "request_contact";
    static final String REQUEST_GEO_LOCATION = "request_geo_location";

    private final Type type;
    private final String text;
    private final Intent intent;

    Button(Type type, String text, Intent intent) {
        this.type = type;
        this.text = text;
        this.intent = intent;
    }

    @JsonProperty(TEXT)
    public String getText() {
        return text;
    }

    @JsonProperty(INTENT)
    public Intent getIntent() {
        return intent;
    }

    @JsonProperty(TYPE)
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Button{");
        sb.append("text='").append(text).append('\'');
        sb.append(", intent=").append(intent);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Button)) return false;

        Button button = (Button) o;

        if (text != null ? !text.equals(button.text) : button.text != null) return false;
        return intent == button.intent;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (intent != null ? intent.hashCode() : 0);
        return result;
    }

    protected enum Type implements TamTamEnum {
        CALLBACK(0),
        LINK(1),
        REQUEST_CONTACT(2),
        REQUEST_GEO_LOCATION(3);

        private final byte id;

        Type(int id) {
            this.id = (byte) id;
        }

        @JsonCreator
        public static Type create(String value) {
            return TamTamEnum.create(Type.class, value);
        }

        @Override
        public byte getId() {
            return id;
        }
    }
}
