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

package chat.tamtam.botapi.queries;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.model.StickerAttachmentRequest;
import chat.tamtam.botapi.model.StickerAttachmentRequestPayload;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EditMessageQueryTest extends QueryTest {

    @Test
    public void editMessageTest() throws Exception {
        List<AttachmentRequest> attachments = Collections.singletonList(new StickerAttachmentRequest(new StickerAttachmentRequestPayload("code")));
        NewMessageBody newMessageBody = new NewMessageBody("edited", attachments);
        String messageId = "mid.qweqwekljoiy7971346";
        SimpleQueryResult response = api.editMessage(newMessageBody, messageId).execute();
        assertThat(response.isSuccess(), is(true));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.editMessage(null, "mid.0912348787923687263847834").execute();
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException2() throws Exception {
        api.editMessage(new NewMessageBody("text", null), null).execute();
    }
}
