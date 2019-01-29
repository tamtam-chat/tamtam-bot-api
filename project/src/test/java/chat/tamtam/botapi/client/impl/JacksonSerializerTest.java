package chat.tamtam.botapi.client.impl;

import org.junit.Test;

import chat.tamtam.botapi.exceptions.SerializationException;

/**
 * @author alexandrchuprin
 */
public class JacksonSerializerTest {
    @Test(expected = SerializationException.class)
    public void shoudlThrowSerializationException() throws Exception {
        JacksonSerializer serializer = new JacksonSerializer();
        serializer.serialize(new NotSerializableClass());
    }

    private static class NotSerializableClass {
        private final NotSerializableClass self = this;

        @Override
        public String toString() {
            return self.getClass().getName();
        }
    }
}