/*
 * ---------------------------------------------------------------------
 * TamTam chat Bot API
 * ---------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ---------------------------------------------------------------------
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
 * ---------------------------------------------------------------------
 */

package chat.tamtam.botapi.client;

import java.util.Objects;

import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;

/**
 * @author alexandrchuprin
 */
public class TamTamClient {
    private static final String ENDPOINT = "https://botapi.tamtam.chat";
    static final String ENDPOINT_ENV_VAR_NAME = "TAMTAM_BOTAPI_ENDPOINT";

    private final String accessToken;
    private final TamTamTransportClient transport;
    private final TamTamSerializer serializer;
    private final String endpoint;

    public TamTamClient(String accessToken, TamTamTransportClient transport, TamTamSerializer serializer) {
        this.endpoint = createEndpoint();
        this.accessToken = Objects.requireNonNull(accessToken, "accessToken");
        this.transport = Objects.requireNonNull(transport, "transport");
        this.serializer = Objects.requireNonNull(serializer, "serializer");
    }

    public static TamTamClient create(String accessToken) {
        Objects.requireNonNull(accessToken, "No access token given. Get it using https://tt.me/primebot");
        OkHttpTransportClient transport = new OkHttpTransportClient();
        JacksonSerializer serializer = new JacksonSerializer();
        return new TamTamClient(accessToken, transport, serializer);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TamTamSerializer getSerializer() {
        return serializer;
    }

    public TamTamTransportClient getTransport() {
        return transport;
    }

    private String createEndpoint() {
        String env = getEnvironment(ENDPOINT_ENV_VAR_NAME);
        if (env != null) {
            return env;
        }

        return System.getProperty("tamtam.botapi.endpoint", ENDPOINT);
    }

    String getEnvironment(String name) {
        return System.getenv(name);
    }
}
