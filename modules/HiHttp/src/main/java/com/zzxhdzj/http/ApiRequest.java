package com.zzxhdzj.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ApiRequest<T extends ApiResponse> {
    public String method = HttpGet.METHOD_NAME;
    public boolean allowRedirect = false;
    private String url;
    private List<NameValuePair> urlParameters;

    public String getUrlString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);

        if (urlParameters != null) {
            stringBuilder.append("?").append(URLEncodedUtils.format(this.urlParameters, "UTF-8"));
        }
        return stringBuilder.toString();
    }

    public ApiRequest<T> appendParameter(String key, String value) {
        if (urlParameters == null) urlParameters = new ArrayList<NameValuePair>();
        NameValuePair nameValuePair = new BasicNameValuePair(key, value);
        urlParameters.add(nameValuePair);
        return this;
    }

    public ApiRequest<T> setBaseUrl(String baseUrl) {
        this.url = baseUrl;
        return this;
    }

    public Map<String, String> getHeaders() {
        return new HashMap<String, String>();
    }

    public String getMethod() {
        return method;
    }

    public HttpEntity getPostEntity() throws Exception {
        return null;
    }

    public abstract T createResponse(int statusCode, Map<String, List<String>> headers);
}
