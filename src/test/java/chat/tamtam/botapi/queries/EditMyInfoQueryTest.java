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

import chat.tamtam.botapi.model.BotCommand;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.BotPatch;
import chat.tamtam.botapi.model.PhotoAttachmentRequest;
import chat.tamtam.botapi.model.PhotoAttachmentRequestPayload;

import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static spark.Spark.get;
import static spark.Spark.patch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class EditMyInfoQueryTest extends QueryTest {
    
    @Test
    public void editMyInfoTest() throws Exception {
        patch("/me", (req, resp) -> {
            BotPatch patch = serializer.deserialize(req.body(), BotPatch.class);
            BotInfo botInfo = new BotInfo(me.getUserId(), patch.getName(), patch.getUsername());
            botInfo.commands(patch.getCommands());
            botInfo.description(patch.getDescription());
            botInfo.avatarUrl(patch.getPhoto().getUrl());
            botInfo.fullAvatarUrl(patch.getPhoto().getUrl());
            return botInfo;
        }, this::serialize);

        List<BotCommand> commands = Collections.singletonList(new BotCommand("name").description("description"));
        String botname = "botname";
        String description = "botdescription";
        BotPatch botPatch = new BotPatch()
                .commands(commands)
                .name(botname)
                .description(description)
                .username("botusername")
                .photo(new PhotoAttachmentRequestPayload().url("patchurl"));

        EditMyInfoQuery query = new EditMyInfoQuery(client, botPatch);
        BotInfo response = query.execute();

        assertThat(response.getCommands(), is(commands));
        assertThat(response.getName(), is(botname));
        assertThat(response.getDescription(), is(description));
    }
    
}
