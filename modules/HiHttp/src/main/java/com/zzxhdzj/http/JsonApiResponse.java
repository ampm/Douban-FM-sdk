package com.zzxhdzj.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonApiResponse extends ApiResponse {
    private JSONObject respJsonObject;
    private JSONArray responseJsonArray;


    public JsonApiResponse(int httpCode) {
        super(httpCode);
    }

    public JsonApiResponse(int httpCode, Map<String, List<String>> headers) {
        super(httpCode, headers);
    }

    @Override
    public void consumeResponse(InputStream responseBody) throws Exception {
        if (isSuccess()) {
            String data = getRespJsonString(responseBody);
            Object json = new JSONTokener(data).nextValue();
            if (json instanceof JSONObject) {
                respJsonObject = new JSONObject(data);
            } else if (json instanceof JSONArray) {
                responseJsonArray = new JSONArray(data);
            } else throw new JSONException("Invalid Json");
        }
    }

    private String getRespJsonString(InputStream responseBody) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(responseBody, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        return responseStrBuilder.toString();
    }

    public JSONObject getRespJsonObject() {
        return respJsonObject;
    }

    public JSONArray getResponseJsonArray() {
        return responseJsonArray;
    }
}
