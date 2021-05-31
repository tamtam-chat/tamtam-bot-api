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

import org.jetbrains.annotations.Nullable;

/**
 * ContactAttachmentRequestPayload
 */
public class ContactAttachmentRequestPayload implements TamTamSerializable {

    @Nullable
    private final @Valid String name;
    @Nullable
    private @Valid Long contactId;
    @Nullable
    private @Valid String vcfInfo;
    @Nullable
    private @Valid String vcfPhone;

    @JsonCreator
    public ContactAttachmentRequestPayload(@Nullable @JsonProperty("name") String name) { 
        this.name = name;
    }

    /**
    * Contact name
    * @return name
    **/
    @Nullable
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public ContactAttachmentRequestPayload contactId(@Nullable Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    /**
    * Contact identifier if it is registered TamTam user
    * @return contactId
    **/
    @Nullable
    @JsonProperty("contact_id")
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(@Nullable Long contactId) {
        this.contactId = contactId;
    }

    public ContactAttachmentRequestPayload vcfInfo(@Nullable String vcfInfo) {
        this.setVcfInfo(vcfInfo);
        return this;
    }

    /**
    * Full information about contact in VCF format
    * @return vcfInfo
    **/
    @Nullable
    @JsonProperty("vcf_info")
    public String getVcfInfo() {
        return vcfInfo;
    }

    public void setVcfInfo(@Nullable String vcfInfo) {
        this.vcfInfo = vcfInfo;
    }

    public ContactAttachmentRequestPayload vcfPhone(@Nullable String vcfPhone) {
        this.setVcfPhone(vcfPhone);
        return this;
    }

    /**
    * Contact phone in VCF format
    * @return vcfPhone
    **/
    @Nullable
    @JsonProperty("vcf_phone")
    public String getVcfPhone() {
        return vcfPhone;
    }

    public void setVcfPhone(@Nullable String vcfPhone) {
        this.vcfPhone = vcfPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        ContactAttachmentRequestPayload other = (ContactAttachmentRequestPayload) o;
        return Objects.equals(this.name, other.name) &&
            Objects.equals(this.contactId, other.contactId) &&
            Objects.equals(this.vcfInfo, other.vcfInfo) &&
            Objects.equals(this.vcfPhone, other.vcfPhone);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contactId != null ? contactId.hashCode() : 0);
        result = 31 * result + (vcfInfo != null ? vcfInfo.hashCode() : 0);
        result = 31 * result + (vcfPhone != null ? vcfPhone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactAttachmentRequestPayload{"
            + " name='" + name + '\''
            + " contactId='" + contactId + '\''
            + " vcfInfo='" + vcfInfo + '\''
            + " vcfPhone='" + vcfPhone + '\''
            + '}';
    }
}
