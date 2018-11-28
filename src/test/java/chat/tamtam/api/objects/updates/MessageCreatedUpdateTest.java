package chat.tamtam.api.objects.updates;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class MessageCreatedUpdateTest {
    private static final ObjectMapper JACKSON = new ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(FIELD, JsonAutoDetect.Visibility.ANY);

    @Test
    public void should_Deserialize_And_Serialize() throws IOException {
        String json = "{\"update_type\":\"message_created\",\"sender\":{\"user_id\":3841143639739," +
                "\"name\":\"alexander chuprin\"},\"recipient\":{\"chat_id\":2802677853}," +
                "\"message\":{\"mid\":\"mid.00000000a70d785d01675561bf5352fa\",\"seq\":101143394949223162," +
                "\"text\":\"text\"},\"timestamp\":1543325728595}";

        MessageCreatedUpdate update = JACKSON.readValue(json, MessageCreatedUpdate.class);
        String result = JACKSON.writeValueAsString(update);

        assertThat(json, is(result));
    }
}