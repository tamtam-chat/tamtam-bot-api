package chat.tamtam.api.client.impl;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import chat.tamtam.api.client.TamTamSerializer;
import chat.tamtam.api.client.exceptions.APIException;
import chat.tamtam.api.client.exceptions.ClientException;
import chat.tamtam.api.client.exceptions.ExceptionMapper;

/**
 * @author alexandrchuprin
 */
public class JacksonSerializer implements TamTamSerializer {
    private final ObjectMapper mapper;

    public JacksonSerializer() {
        this(new ObjectMapper());
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    public JacksonSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Nullable
    @Override
    public byte[] serialize(Object object) throws ClientException {
        if (object == null) {
            return null;
        }

        try {
            return mapper.writer().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new ClientException("Failed to serialize: " + object, e);
        }
    }

    @Nullable
    @Override
    public <T> T deserialize(String data, Class<T> responseType) throws ClientException, APIException {
        if (data == null) {
            return null;
        }

        try {
            ObjectReader reader = mapper.reader();
            JsonNode json = reader.readTree(data);
            if (json.has("error")) {
                throw ExceptionMapper.map(reader.readValue(json));
            }

            return reader.treeToValue(json, responseType);
        } catch (IOException e) {
            throw new ClientException("Failed to deserialize data: " + data, e);
        }
    }
}
