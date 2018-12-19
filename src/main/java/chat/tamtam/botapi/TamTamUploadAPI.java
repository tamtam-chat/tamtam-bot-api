package chat.tamtam.botapi;

import java.io.File;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.queries.upload.TamTamUploadAVQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadFileQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadImageQuery;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadAPI {
    private final TamTamClient client;

    public TamTamUploadAPI(TamTamClient client) {
        this.client = client;
    }

    public TamTamUploadFileQuery uploadFile(String url, File file) {
        return new TamTamUploadFileQuery(client, url, file);
    }

    public TamTamUploadImageQuery uploadImage(String url, File file) {
        return new TamTamUploadImageQuery(client, url, file);
    }

    public TamTamUploadAVQuery uploadAV(String url, File file) {
        return new TamTamUploadAVQuery(client, url, file);
    }
}
