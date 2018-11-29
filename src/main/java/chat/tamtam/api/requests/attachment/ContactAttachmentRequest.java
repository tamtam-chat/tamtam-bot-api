package chat.tamtam.api.requests.attachment;

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
public class ContactAttachmentRequest extends AttachmentRequest {
    private final Payload payload;

    public ContactAttachmentRequest(String name, Long contactId, String vcfInfo, String vcfPhone) {
        this(new Payload());
        payload.name = name;
        payload.contactId = contactId;
        payload.vcfInfo = vcfInfo;
        payload.vcfPhone = vcfPhone;
    }

    @JsonCreator
    ContactAttachmentRequest(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.payload = payload;
    }

    @Override
    public <T, E extends Throwable> T map(Mapper<T, E> mapper) throws E {
        return mapper.map(this);
    }

    public String getName() {
        return payload.name;
    }

    public Long getContactId() {
        return payload.contactId;
    }

    public String getVcfInfo() {
        return payload.vcfInfo;
    }

    public String getVcfPhone() {
        return payload.vcfPhone;
    }

    static class Payload implements AttachmentRequestPayload {
        @JsonProperty
        private String name;
        @JsonProperty
        private Long contactId;
        @JsonProperty
        private String vcfInfo;
        @JsonProperty
        private String vcfPhone;
    }
}
