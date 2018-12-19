package chat.tamtam.botapi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
        File file = new File(getClass().getClassLoader().getResource("logo.png").toURI());
        GetUploadUrlQuery query = botAPI.getUploadUrl(UploadType.PHOTO);
        UploadEndpoint uploadEndpoint = query.get();
        TamTamUploadImageQuery uploadQuery = uploadAPI.uploadImage(uploadEndpoint.getUrl(), file);
        PhotoTokens photoTokens = uploadQuery.get();
        assertThat(photoTokens.getPhotos().size(), is(1));
        assertThat(photoTokens.getPhotos().values().iterator().next().getToken(), is(notNullValue()));
    }

    @Test
    public void should_Upload_File() throws Exception {
        String url = botAPI.getUploadUrl(UploadType.FILE).get().getUrl();
        File file = Files.createTempFile("tt", ".bin").toFile();
        file.deleteOnExit();
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < 4096; i++) {
                outputStream.write(RANDOM.nextInt(256));
            }
            outputStream.flush();

            TamTamUploadFileQuery uploadFileQuery = uploadAPI.uploadFile(url, file);
            UploadedFileInfo uploadedFileInfo = uploadFileQuery.get();
            assertThat(uploadedFileInfo.getFileId(), is(notNullValue()));
            assertThat(uploadedFileInfo.getFileId(), is(not(0)));
        }
    }

    @Test
    public void should_Upload_Video() {

    }
}