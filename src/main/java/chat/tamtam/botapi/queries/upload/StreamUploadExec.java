package chat.tamtam.botapi.queries.upload;

import java.io.InputStream;
import java.util.concurrent.Future;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.TransportClientException;

/**
 * @author alexandrchuprin
 */
class StreamUploadExec implements UploadExec {
    private final String fileName;
    private final InputStream input;
    private final String url;

    StreamUploadExec(String url, String fileName, InputStream input) {
        this.url = url;
        this.fileName = fileName;
        this.input = input;
    }

    @Override
    public Future<ClientResponse> newCall(TamTamTransportClient transportClient) throws ClientException {
        try {
            return transportClient.post(url, fileName, input);
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }
    }
}
