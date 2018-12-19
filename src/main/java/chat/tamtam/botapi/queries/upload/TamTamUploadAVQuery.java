package chat.tamtam.botapi.queries.upload;

import java.io.File;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.model.UploadedInfo;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadAVQuery extends TamTamUploadQuery<UploadedInfo> {
    public TamTamUploadAVQuery(TamTamClient tamTamClient, String url, File file) {
        super(tamTamClient, UploadedInfo.class, url, file);
    }
}
