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


/**
 * LocationAttachment
 */
public class LocationAttachment extends Attachment implements TamTamSerializable {

    private final Float latitude;
    private final Float longitude;

    @JsonCreator
    public LocationAttachment(@JsonProperty("latitude") Float latitude, @JsonProperty("longitude") Float longitude) { 
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
    public Float getLatitude() {
        return latitude;
    }

    /**
    * @return longitude
    **/
    @JsonProperty("longitude")
    public Float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        LocationAttachment other = (LocationAttachment) o;
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
        return "LocationAttachment{"+ super.toString()
            + " latitude='" + latitude + '\''
            + " longitude='" + longitude + '\''
            + '}';
    }
}

