package com.zzxhdzj.http;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static com.zzxhdzj.http.util.Strings.fromStream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class HttpTest {
    private Http http;
    private MockWebServer server;

    @Before
    public void setUp() throws IOException {
        http = new Http();
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        server = new MockWebServer();

    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void shouldReturnCorrectResponse() throws Exception {
        server.enqueue(new MockResponse().setBody("it's all cool"));
        server.play();

        Http.Response response = http.get(server.getUrl("/").toString(), new HashMap<String, String>(), false);
        assertThat(fromStream(response.getResponseBody()), equalTo("it's all cool"));
    }
    @Test
    public void testHttpClientCanReadHttpErrorResp() throws Exception {
        server.enqueue(new MockResponse().setBody("Forbidden")
                .setResponseCode(403));
        server.play();
        Http.Response response = http.get(server.getUrl("/").toString(), new HashMap<String, String>(), false);
        assertThat(fromStream(response.getResponseBody()), equalTo("Forbidden"));
    }

    @Test
    public void testPost_shouldUseCorrectMethod() throws Exception {
        server.enqueue(new MockResponse().setBody("it's all cool"));
        server.play();

        StringEntity stringEntity = new StringEntity("a post body");
        http.post(server.getUrl("/").toString(), new HashMap<String, String>(), stringEntity, false);
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getMethod(), equalTo(HttpPost.METHOD_NAME));
        assertThat(recordedRequest.getUtf8Body(), equalTo("a post body"));
    }

    @Test
    public void tesPost_shouldIncludePostBody() throws Exception {
        server.enqueue(new MockResponse().setBody("it's all cool"));
        server.play();

        StringEntity stringEntity = new StringEntity("a post body");
        stringEntity.setContentType("text/plain; charset=UTF-8");
        http.post(server.getUrl("/").toString(), new HashMap<String, String>(), stringEntity, false);
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getUtf8Body(), equalTo("a post body"));
        assertThat(recordedRequest.getHeader(HTTP.CONTENT_TYPE), equalTo("text/plain; charset=UTF-8"));
    }

    @Test
    public void testPostFile_shouldWithCorrectContentType() throws Exception {
        server.enqueue(new MockResponse().setBody("it's all cool"));
        server.play();

        File file = new File(ClassLoader.getSystemResource("test.jpg").toURI());
        assertNotNull(file);
//        Didn't figure out why can't get resource from mvn command line.
//        So i manually copy it to test/resources directory.
//        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("META-INF/mimetypes.default");
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream("mimetypes.default");
        assertNotNull(resourceAsStream);
        FileEntity fileEntity = new FileEntity(file, new MimetypesFileTypeMap(resourceAsStream).getContentType(file));
        http.post(server.getUrl("/").toString(), new HashMap<String, String>(), fileEntity, false);
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getHeader(HTTP.CONTENT_TYPE), equalTo("image/jpeg"));
    }

}
