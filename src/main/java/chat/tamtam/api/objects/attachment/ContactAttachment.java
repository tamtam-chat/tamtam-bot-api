package chat.tamtam.api.objects.attachment;

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

import chat.tamtam.api.objects.User;

/**
 * @author alexandrchuprin
 */
public class ContactAttachment extends Attachment {
    private static final String VCF_INFO = "vcfInfo";
    private static final String TAM_INFO = "tamInfo";

    private final String vcfInfo;
    private final User user;

    @JsonCreator
    public ContactAttachment(@JsonProperty(PAYLOAD) Payload payload) {
        super(payload);
        this.vcfInfo = payload.vcfInfo;
        this.user = payload.user;
    }

    public ContactAttachment(String vcfInfo, User user) {
        super(new Payload(vcfInfo, user));
        this.vcfInfo = vcfInfo;
        this.user = user;
    }

    private static class Payload implements AttachmentPayload {
        @JsonProperty(VCF_INFO)
        private final String vcfInfo;
        @JsonProperty(TAM_INFO)
        private final User user;

        @JsonCreator
        private Payload(@JsonProperty(VCF_INFO) String vcfInfo, @JsonProperty(TAM_INFO) User user) {
            this.vcfInfo = vcfInfo;
            this.user = user;
        }
    }
}
