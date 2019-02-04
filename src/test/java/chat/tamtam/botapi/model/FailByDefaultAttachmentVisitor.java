package chat.tamtam.botapi.model;

import static org.junit.Assert.fail;

/**
 * @author alexandrchuprin
 */
public class FailByDefaultAttachmentVisitor implements Attachment.Visitor {
    @Override
    public void visit(PhotoAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(VideoAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(AudioAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(FileAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(StickerAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(ContactAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(InlineKeyboardAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visit(ShareAttachment model) {
        fail("Should not happens");
    }

    @Override
    public void visitDefault(Attachment model) {
        fail("Should not happens");
    }
}
