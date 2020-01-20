package chat.tamtam.botapi.queries.upload;

import java.util.concurrent.Future;

import chat.tamtam.botapi.client.ClientResponse;
import chat.tamtam.botapi.client.TamTamTransportClient;
import chat.tamtam.botapi.exceptions.ClientException;

/**
 * @author alexandrchuprin
 */
public interface UploadExec {
    Future<ClientResponse> newCall(TamTamTransportClient transportClient) throws ClientException, InterruptedException;
}
