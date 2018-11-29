package chat.tamtam.api.requests;

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
import java.util.List;

import chat.tamtam.api.objects.Recipient;
import chat.tamtam.api.requests.attachment.PhotoAttachmentRequest;

public class ChatControl implements Serializable {
    public String title;
    public PhotoAttachmentRequest icon;
    public String leave;
    public List<Recipient> add_members;
    public Recipient remove_member;
}
