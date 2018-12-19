package chat.tamtam.botapi.queries;

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

import chat.tamtam.botapi.client.TamTamClient;

import chat.tamtam.botapi.queries.GetUpdatesQuery;
import chat.tamtam.botapi.model.UpdateList;

public class GetUpdatesQuery extends TamTamQuery<UpdateList> {
    private final QueryParam<Integer> limit = new QueryParam<Integer>("limit", this);
    private final QueryParam<Integer> timeout = new QueryParam<Integer>("timeout", this);

    public GetUpdatesQuery(TamTamClient client) {
        super(client, "/me/updates", null, UpdateList.class, Method.GET);
    }

    public GetUpdatesQuery limit(Integer value) {
        this.limit.setValue(value);
        return this;
    }
    public GetUpdatesQuery timeout(Integer value) {
        this.timeout.setValue(value);
        return this;
    }
}
