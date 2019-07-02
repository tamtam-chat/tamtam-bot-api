package chat.tamtam.botapi;

import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

import javax.servlet.MultipartConfigElement;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.tamtam.botapi.client.TamTamClient;
import chat.tamtam.botapi.exceptions.APIException;
import chat.tamtam.botapi.exceptions.ClientException;
import chat.tamtam.botapi.model.PhotoToken;
import chat.tamtam.botapi.model.PhotoTokens;
import chat.tamtam.botapi.model.UploadedInfo;
import chat.tamtam.botapi.queries.QueryTest;
import chat.tamtam.botapi.queries.upload.TamTamUploadAVQuery;
import chat.tamtam.botapi.queries.upload.TamTamUploadQuery;
import chat.tamtam.botapi.server.TamTamService;
import spark.Request;
import spark.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static spark.Spark.halt;
import static spark.Spark.post;

/**
 * @author alexandrchuprin
 */
public class TamTamUploadAPITest extends QueryTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static final String FILE_UPLOAD_URL = "http://localhost:4567/fileupload";
    protected static final String IMAGE_UPLOAD_URL = "http://localhost:4567/imageupload";
    protected static final String AV_UPLOAD_URL = "http://localhost:4567/avupload";
    private final TamTamClient client = TamTamClient.create(TamTamService.ACCESS_TOKEN);
    private final TamTamUploadAPI uploadAPI = new TamTamUploadAPI(client);

    @BeforeClass
    public static void before() {
        ObjectMapper mapper = new ObjectMapper();
        post("/fileupload", TamTamUploadAPITest::serverUploadFile, mapper::writeValueAsString);
        post("/imageupload", TamTamUploadAPITest::serverUploadImage, mapper::writeValueAsString);
        post("/avupload", TamTamUploadAPITest::serverUploadAV, mapper::writeValueAsString);
    }

    private static Object serverUploadFile(Request request, Response response) throws Exception {
        Path tempFile = Files.createTempFile("", "");
        tempFile.toFile().deleteOnExit();
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = request.raw().getPart("v1").getInputStream()) {
            long bytes = Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            if (bytes <= 0) {
                halt(400);
            }

            LOG.info("Uploaded {} bytes", bytes);
        }

        return new UploadedInfo("token");
    }

    private static Object serverUploadImage(Request request, Response response) throws Exception {
        Path tempFile = Files.createTempFile("", "");
        tempFile.toFile().deleteOnExit();
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = request.raw().getPart("v1").getInputStream()) {
            long bytes = Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            if (bytes <= 0) {
                halt(400);
            }

            LOG.info("Uploaded {} bytes", bytes);
        }

        return new PhotoTokens(Collections.singletonMap("photokey", new PhotoToken("token")));
    }

    private static Object serverUploadAV(Request request, Response response) throws Exception {
        Path tempFile = Files.createTempFile("", "");
        tempFile.toFile().deleteOnExit();
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = request.raw().getPart("v1").getInputStream()) {
            long bytes = Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            if (bytes <= 0) {
                halt(400);
            }

            LOG.info("Uploaded {} bytes", bytes);
        }

        return new UploadedInfo("token");
    }

    @Test
    public void uploadFile() throws APIException, ClientException {
        String fileName = "test.txt";
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        UploadedInfo uploadedFileInfo = uploadAPI.uploadFile(FILE_UPLOAD_URL, fileName,
                is).execute();
        assertThat(uploadedFileInfo.getToken(), is(notNullValue()));
    }

    @Test
    public void uploadFile1() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("test.txt").toURI());
        UploadedInfo uploadedFileInfo = uploadAPI.uploadFile(FILE_UPLOAD_URL, file).execute();
        assertThat(uploadedFileInfo.getToken(), is(notNullValue()));
    }

    @Test
    public void uploadImage() throws Exception {
        String fileName = "test.png";
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        PhotoTokens uploadedFileInfo = uploadAPI.uploadImage(IMAGE_UPLOAD_URL, fileName, is).execute();
        assertThat(uploadedFileInfo.getPhotos(), is(notNullValue()));
    }

    @Test
    public void uploadImage1() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        PhotoTokens uploadedFileInfo = uploadAPI.uploadImage(IMAGE_UPLOAD_URL, file).execute();
        assertThat(uploadedFileInfo.getPhotos(), is(notNullValue()));
    }

    @Test
    public void uploadAV() throws Exception {
        String fileName = "test.mp4";
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        UploadedInfo uploadedFileInfo = uploadAPI.uploadAV(AV_UPLOAD_URL, fileName, is).execute();
        assertThat(uploadedFileInfo.getToken(), is(notNullValue()));
    }

    @Test
    public void uploadAV1() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("test.mp4").toURI());
        UploadedInfo uploadedFileInfo = uploadAPI.uploadAV(AV_UPLOAD_URL, file).execute();
        assertThat(uploadedFileInfo.getToken(), is(notNullValue()));
    }

    @Test(expected = ClientException.class)
    public void shoudlFail() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("test.png").toURI());
        TamTamUploadQuery<UploadedInfo> query = new TamTamUploadAVQuery(invalidClient, "https://url", file);
        query.execute();
    }
}