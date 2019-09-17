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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Request to attach keyboard to message
 */
public class LocationAttachmentRequest extends AttachmentRequest implements TamTamSerializable {

    @NotNull
    private final @Valid Double latitude;
    @NotNull
    private final @Valid Double longitude;

    @JsonCreator
    public LocationAttachmentRequest(@JsonProperty("latitude") Double latitude, @JsonProperty("longitude") Double longitude) { 
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    /**
    * @return latitude
    **/
    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    /**
    * @return longitude
    **/
    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("type")
    @Override
    public String getType() {
        return AttachmentRequest.LOCATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        LocationAttachmentRequest other = (LocationAttachmentRequest) o;
        return Objects.equals(this.latitude, other.latitude) &&
            Objects.equals(this.longitude, other.longitude);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationAttachmentRequest{"+ super.toString()
            + " latitude='" + latitude + '\''
            + " longitude='" + longitude + '\''
            + '}';
    }
}
