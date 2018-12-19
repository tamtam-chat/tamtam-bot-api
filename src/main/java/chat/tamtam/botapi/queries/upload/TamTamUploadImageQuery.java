package chat.tamtam.botapi.queries.upload;

import java.io.File;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.model.PhotoTokens;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadImageQuery extends TamTamUploadQuery<PhotoTokens> {
    public TamTamUploadImageQuery(TamTamClient tamTamClient, String url, File file) {
        super(tamTamClient, PhotoTokens.class, url, file);
    }
}
