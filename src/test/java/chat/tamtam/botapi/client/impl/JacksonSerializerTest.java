package chat.tamtam.botapi.client.impl;

import org.junit.Test;

import chat.tamtam.botapi.exceptions.SerializationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class JacksonSerializerTest {
    JacksonSerializer serializer = new JacksonSerializer();

    @Test(expected = SerializationException.class)
    public void shoudlThrowSerializationException() throws Exception {
        serializer.serialize(new NotSerializableClass());
    }

    @Test
    public void shouldReturnNullOnEmptyInput() throws Exception {
        assertThat(serializer.deserialize((byte[]) null, Object.class), is(nullValue()));
        assertThat(serializer.deserialize("", Object.class), is(nullValue()));
    }

    private static class NotSerializableClass {
        private final NotSerializableClass self = this;

        @Override
        public String toString() {
            return self.getClass().getName();
        }
    }
}