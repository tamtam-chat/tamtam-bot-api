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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alexandrchuprin
 */
public class CallbackButton extends Button {
    protected static final String PAYLOAD = "payload";
    private final String payload;

    public CallbackButton(String text, String payload) {
        this(text, Intent.DEFAULT, payload);
    }

    public CallbackButton(String text, Intent intent, String payload) {
        super(Type.CALLBACK, text, intent);
        this.payload = payload;
    }

    @JsonProperty(PAYLOAD)
    public String getPayload() {
        return payload;
    }

    @JsonCreator
    protected static CallbackButton create(@JsonProperty(TEXT) String text,
                                           @JsonProperty(value = INTENT) Intent intent,
                                           @JsonProperty(PAYLOAD) String payload) {
        return new CallbackButton(text, intent == null ? Intent.DEFAULT : intent, payload);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallbackButton)) return false;

        CallbackButton that = (CallbackButton) o;

        return payload != null ? payload.equals(that.payload) : that.payload == null;
    }

    @Override
    public int hashCode() {
        return payload != null ? payload.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CallbackButton{");
        sb.append("payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
