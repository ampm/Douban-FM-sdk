package com.zzxhdzj.http;

import com.zzxhdzj.http.mock.TestResponses;
import org.json.JSONObject;
import org.junit.Test;

import static com.zzxhdzj.http.util.Strings.asStream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonApiResponseTest {
   @Test
    public void consumeResponse_shouldGetAnJsonObjectFromTheResponseBody() throws Exception{
       JsonApiResponse apiResponse = new JsonApiResponse(200);
       apiResponse.consumeResponse(asStream(TestResponses.AUTH_SUCCESS));
       JSONObject jsonObject = apiResponse.getRespJsonObject();
       assertThat(jsonObject.getInt("code"),equalTo(0));
   }
}
