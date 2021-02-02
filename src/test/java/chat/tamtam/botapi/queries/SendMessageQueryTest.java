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
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.AudioAttachmentRequest;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.ContactAttachmentRequest;
import chat.tamtam.botapi.model.ContactAttachmentRequestPayload;
import chat.tamtam.botapi.model.FailByDefaultARVisitor;
import chat.tamtam.botapi.model.FileAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequest;
import chat.tamtam.botapi.model.InlineKeyboardAttachmentRequestPayload;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.LocationAttachmentRequest;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;
import chat.tamtam.botapi.model.PhotoToken;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.StickerAttachmentRequest;
import chat.tamtam.botapi.model.StickerAttachmentRequestPayload;
import chat.tamtam.botapi.model.UploadedInfo;
import chat.tamtam.botapi.model.VideoAttachmentRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static spark.Spark.post;

public class SendMessageQueryTest extends UnitTestBase {

    private static final PhotoAttachmentRequest PHOTO_ATTACHMENT_REQUEST = new PhotoAttachmentRequest(
            new PhotoAttachmentRequestPayload().photos(Collections.singletonMap("photokey", new PhotoToken("token"))));
    private static final VideoAttachmentRequest VIDEO_ATTACHMENT_REQUEST = new VideoAttachmentRequest(
            new UploadedInfo().token("token"));
    private static final AudioAttachmentRequest AUDIO_ATTACHMENT_REQUEST = new AudioAttachmentRequest(
            new UploadedInfo().token("token"));
    private static final FileAttachmentRequest FILE_ATTACHMENT_REQUEST = new FileAttachmentRequest(
            new UploadedInfo().token("token"));
    private static final StickerAttachmentRequest STICKER_ATTACHMENT_REQUEST = new StickerAttachmentRequest(new StickerAttachmentRequestPayload("stickercode"));
    private static final ContactAttachmentRequest CONTACT_ATTACHMENT_REQUEST = new ContactAttachmentRequest(new ContactAttachmentRequestPayload("name").vcfInfo("vcfInfo"));
    private static final LocationAttachmentRequest LOCATION_ATTACHMENT_REQUEST = new LocationAttachmentRequest(
            ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble());
    private static final InlineKeyboardAttachmentRequest INLINE_KEYBOARD_ATTACHMENT_REQUEST = new InlineKeyboardAttachmentRequest(new InlineKeyboardAttachmentRequestPayload(
            Arrays.asList(
                    Arrays.asList(
                            new CallbackButton("payload", "text"),
                            new LinkButton("https://url.com", "linktext")
                    ),
                    Arrays.asList(
                            new RequestContactButton("willbeignored"),
                            new RequestGeoLocationButton("willbeignored")
                    )
            )
    ));

    @Test
    public void sendMessageTest() throws Exception {
        NewMessageBody sendingMessage = new NewMessageBody("text", createAttachmentRequests(), null).notify(true);

        post("/messages", (req, resp) -> {
            String chatId = req.queryParams("chat_id");
            String userId = req.queryParams("user_id");
            NewMessageBody newMessage = serializer.deserialize(req.body(), NewMessageBody.class);
            if (chatId != null) {
                assertThat(req.queryParams("disable_link_preview"), is("true"));
            }
            assertThat(newMessage, is(equalTo(sendingMessage)));
            visit(newMessage.getAttachments());
            return new SendMessageResult(message(chatId == null ? null : Long.parseLong(chatId), userId == null ? null : Long.parseLong(userId)));
        }, this::serialize);

        SendMessageResult response = api.sendMessage(sendingMessage).chatId(1L).disableLinkPreview(true).execute();
        assertThat(response.getMessage().getBody().getMid(), is(notNullValue()));

        SendMessageResult response2 = api.sendMessage(sendingMessage).userId(2L).execute();
        assertThat(response2.getMessage().getRecipient().getUserId(), is(notNullValue()));
    }

    private void visit(List<AttachmentRequest> attachments) {
        for (AttachmentRequest attachment : attachments) {
            attachment.visit(new FailByDefaultARVisitor() {
                @Override
                public void visit(PhotoAttachmentRequest model) {
                    assertThat(model, is(PHOTO_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(VideoAttachmentRequest model) {
                    assertThat(model, is(VIDEO_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(AudioAttachmentRequest model) {
                    assertThat(model, is(AUDIO_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(FileAttachmentRequest model) {
                    assertThat(model, is(FILE_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(StickerAttachmentRequest model) {
                    assertThat(model, is(STICKER_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(ContactAttachmentRequest model) {
                    assertThat(model, is(CONTACT_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(LocationAttachmentRequest model) {
                    assertThat(model, is(LOCATION_ATTACHMENT_REQUEST));
                }

                @Override
                public void visit(InlineKeyboardAttachmentRequest model) {
                    assertThat(model, is(INLINE_KEYBOARD_ATTACHMENT_REQUEST));
                }
            });
        }
    }

    @Test(expected = RequiredParameterMissingException.class)
    public void shouldThrowException() throws Exception {
        api.sendMessage(null).execute();
    }

    private List<AttachmentRequest> createAttachmentRequests() {
        return Arrays.asList(
                PHOTO_ATTACHMENT_REQUEST,
                VIDEO_ATTACHMENT_REQUEST,
                AUDIO_ATTACHMENT_REQUEST,
                FILE_ATTACHMENT_REQUEST,
                STICKER_ATTACHMENT_REQUEST,
                CONTACT_ATTACHMENT_REQUEST,
                INLINE_KEYBOARD_ATTACHMENT_REQUEST,
                LOCATION_ATTACHMENT_REQUEST
        );
    }

}
