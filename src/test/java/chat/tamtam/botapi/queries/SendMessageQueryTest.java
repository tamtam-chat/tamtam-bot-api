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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.Intent;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoToken;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.StickerAttachmentRequest;
import chat.tamtam.botapi.model.StickerAttachmentRequestPayload;
import chat.tamtam.botapi.model.UploadedFileInfo;
import chat.tamtam.botapi.model.UploadedInfo;
import chat.tamtam.botapi.model.VideoAttachmentRequest;
import spark.Request;
import spark.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static spark.Spark.post;

public class SendMessageQueryTest extends QueryTest {

    @Test
    public void sendMessageTest() throws Exception {
        NewMessageBody sendingMessage = new NewMessageBody("text", createAttachmentRequests())
                .notify(true);

        post("/messages", (req, resp) -> {
            String chatId = req.queryParams("chat_id");
            String userId = req.queryParams("user_id");
            NewMessageBody newMessage = serializer.deserialize(req.body(), NewMessageBody.class);
            assertThat(newMessage, is(equalTo(sendingMessage)));
            return new SendMessageResult(chatId == null ? null : Long.parseLong(chatId), userId == null ? null : Long.parseLong(userId), "mid." + chatId);
        }, this::serialize);

        Long chatId = 1L;
        SendMessageResult response = api.sendMessage(sendingMessage).chatId(chatId).execute();
        assertThat(response.getMessageId(), is(notNullValue()));

        SendMessageResult response2 = api.sendMessage(sendingMessage).userId(2L).execute();
        assertThat(response2.getRecipientId(), is(notNullValue()));
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.sendMessage(null).execute();
    }

    private List<AttachmentRequest> createAttachmentRequests() {
        return Arrays.asList(
                new PhotoAttachmentRequest(null)
//                new PhotoAttachmentRequest(
//                        new PhotoAttachmentRequestPayload(null,
//                                Collections.singletonMap("photokey", new PhotoToken("token"))))
//                new VideoAttachmentRequest(new UploadedInfo(1L))
//                new AudioAttachmentRequest(new UploadedInfo(2L))
//                new FileAttachmentRequest(new UploadedFileInfo(3L))
//                new StickerAttachmentRequest(new StickerAttachmentRequestPayload("stickercode"))
//                new ContactAttachmentRequest(new ContactAttachmentRequestPayload("name", null, "vcfInfo", null))
//                new InlineKeyboardAttachmentRequest(new InlineKeyboardAttachmentRequestPayload(
//                        Arrays.asList(
//                                Arrays.asList(
//                                        new CallbackButton("payload", "text", Intent.DEFAULT),
//                                        new LinkButton("https://url.com", "linktext", Intent.DEFAULT)
//                                ),
//                                Arrays.asList(
//                                        new RequestContactButton("willbeignored", Intent.DEFAULT),
//                                        new RequestGeoLocationButton("willbeignored", Intent.DEFAULT)
//                                )
//                        )
//                ))
        );
    }

}
