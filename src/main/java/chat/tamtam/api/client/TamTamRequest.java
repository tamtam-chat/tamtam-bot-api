package chat.tamtam.api.client;

import java.util.concurrent.Future;

import chat.tamtam.api.client.exceptions.APIException;
import chat.tamtam.api.client.exceptions.ClientException;

/**
 * @author alexandrchuprin
 */
public interface TamTamRequest<T> {
    T get() throws APIException, ClientException;

    Future<T> enqueue();
}
