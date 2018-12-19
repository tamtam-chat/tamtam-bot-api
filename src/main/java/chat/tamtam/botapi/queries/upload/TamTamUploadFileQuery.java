package chat.tamtam.botapi.queries.upload;

import java.io.File;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.model.UploadedFileInfo;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadFileQuery extends TamTamUploadQuery<UploadedFileInfo> {
    public TamTamUploadFileQuery(TamTamClient tamTamClient, String url, File file) {
        super(tamTamClient, UploadedFileInfo.class, url, file);
    }
}
