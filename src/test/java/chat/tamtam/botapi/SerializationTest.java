package chat.tamtam.botapi;

import java.nio.charset.StandardCharsets;

import chat.tamtam.botapi.client.TamTamSerializer;
import chat.tamtam.botapi.client.impl.JacksonSerializer;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class SerializationTest {
    private TamTamSerializer serializer = new JacksonSerializer();

    protected <T> T testDeserialization(String input, Class<T> clazz) throws APIException, ClientException {
        T object = serializer.deserialize(input, clazz);
        assertThat(new String(serializer.serialize(object), StandardCharsets.UTF_8), is(input));
        return object;
    }
}
