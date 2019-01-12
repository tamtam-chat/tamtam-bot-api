package chat.tamtam.botapi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import chat.tamtam.botapi.model.PhotoTokens;
import chat.tamtam.botapi.model.UploadEndpoint;
import chat.tamtam.botapi.model.UploadType;
import chat.tamtam.botapi.model.UploadedFileInfo;
import chat.tamtam.botapi.queries.GetUploadUrlQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadFileQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadImageQuery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadAPITest extends TamTamIntegrationTest {
    @Test
    public void should_Upload_Image() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        GetUploadUrlQuery query = botAPI.getUploadUrl(UploadType.PHOTO);
        UploadEndpoint uploadEndpoint = query.execute();
        TamTamUploadImageQuery uploadQuery = uploadAPI.uploadImage(uploadEndpoint.getUrl(), file);
        PhotoTokens photoTokens = uploadQuery.execute();
        assertThat(photoTokens.getPhotos().size(), is(1));
        assertThat(photoTokens.getPhotos().values().iterator().next().getToken(), is(notNullValue()));
    }

    @Test
    public void should_Upload_File() throws Exception {
        String url = botAPI.getUploadUrl(UploadType.FILE).execute().getUrl();
        File file = Files.createTempFile("tt", ".bin").toFile();
        file.deleteOnExit();
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < 4096; i++) {
                outputStream.write(ThreadLocalRandom.current().nextInt(256));
            }
            outputStream.flush();

            TamTamUploadFileQuery uploadFileQuery = uploadAPI.uploadFile(url, file);
            UploadedFileInfo uploadedFileInfo = uploadFileQuery.execute();
            assertThat(uploadedFileInfo.getFileId(), is(notNullValue()));
            assertThat(uploadedFileInfo.getFileId(), is(not(0)));
        }
    }

    @Test
    public void should_Upload_Video() {

    }
}