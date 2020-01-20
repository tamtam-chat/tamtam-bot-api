package chat.tamtam.botapi.queries.upload;

import java.io.File;
import java.util.concurrent.Future;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.exceptions.TransportClientException;

/**
 * @author alexandrchuprin
 */
class FileUploadExec implements UploadExec {
    private final File file;
    private final String url;

    FileUploadExec(String url, File file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public Future<ClientResponse> newCall(TamTamTransportClient transportClient) throws ClientException,
            InterruptedException {

        try {
            return transportClient.post(url, file);
        } catch (TransportClientException e) {
            throw new ClientException(e);
        }
    }
}
