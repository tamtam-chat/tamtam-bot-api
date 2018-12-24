package chat.tamtam.botapi;

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

import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.client.impl.OkHttpTransportClient;

import chat.tamtam.botapi.queries.AnswerOnCallbackQuery;
import chat.tamtam.botapi.model.CallbackAnswer;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.queries.EditMessageQuery;
import chat.tamtam.botapi.queries.GetChatQuery;
import chat.tamtam.botapi.queries.GetChatsQuery;
import chat.tamtam.botapi.queries.GetMessagesQuery;
import chat.tamtam.botapi.queries.GetSubscriptionsQuery;
import chat.tamtam.botapi.model.GetSubscriptionsResult;
import chat.tamtam.botapi.queries.GetUpdatesQuery;
import chat.tamtam.botapi.queries.GetUploadUrlQuery;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.queries.MyInfoQuery;
import chat.tamtam.botapi.model.NewMessage;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.queries.SendMessageQuery;
import chat.tamtam.botapi.model.SendMessageResult;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.queries.SubscribeQuery;
import chat.tamtam.botapi.model.SubscriptionRequestBody;
import chat.tamtam.botapi.queries.UnsubscribeQuery;
import chat.tamtam.botapi.model.UpdateList;
import chat.tamtam.botapi.model.UploadEndpoint;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.User;

import java.util.Objects;

public class TamTamBotAPI {
    private final TamTamClient client;

    public TamTamBotAPI(String accessToken, TamTamTransportClient transport, TamTamSerializer serializer) {
        this(new TamTamClient(accessToken, transport, serializer));
    }

    public TamTamBotAPI(TamTamClient client) {
        this.client = client;
    }

    public static TamTamBotAPI create(String accessToken) {
        Objects.requireNonNull(accessToken, "No access token given. Get it using https://tt.me/primebot");
        OkHttpTransportClient transport = new OkHttpTransportClient();
        JacksonSerializer serializer = new JacksonSerializer();
        return new TamTamBotAPI(accessToken, transport, serializer);
    }

    /**
    * Answer on callback
    * This method should be called to send an answer after a user has clicked the button. The answer may be an updated message or a one-time user notification.
    * @param callbackAnswer  (required)
    * @param callbackId Identifies a button clicked by user. Bot receives this identifier after user pressed button as part of &#x60;MessageCallbackUpdate&#x60; (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public AnswerOnCallbackQuery answerOnCallback(CallbackAnswer callbackAnswer, String callbackId) throws ClientException { 
        if (callbackId == null) {
            throw new ClientException(400, "Missing the required parameter 'callback_id' when calling answerOnCallback");
        }
    
        if (callbackAnswer == null) {
            throw new ClientException(400, "Missing the required request body when calling answerOnCallback");
        }
    
        return new AnswerOnCallbackQuery(client, callbackAnswer, callbackId);
    }

    /**
    * Edit message
    * Updated message should be sent as &#x60;NewMessage&#x60; in a request body. In case &#x60;attachments&#x60; field is &#x60;null&#x60;, the current message attachments won’t be changed. In case of sending an empty list in this field, all attachments will be deleted.
    * @param newMessageBody  (required)
    * @param messageId Editing message identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public EditMessageQuery editMessage(NewMessageBody newMessageBody, Long messageId) throws ClientException { 
        if (messageId == null) {
            throw new ClientException(400, "Missing the required parameter 'message_id' when calling editMessage");
        }
    
        if (newMessageBody == null) {
            throw new ClientException(400, "Missing the required request body when calling editMessage");
        }
    
        return new EditMessageQuery(client, newMessageBody, messageId);
    }

    /**
    * Get chat
    * Returns info about chat.
    * @param chatId Requested chat identifier (required)
    * @return {@link Chat}
    * @throws ClientException if fails to make API call
    */
    public GetChatQuery getChat(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new ClientException(400, "Missing the required parameter 'chat_id' when calling getChat");
        }
    
        return new GetChatQuery(client, chatId);
    }

    /**
    * Get all chats
    * Returns information about chats that bot participated in: a result list and marker points to the next page.
    * @return {@link ChatList}
    */
    public GetChatsQuery getChats() { 
        return new GetChatsQuery(client);
    }

    /**
    * Get messages
    * Returns messages in chat: result page and marker referencing to the next page
    * @param chatId Chat identifier (required)
    * @return {@link MessageList}
    * @throws ClientException if fails to make API call
    */
    public GetMessagesQuery getMessages(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new ClientException(400, "Missing the required parameter 'chat_id' when calling getMessages");
        }
    
        return new GetMessagesQuery(client, chatId);
    }

    /**
    * Get subscriptions
    * In case your bot gets data via WebHook, the method returns list of all subscriptions.
    * @return {@link GetSubscriptionsResult}
    */
    public GetSubscriptionsQuery getSubscriptions() { 
        return new GetSubscriptionsQuery(client);
    }

    /**
    * Get updates
    * You can use this method for getting updates in case your bot is not subscribed to WebHook. The method based on long polling.
    * @return {@link UpdateList}
    */
    public GetUpdatesQuery getUpdates() { 
        return new GetUpdatesQuery(client);
    }

    /**
    * Get upload URL
    * Returns the URL for the subsequent file upload.  For example, you can upload it via curl: &#x60;&#x60;&#x60; curl -i -X POST      -H \&quot;Content-Type: multipart/form-data\&quot;      -F \&quot;data&#x3D;@movie.mp4\&quot; \&quot;%UPLOAD_URL%\&quot; &#x60;&#x60;&#x60;  Two types of an upload are supported:  - single request upload (multipart request) - and resumable upload.   ##### Multipart upload  This type of upload is a simpler one but it is less reliable and agile. If a &#x60;Content-Type&#x60;: multipart/form-data header is passed in a request our service indicates upload type as a simple single request upload.  This type of an upload has some restrictions:  - Max. file size - 2 Gb  - Only one file per request can be uploaded - No possibility to restart stopped / failed upload  ##### Resumable upload If &#x60;Content-Type&#x60; header value is not equal to &#x60;multipart/form-data&#x60; our service indicated upload type as a resumable upload. With a &#x60;Content-Range&#x60; header current file chunk range and complete file size can be passed. If a network error has happened or upload was stopped you can continue to upload a file from the last successfully uploaded file chunk. You can request the last known byte of uploaded file from server and continue to upload a file.   ##### Get upload status To GET an upload status you simply need to perform HTTP-GET request to a file upload URL. Our service will respond with current upload status, complete file size and last known uploaded byte. This data can be used to complete stopped upload if something went wrong. If &#x60;REQUESTED_RANGE_NOT_SATISFIABLE&#x60; or &#x60;INTERNAL_SERVER_ERROR&#x60; status was returned it is a good point to try to restart an upload
    * @param type Uploaded file type: photo, audio, video, file (required)
    * @return {@link UploadEndpoint}
    * @throws ClientException if fails to make API call
    */
    public GetUploadUrlQuery getUploadUrl(UploadType type) throws ClientException { 
        if (type == null) {
            throw new ClientException(400, "Missing the required parameter 'type' when calling getUploadUrl");
        }
    
        return new GetUploadUrlQuery(client, type);
    }

    /**
    * Get current bot info
    * Returns info about current bot. Current bot can be identified by access token. Method returns bot identifier, name and avatar (if any).
    * @return {@link User}
    */
    public MyInfoQuery myInfo() { 
        return new MyInfoQuery(client);
    }

    /**
    * Send message
    * Sends a message to a chat. Use object &#x60;NewMessage&#x60; for request body. As a result for this method new message ID returns.
    * @param newMessage  (required)
    * @return {@link SendMessageResult}
    * @throws ClientException if fails to make API call
    */
    public SendMessageQuery sendMessage(NewMessage newMessage) throws ClientException { 
        if (newMessage == null) {
            throw new ClientException(400, "Missing the required request body when calling sendMessage");
        }
    
        return new SendMessageQuery(client, newMessage);
    }

    /**
    * Subscribe
    * Subscribes bot to receive updates via WebHook. After calling this method, the bot will receive notifications about new events in chat rooms at the specified URL
    * @param subscriptionRequestBody  (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public SubscribeQuery subscribe(SubscriptionRequestBody subscriptionRequestBody) throws ClientException { 
        if (subscriptionRequestBody == null) {
            throw new ClientException(400, "Missing the required request body when calling subscribe");
        }
    
        return new SubscribeQuery(client, subscriptionRequestBody);
    }

    /**
    * Unsubscribe
    * Unsubscribes bot from receiving updates via WebHook. After calling the method, the bot stops receiving notifications about new events. Notification via the long-poll API becomes available for the bot
    * @param subscriptionRequestBody  (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public UnsubscribeQuery unsubscribe(SubscriptionRequestBody subscriptionRequestBody) throws ClientException { 
        if (subscriptionRequestBody == null) {
            throw new ClientException(400, "Missing the required request body when calling unsubscribe");
        }
    
        return new UnsubscribeQuery(client, subscriptionRequestBody);
    }
}
