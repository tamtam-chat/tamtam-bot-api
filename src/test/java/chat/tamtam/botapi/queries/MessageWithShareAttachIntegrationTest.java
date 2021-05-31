package chat.tamtam.botapi.queries;

import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import chat.tamtam.botapi.TamTamIntegrationTest;
import chat.tamtam.botapi.model.Attachment;
import chat.tamtam.botapi.model.AttachmentRequest;
import chat.tamtam.botapi.model.FailByDefaultAttachmentVisitor;
import chat.tamtam.botapi.model.Message;
import chat.tamtam.botapi.model.NewMessageBody;
import chat.tamtam.botapi.model.ShareAttachment;
import chat.tamtam.botapi.model.ShareAttachmentPayload;
import chat.tamtam.botapi.model.ShareAttachmentRequest;

/**
 * @author alexandrchuprin
 */
public class MessageWithShareAttachIntegrationTest extends TamTamIntegrationTest {
    @Test
    public void shouldSendMessageWithShareAttachment() throws Exception {
        ShareAttachmentPayload payload = new ShareAttachmentPayload();
        payload.url("https://tamtam.chat/");
        AttachmentRequest attach = new ShareAttachmentRequest(payload);
        NewMessageBody newMessage = new NewMessageBody(randomText(), Collections.singletonList(attach), null);
        List<Message> messages = send(newMessage, getChatsForSend());
        Attachment attachment = messages.get(0).getBody().getAttachments().get(0);
        attachment.visit(new FailByDefaultAttachmentVisitor() {
            @Override
            public void visit(ShareAttachment model) {
                // send same attach by token
                ShareAttachmentPayload payload = new ShareAttachmentPayload();
                payload.token(model.getPayload().getToken());
                AttachmentRequest attach = new ShareAttachmentRequest(payload);
                NewMessageBody newMessage = new NewMessageBody(randomText(), Collections.singletonList(attach), null);
                try {
                    send(newMessage, getChatsForSend());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
