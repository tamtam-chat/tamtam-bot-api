package chat.tamtam.botapi.model;

import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultARVisitor implements AttachmentRequest.Visitor {
    @Override
    public void visit(PhotoAttachmentRequest model) {
        fail("Should not happens");

    }

    @Override
    public void visit(VideoAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visit(AudioAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visit(FileAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visit(StickerAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visit(ContactAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visit(InlineKeyboardAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visit(LocationAttachmentRequest model) {
        fail("Should not happens");
    }

    @Override
    public void visitDefault(AttachmentRequest model) {
        fail("Should not happens");
    }
}
