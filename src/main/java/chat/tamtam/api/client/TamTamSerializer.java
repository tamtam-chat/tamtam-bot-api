package chat.tamtam.api.client;

import org.jetbrains.annotations.Nullable;

import chat.tamtam.api.client.exceptions.APIException;
import chat.tamtam.api.client.exceptions.ClientException;

/**
 * @author alexandrchuprin
 */
public interface TamTamSerializer {
    @Nullable
    byte[] serialize(@Nullable Object object) throws ClientException;

    @Nullable
    <T> T deserialize(@Nullable String data, Class<T> responseType) throws APIException, ClientException;
}
