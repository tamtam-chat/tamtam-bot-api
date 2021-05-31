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

package chat.tamtam.botapi;

import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.RequiredParameterMissingException;

import chat.tamtam.botapi.model.ActionRequestBody;
import chat.tamtam.botapi.queries.AddMembersQuery;
import chat.tamtam.botapi.queries.AnswerOnCallbackQuery;
import chat.tamtam.botapi.model.BotInfo;
import chat.tamtam.botapi.model.BotPatch;
import chat.tamtam.botapi.model.CallbackAnswer;
import chat.tamtam.botapi.model.Chat;
import chat.tamtam.botapi.model.ChatList;
import chat.tamtam.botapi.model.ChatMember;
import chat.tamtam.botapi.model.ChatMembersList;
import chat.tamtam.botapi.model.ChatPatch;
import chat.tamtam.botapi.queries.ConstructQuery;
import chat.tamtam.botapi.model.ConstructorAnswer;
import chat.tamtam.botapi.queries.DeleteMessageQuery;
import chat.tamtam.botapi.queries.EditChatQuery;
import chat.tamtam.botapi.queries.EditMessageQuery;
import chat.tamtam.botapi.queries.EditMyInfoQuery;
import chat.tamtam.botapi.queries.GetAdminsQuery;
import chat.tamtam.botapi.queries.GetChatByLinkQuery;
import chat.tamtam.botapi.queries.GetChatQuery;
import chat.tamtam.botapi.queries.GetChatsQuery;
import chat.tamtam.botapi.queries.GetMembersQuery;
import chat.tamtam.botapi.queries.GetMembershipQuery;
import chat.tamtam.botapi.queries.GetMessageByIdQuery;
import chat.tamtam.botapi.queries.GetMessagesQuery;
import chat.tamtam.botapi.queries.GetMyInfoQuery;
import chat.tamtam.botapi.queries.GetPinnedMessageQuery;
import chat.tamtam.botapi.model.GetPinnedMessageResult;
import chat.tamtam.botapi.queries.GetSubscriptionsQuery;
import chat.tamtam.botapi.model.GetSubscriptionsResult;
import chat.tamtam.botapi.queries.GetUpdatesQuery;
import chat.tamtam.botapi.queries.GetUploadUrlQuery;
import chat.tamtam.botapi.queries.LeaveChatQuery;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.MessageList;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.PinMessageBody;
import chat.tamtam.botapi.queries.PinMessageQuery;
import chat.tamtam.botapi.queries.RemoveMemberQuery;
import chat.tamtam.botapi.queries.SendActionQuery;
import chat.tamtam.botapi.queries.SendMessageQuery;
import chat.tamtam.botapi.model.SendMessageResult;
import java.util.Set;
import chat.tamtam.botapi.model.SimpleQueryResult;
import chat.tamtam.botapi.queries.SubscribeQuery;
import chat.tamtam.botapi.model.SubscriptionRequestBody;
import chat.tamtam.botapi.queries.UnpinMessageQuery;
import chat.tamtam.botapi.queries.UnsubscribeQuery;
import chat.tamtam.botapi.model.UpdateList;
import chat.tamtam.botapi.model.UploadEndpoint;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UserIdsList;

public class TamTamBotAPI {
    final TamTamClient client;

    public TamTamBotAPI(String accessToken, TamTamTransportClient transport, TamTamSerializer serializer) {
        this(new TamTamClient(accessToken, transport, serializer));
    }

    public TamTamBotAPI(TamTamClient client) {
        this.client = client;
    }

    public static TamTamBotAPI create(String accessToken) {
        return new TamTamBotAPI(TamTamClient.create(accessToken));
    }

    /**
    * Add members
    * Adds members to chat. Additional permissions may require.
    * @param userIdsList  (required)
    * @param chatId Chat identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public AddMembersQuery addMembers(UserIdsList userIdsList, Long chatId) throws ClientException { 
        if (userIdsList == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling addMembers");
        }

        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling addMembers");
        }

        return new AddMembersQuery(client, userIdsList, chatId);
    }

    /**
    * Answer on callback
    * This method should be called to send an answer after a user has clicked the button. The answer may be an updated message or/and a one-time user notification.
    * @param callbackAnswer  (required)
    * @param callbackId Identifies a button clicked by user. Bot receives this identifier after user pressed button as part of &#x60;MessageCallbackUpdate&#x60; (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public AnswerOnCallbackQuery answerOnCallback(CallbackAnswer callbackAnswer, String callbackId) throws ClientException { 
        if (callbackId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'callback_id' when calling answerOnCallback");
        }

        if (callbackAnswer == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling answerOnCallback");
        }

        return new AnswerOnCallbackQuery(client, callbackAnswer, callbackId);
    }

    /**
    * Construct message
    * Sends answer on construction request. Answer can contain any prepared message and/or keyboard to help user interact with bot.
    * @param constructorAnswer  (required)
    * @param sessionId Constructor session identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public ConstructQuery construct(ConstructorAnswer constructorAnswer, String sessionId) throws ClientException { 
        if (sessionId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'session_id' when calling construct");
        }

        if (constructorAnswer == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling construct");
        }

        return new ConstructQuery(client, constructorAnswer, sessionId);
    }

    /**
    * Delete message
    * Deletes message in a dialog or in a chat if bot has permission to delete messages.
    * @param messageId Deleting message identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public DeleteMessageQuery deleteMessage(String messageId) throws ClientException { 
        if (messageId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'message_id' when calling deleteMessage");
        }

        return new DeleteMessageQuery(client, messageId);
    }

    /**
    * Edit chat info
    * Edits chat info: title, icon, etc…
    * @param chatPatch  (required)
    * @param chatId Chat identifier (required)
    * @return {@link Chat}
    * @throws ClientException if fails to make API call
    */
    public EditChatQuery editChat(ChatPatch chatPatch, Long chatId) throws ClientException { 
        if (chatPatch == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling editChat");
        }

        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling editChat");
        }

        return new EditChatQuery(client, chatPatch, chatId);
    }

    /**
    * Edit message
    * Updated message should be sent as &#x60;NewMessageBody&#x60; in a request body. In case &#x60;attachments&#x60; field is &#x60;null&#x60;, the current message attachments won’t be changed. In case of sending an empty list in this field, all attachments will be deleted.
    * @param newMessageBody  (required)
    * @param messageId Editing message identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public EditMessageQuery editMessage(NewMessageBody newMessageBody, String messageId) throws ClientException { 
        if (messageId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'message_id' when calling editMessage");
        }

        if (newMessageBody == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling editMessage");
        }

        return new EditMessageQuery(client, newMessageBody, messageId);
    }

    /**
    * Edit current bot info
    * Edits current bot info. Fill only the fields you want to update. All remaining fields will stay untouched
    * @param botPatch  (required)
    * @return {@link BotInfo}
    * @throws ClientException if fails to make API call
    */
    public EditMyInfoQuery editMyInfo(BotPatch botPatch) throws ClientException { 
        if (botPatch == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling editMyInfo");
        }

        return new EditMyInfoQuery(client, botPatch);
    }

    /**
    * Get chat admins
    * Returns all chat administrators. Bot must be **administrator** in requested chat.
    * @param chatId Chat identifier (required)
    * @return {@link ChatMembersList}
    * @throws ClientException if fails to make API call
    */
    public GetAdminsQuery getAdmins(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling getAdmins");
        }

        return new GetAdminsQuery(client, chatId);
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
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling getChat");
        }

        return new GetChatQuery(client, chatId);
    }

    /**
    * Get chat by link
    * Returns chat/channel information by its public link or dialog with user by username
    * @param chatLink Public chat link or username (required)
    * @return {@link Chat}
    * @throws ClientException if fails to make API call
    */
    public GetChatByLinkQuery getChatByLink(String chatLink) throws ClientException { 
        if (chatLink == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatLink' when calling getChatByLink");
        }

        return new GetChatByLinkQuery(client, chatLink);
    }

    /**
    * Get all chats
    * Returns information about chats that bot participated in: a result list and marker points to the next page
    * @return {@link ChatList}
    */
    public GetChatsQuery getChats() { 
        return new GetChatsQuery(client);
    }

    /**
    * Get members
    * Returns users participated in chat.
    * @param chatId Chat identifier (required)
    * @return {@link ChatMembersList}
    * @throws ClientException if fails to make API call
    */
    public GetMembersQuery getMembers(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling getMembers");
        }

        return new GetMembersQuery(client, chatId);
    }

    /**
    * Get chat membership
    * Returns chat membership info for current bot
    * @param chatId Chat identifier (required)
    * @return {@link ChatMember}
    * @throws ClientException if fails to make API call
    */
    public GetMembershipQuery getMembership(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling getMembership");
        }

        return new GetMembershipQuery(client, chatId);
    }

    /**
    * Get message
    * Returns single message by its identifier.
    * @param messageId Message identifier (&#x60;mid&#x60;) to get single message in chat (required)
    * @return {@link Message}
    * @throws ClientException if fails to make API call
    */
    public GetMessageByIdQuery getMessageById(String messageId) throws ClientException { 
        if (messageId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'messageId' when calling getMessageById");
        }

        return new GetMessageByIdQuery(client, messageId);
    }

    /**
    * Get messages
    * Returns messages in chat: result page and marker referencing to the next page. Messages traversed in reverse direction so the latest message in chat will be first in result array. Therefore if you use &#x60;from&#x60; and &#x60;to&#x60; parameters, &#x60;to&#x60; must be **less than** &#x60;from&#x60;
    * @return {@link MessageList}
    */
    public GetMessagesQuery getMessages() { 
        return new GetMessagesQuery(client);
    }

    /**
    * Get current bot info
    * Returns info about current bot. Current bot can be identified by access token. Method returns bot identifier, name and avatar (if any)
    * @return {@link BotInfo}
    */
    public GetMyInfoQuery getMyInfo() { 
        return new GetMyInfoQuery(client);
    }

    /**
    * Get pinned message
    * Get pinned message in chat or channel.
    * @param chatId Chat identifier to get its pinned message (required)
    * @return {@link GetPinnedMessageResult}
    * @throws ClientException if fails to make API call
    */
    public GetPinnedMessageQuery getPinnedMessage(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling getPinnedMessage");
        }

        return new GetPinnedMessageQuery(client, chatId);
    }

    /**
    * Get subscriptions
    * In case your bot gets data via WebHook, the method returns list of all subscriptions
    * @return {@link GetSubscriptionsResult}
    */
    public GetSubscriptionsQuery getSubscriptions() { 
        return new GetSubscriptionsQuery(client);
    }

    /**
    * Get updates
    * You can use this method for getting updates in case your bot is not subscribed to WebHook. The method is based on long polling.  Every update has its own sequence number. &#x60;marker&#x60; property in response points to the next upcoming update.  All previous updates are considered as *committed* after passing &#x60;marker&#x60; parameter. If &#x60;marker&#x60; parameter is **not passed**, your bot will get all updates happened after the last commitment.
    * @return {@link UpdateList}
    */
    public GetUpdatesQuery getUpdates() { 
        return new GetUpdatesQuery(client);
    }

    /**
    * Get upload URL
    * Returns the URL for the subsequent file upload.  For example, you can upload it via curl:  &#x60;&#x60;&#x60;curl -i -X POST   -H \&quot;Content-Type: multipart/form-data\&quot;   -F \&quot;data&#x3D;@movie.mp4\&quot; \&quot;%UPLOAD_URL%\&quot;&#x60;&#x60;&#x60;  Two types of an upload are supported: - single request upload (multipart request) - and resumable upload.  ##### Multipart upload This type of upload is a simpler one but it is less reliable and agile. If a &#x60;Content-Type&#x60;: multipart/form-data header is passed in a request our service indicates upload type as a simple single request upload.  This type of an upload has some restrictions:  - Max. file size - 2 Gb - Only one file per request can be uploaded - No possibility to restart stopped / failed upload  ##### Resumable upload If &#x60;Content-Type&#x60; header value is not equal to &#x60;multipart/form-data&#x60; our service indicated upload type as a resumable upload. With a &#x60;Content-Range&#x60; header current file chunk range and complete file size can be passed. If a network error has happened or upload was stopped you can continue to upload a file from the last successfully uploaded file chunk. You can request the last known byte of uploaded file from server and continue to upload a file.  ##### Get upload status To GET an upload status you simply need to perform HTTP-GET request to a file upload URL. Our service will respond with current upload status, complete file size and last known uploaded byte. This data can be used to complete stopped upload if something went wrong. If &#x60;REQUESTED_RANGE_NOT_SATISFIABLE&#x60; or &#x60;INTERNAL_SERVER_ERROR&#x60; status was returned it is a good point to try to restart an upload
    * @param type Uploaded file type: photo, audio, video, file (required)
    * @return {@link UploadEndpoint}
    * @throws ClientException if fails to make API call
    */
    public GetUploadUrlQuery getUploadUrl(UploadType type) throws ClientException { 
        if (type == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'type' when calling getUploadUrl");
        }

        return new GetUploadUrlQuery(client, type);
    }

    /**
    * Leave chat
    * Removes bot from chat members.
    * @param chatId Chat identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public LeaveChatQuery leaveChat(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling leaveChat");
        }

        return new LeaveChatQuery(client, chatId);
    }

    /**
    * Pin message
    * Pins message in chat or channel.
    * @param pinMessageBody  (required)
    * @param chatId Chat identifier where message should be pinned (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public PinMessageQuery pinMessage(PinMessageBody pinMessageBody, Long chatId) throws ClientException { 
        if (pinMessageBody == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling pinMessage");
        }

        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling pinMessage");
        }

        return new PinMessageQuery(client, pinMessageBody, chatId);
    }

    /**
    * Remove member
    * Removes member from chat. Additional permissions may require.
    * @param chatId Chat identifier (required)
    * @param userId User id to remove from chat (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public RemoveMemberQuery removeMember(Long chatId, Long userId) throws ClientException { 
        if (userId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'user_id' when calling removeMember");
        }

        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling removeMember");
        }

        return new RemoveMemberQuery(client, chatId, userId);
    }

    /**
    * Send action
    * Send bot action to chat.
    * @param actionRequestBody  (required)
    * @param chatId Chat identifier (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public SendActionQuery sendAction(ActionRequestBody actionRequestBody, Long chatId) throws ClientException { 
        if (actionRequestBody == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling sendAction");
        }

        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling sendAction");
        }

        return new SendActionQuery(client, actionRequestBody, chatId);
    }

    /**
    * Send message
    * Sends a message to a chat. As a result for this method new message identifier returns. ### Attaching media Attaching media to messages is a three-step process.  At first step, you should [obtain a URL to upload](#operation/getUploadUrl) your media files.  At the second, you should upload binary of appropriate format to URL you obtained at the previous step. See [upload](https://dev.tamtam.chat/#operation/getUploadUrl) section for details.  Finally, if the upload process was successful, you will receive JSON-object in a response body.  Use this object to create attachment. Construct an object with two properties: - &#x60;type&#x60; with the value set to appropriate media type - and &#x60;payload&#x60; filled with the JSON you&#39;ve got.  For example, you can attach a video to message this way:  1. Get URL to upload. Execute following: &#x60;&#x60;&#x60;shell curl -X POST &#39;https://botapi.tamtam.chat/uploads?access_token&#x3D;%access_token%&amp;type&#x3D;video&#39; &#x60;&#x60;&#x60; As the result it will return URL for the next step. &#x60;&#x60;&#x60;json {     \&quot;url\&quot;: \&quot;http://vu.mycdn.me/upload.do…\&quot; } &#x60;&#x60;&#x60;  2. Use this url to upload your binary: &#x60;&#x60;&#x60;shell curl -i -X POST   -H \&quot;Content-Type: multipart/form-data\&quot;   -F \&quot;data&#x3D;@movie.mp4\&quot; \&quot;http://vu.mycdn.me/upload.do…\&quot; &#x60;&#x60;&#x60; As the result it will return JSON you can attach to message: &#x60;&#x60;&#x60;json   {     \&quot;token\&quot;: \&quot;_3Rarhcf1PtlMXy8jpgie8Ai_KARnVFYNQTtmIRWNh4\&quot;   } &#x60;&#x60;&#x60; 3. Send message with attach: &#x60;&#x60;&#x60;json {     \&quot;text\&quot;: \&quot;Message with video\&quot;,     \&quot;attachments\&quot;: [         {             \&quot;type\&quot;: \&quot;video\&quot;,             \&quot;payload\&quot;: {                 \&quot;token\&quot;: \&quot;_3Rarhcf1PtlMXy8jpgie8Ai_KARnVFYNQTtmIRWNh4\&quot;             }         }     ] } &#x60;&#x60;&#x60;  **Important notice**:  It may take time for the server to process your file (audio/video or any binary). While a file is not processed you can&#39;t attach it. It means the last step will fail with &#x60;400&#x60; error. Try to send a message again until you&#39;ll get a successful result.
    * @param newMessageBody  (required)
    * @return {@link SendMessageResult}
    * @throws ClientException if fails to make API call
    */
    public SendMessageQuery sendMessage(NewMessageBody newMessageBody) throws ClientException { 
        if (newMessageBody == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling sendMessage");
        }

        return new SendMessageQuery(client, newMessageBody);
    }

    /**
    * Subscribe
    * Subscribes bot to receive updates via WebHook. After calling this method, the bot will receive notifications about new events in chat rooms at the specified URL.  Your server **must** be listening on one of the following ports: **80, 8080, 443, 8443, 16384-32383**
    * @param subscriptionRequestBody  (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public SubscribeQuery subscribe(SubscriptionRequestBody subscriptionRequestBody) throws ClientException { 
        if (subscriptionRequestBody == null) {
            throw new RequiredParameterMissingException("Missing the required request body when calling subscribe");
        }

        return new SubscribeQuery(client, subscriptionRequestBody);
    }

    /**
    * Unpin message
    * Unpins message in chat or channel.
    * @param chatId Chat identifier to remove pinned message (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public UnpinMessageQuery unpinMessage(Long chatId) throws ClientException { 
        if (chatId == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'chatId' when calling unpinMessage");
        }

        return new UnpinMessageQuery(client, chatId);
    }

    /**
    * Unsubscribe
    * Unsubscribes bot from receiving updates via WebHook. After calling the method, the bot stops receiving notifications about new events. Notification via the long-poll API becomes available for the bot
    * @param url URL to remove from WebHook subscriptions (required)
    * @return {@link SimpleQueryResult}
    * @throws ClientException if fails to make API call
    */
    public UnsubscribeQuery unsubscribe(String url) throws ClientException { 
        if (url == null) {
            throw new RequiredParameterMissingException("Missing the required parameter 'url' when calling unsubscribe");
        }

        return new UnsubscribeQuery(client, url);
    }
}
