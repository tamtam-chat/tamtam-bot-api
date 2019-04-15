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

import org.jetbrains.annotations.Nullable;

/**
 * After pressing this type of button client sends new message with attachment of current user geo location
 */
public class RequestGeoLocationButton extends Button implements TamTamSerializable {

    private Boolean quick;

    @JsonCreator
    public RequestGeoLocationButton(@JsonProperty("text") String text, @Nullable @JsonProperty("intent") Intent intent) { 
        super(text, intent);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    public RequestGeoLocationButton quick(Boolean quick) {
        this.setQuick(quick);
        return this;
    }

    /**
    * If *true*, sends location without asking user&#39;s confirmation
    * @return quick
    **/
    @JsonProperty("quick")
    public Boolean isQuick() {
        return quick;
    }

    public void setQuick(Boolean quick) {
        this.quick = quick;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return Button.REQUEST_GEO_LOCATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        RequestGeoLocationButton other = (RequestGeoLocationButton) o;
        return Objects.equals(this.quick, other.quick) &&
            super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (quick != null ? quick.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestGeoLocationButton{"+ super.toString()
            + " quick='" + quick + '\''
            + '}';
    }
}
