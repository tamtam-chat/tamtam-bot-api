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

import chat.tamtam.botapi.client.TamTamClient;
import java.util.Set;import chat.tamtam.botapi.model.UpdateList;import java.util.Collection;
import static chat.tamtam.botapi.client.TamTamTransportClient.Method;

public class GetUpdatesQuery extends TamTamQuery<UpdateList> { 
    public final QueryParam<Integer> limit = new QueryParam<>("limit", this);
    public final QueryParam<Integer> timeout = new QueryParam<>("timeout", this);
    public final QueryParam<Long> marker = new QueryParam<>("marker", this);
    public final QueryParam<Collection<String>> types = new CollectionQueryParam<>("types", this);

    public GetUpdatesQuery(TamTamClient client) {
        super(client, "/updates", null, UpdateList.class, Method.GET);
    }

    public GetUpdatesQuery limit(Integer value) {
        this.limit.setValue(value);
        return this;
    }

    public GetUpdatesQuery timeout(Integer value) {
        this.timeout.setValue(value);
        return this;
    }

    public GetUpdatesQuery marker(Long value) {
        this.marker.setValue(value);
        return this;
    }

    public GetUpdatesQuery types(Set<String> value) {
        this.types.setValue(value);
        return this;
    }
}
