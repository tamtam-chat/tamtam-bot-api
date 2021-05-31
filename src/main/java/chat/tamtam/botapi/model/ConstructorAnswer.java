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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.Nullable;

/**
 * Bot&#39;s answer on construction request
 */
public class ConstructorAnswer implements TamTamSerializable {

    private List<@Valid ConstructedMessageBody> messages;
    private @Valid Boolean allowUserInput;
    @Nullable
    private @Valid String hint;
    @Size(max = 8192)
    private @Valid String data;
    @Nullable
    private @Valid Keyboard keyboard;
    @Nullable
    private @Valid String placeholder;

    public ConstructorAnswer messages(List<ConstructedMessageBody> messages) {
        this.setMessages(messages);
        return this;
    }

    /**
    * Array of prepared messages. This messages will be sent as user taps on \&quot;Send\&quot; button
    * @return messages
    **/
    @JsonProperty("messages")
    public List<ConstructedMessageBody> getMessages() {
        return messages;
    }

    public void setMessages(List<ConstructedMessageBody> messages) {
        this.messages = messages;
    }

    public ConstructorAnswer allowUserInput(Boolean allowUserInput) {
        this.setAllowUserInput(allowUserInput);
        return this;
    }

    /**
    * If &#x60;true&#x60; user can send any input manually. Otherwise, only keyboard will be shown
    * @return allowUserInput
    **/
    @JsonProperty("allow_user_input")
    public Boolean isAllowUserInput() {
        return allowUserInput;
    }

    public void setAllowUserInput(Boolean allowUserInput) {
        this.allowUserInput = allowUserInput;
    }

    public ConstructorAnswer hint(@Nullable String hint) {
        this.setHint(hint);
        return this;
    }

    /**
    * Hint to user. Will be shown on top of keyboard
    * @return hint
    **/
    @Nullable
    @JsonProperty("hint")
    public String getHint() {
        return hint;
    }

    public void setHint(@Nullable String hint) {
        this.hint = hint;
    }

    public ConstructorAnswer data(String data) {
        this.setData(data);
        return this;
    }

    /**
    * In this property you can store any additional data up to 8KB. We send this data back to bot within the next construction request. It is handy to store here any state of construction session
    * @return data
    **/
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ConstructorAnswer keyboard(@Nullable Keyboard keyboard) {
        this.setKeyboard(keyboard);
        return this;
    }

    /**
    * Keyboard to show to user in constructor mode
    * @return keyboard
    **/
    @Nullable
    @JsonProperty("keyboard")
    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(@Nullable Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public ConstructorAnswer placeholder(@Nullable String placeholder) {
        this.setPlaceholder(placeholder);
        return this;
    }

    /**
    * Text to show over the text field
    * @return placeholder
    **/
    @Nullable
    @JsonProperty("placeholder")
    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(@Nullable String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ConstructorAnswer other = (ConstructorAnswer) o;
        return Objects.equals(this.messages, other.messages) &&
            Objects.equals(this.allowUserInput, other.allowUserInput) &&
            Objects.equals(this.hint, other.hint) &&
            Objects.equals(this.data, other.data) &&
            Objects.equals(this.keyboard, other.keyboard) &&
            Objects.equals(this.placeholder, other.placeholder);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        result = 31 * result + (allowUserInput != null ? allowUserInput.hashCode() : 0);
        result = 31 * result + (hint != null ? hint.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (keyboard != null ? keyboard.hashCode() : 0);
        result = 31 * result + (placeholder != null ? placeholder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConstructorAnswer{"
            + " messages='" + messages + '\''
            + " allowUserInput='" + allowUserInput + '\''
            + " hint='" + hint + '\''
            + " data='" + data + '\''
            + " keyboard='" + keyboard + '\''
            + " placeholder='" + placeholder + '\''
            + '}';
    }
}
