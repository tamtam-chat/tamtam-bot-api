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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AudioAttachment;
import chat.tamtam.botapi.model.Button;
import chat.tamtam.botapi.model.CallbackButton;
import chat.tamtam.botapi.model.ContactAttachment;
import chat.tamtam.botapi.model.FailByDefaultAttachmentVisitor;
import chat.tamtam.botapi.model.FailByDefaultButtonVisitor;
import chat.tamtam.botapi.model.FileAttachment;
import chat.tamtam.botapi.model.InlineKeyboardAttachment;
import chat.tamtam.botapi.model.LinkButton;
import chat.tamtam.botapi.model.LocationAttachment;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.PhotoAttachment;
import chat.tamtam.botapi.model.RequestContactButton;
import chat.tamtam.botapi.model.RequestGeoLocationButton;
import chat.tamtam.botapi.model.ShareAttachment;
import chat.tamtam.botapi.model.StickerAttachment;
import chat.tamtam.botapi.model.VideoAttachment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;
import static spark.Spark.get;

public class GetMessagesQueryTest extends UnitTestBase {

    @Test
    public void getMessagesTest() throws Exception {
        Long chatId = ID_COUNTER.incrementAndGet();

        get("/messages", (req, resp) -> {
            String messageIds = req.queryParams("message_ids");
            if (messageIds != null) {
                Set<String> idsSet = Stream.of(messageIds.split(",")).collect(Collectors.toSet());
                assertThat(idsSet, is(new HashSet(Arrays.asList("mid2", "mid1"))));
                return new MessageList(Arrays.asList(message(chatId, null), message(chatId, null)));
            }

            long chatIdParam = Long.parseLong(req.queryParams("chat_id"));
            assertThat(chatIdParam, is(chatId));
            return new MessageList(Arrays.asList(message(chatId, null), message(chatId, null)));
        }, this::serialize);

        MessageList response = api.getMessages()
                .chatId(chatId)
                .count(ThreadLocalRandom.current().nextInt())
                .from(ThreadLocalRandom.current().nextLong())
                .to(ThreadLocalRandom.current().nextLong())
                .execute();

        MessageList response2 = api.getMessages()
                .messageIds(new HashSet<>(Arrays.asList("mid1", "mid2")))
                .count(ThreadLocalRandom.current().nextInt())
                .from(ThreadLocalRandom.current().nextLong())
                .to(ThreadLocalRandom.current().nextLong())
                .execute();

        assertThat(response.getMessages().size(), is(greaterThan(0)));
        assertThat(response2.getMessages().size(), is(greaterThan(0)));

        for (Message message : response.getMessages()) {
            for (Attachment attachment : message.getBody().getAttachments()) {
                attachment.visit(new FailByDefaultAttachmentVisitor() {
                    @Override
                    public void visit(PhotoAttachment model) {
                        assertThat(model, is(PHOTO_ATTACHMENT));
                    }

                    @Override
                    public void visit(VideoAttachment model) {
                        assertThat(model, is(VIDEO_ATTACHMENT));
                    }

                    @Override
                    public void visit(AudioAttachment model) {
                        assertThat(model, is(AUDIO_ATTACHMENT));
                    }

                    @Override
                    public void visit(FileAttachment model) {
                        assertThat(model, is(FILE_ATTACHMENT));
                    }

                    @Override
                    public void visit(StickerAttachment model) {
                        assertThat(model, is(STICKER_ATTACHMENT));
                    }

                    @Override
                    public void visit(ContactAttachment model) {
                        assertThat(model, is(CONTACT_ATTACHMENT));
                    }

                    @Override
                    public void visit(InlineKeyboardAttachment model) {
                        assertThat(model, is(INLINE_KEYBOARD_ATTACHMENT));
                        model.getPayload().getButtons().stream().flatMap(List::stream).forEach(this::visitButton);
                    }

                    @Override
                    public void visit(ShareAttachment model) {
                        assertThat(model, is(SHARE_ATTACHMENT));
                    }

                    @Override
                    public void visit(LocationAttachment model) {
                        assertThat(model, is(LOCATION_ATTACHMENT));
                    }

                    private void visitButton(Button button) {
                        button.visit(new FailByDefaultButtonVisitor() {
                            @Override
                            public void visit(CallbackButton model) {
                                assertThat(model, is(CALLBACK_BUTTON));
                            }

                            @Override
                            public void visit(LinkButton model) {
                                assertThat(model, is(LINK_BUTTON));
                            }

                            @Override
                            public void visit(RequestGeoLocationButton model) {
                                assertThat(model, is(REQUEST_GEO_LOCATION_BUTTON));
                            }

                            @Override
                            public void visit(RequestContactButton model) {
                                assertThat(model, is(REQUEST_CONTACT_BUTTON));
                            }
                        });
                    }
                });
            }
        }
    }
}
