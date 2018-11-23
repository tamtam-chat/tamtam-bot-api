package chat.tamtam.api.requests;

import java.io.Serializable;
import java.util.List;

import chat.tamtam.api.objects.Recipient;
import chat.tamtam.api.requests.attachment.PhotoAttachmentRequest;

public class ChatControl implements Serializable {
    public String title;
    public PhotoAttachmentRequest icon;
    public String leave;
    public List<Recipient> add_members;
    public Recipient remove_member;
}
