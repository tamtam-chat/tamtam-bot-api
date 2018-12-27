package chat.tamtam.botapi.model;

import org.junit.Test;

import chat.tamtam.botapi.SerializationTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class PhotoAttachmentRequestTest extends SerializationTest {
    @Test
    public void should_Deserialize() throws Exception {
        String json = "{\"type\":\"image\",\"payload\":{\"photos\":{\"somehash\":{\"token\":\"sometoken\"}}},\"type\":\"image\"}";
        PhotoAttachmentRequest attachmentRequest = testDeserialization(json, PhotoAttachmentRequest.class);
        assertThat(attachmentRequest.getPayload(), is(notNullValue()));
        assertThat(attachmentRequest.getPayload().getPhotos(), is(notNullValue()));
    }
}