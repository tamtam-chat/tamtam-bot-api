package chat.tamtam.botapi.queries.upload;

import java.io.File;
import java.util.concurrent.Future;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.TransportClientException;
import chat.tamtam.botapi.queries.TamTamQuery;

/**
 * @author alexandrchuprin
 */
public abstract class TamTamUploadQuery<T> extends TamTamQuery<T> {
    private final TamTamClient tamTamClient;
    private final String url;
    private final File file;

    public TamTamUploadQuery(TamTamClient tamTamClient, Class<T> responseType, String url, File file) {
        super(tamTamClient, url, responseType);
        this.tamTamClient = tamTamClient;
        this.url = url;
        this.file = file;
    }

    @Override
    protected Future<ClientResponse> call() throws ClientException {
        try {
            return tamTamClient.getTransport().post(url, file);
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }
    }
}
