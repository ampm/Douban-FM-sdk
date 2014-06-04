package com.zzxhdzj.http;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

public abstract class ApiResponse {
    protected int httpResponseCode;
    private Map<String, List<String>> headers;

    public ApiResponse(int httpCode) {
        this.httpResponseCode = httpCode;
    }

    public ApiResponse(int httpCode, Map<String, List<String>> headers) {
        this.httpResponseCode = httpCode;
        this.headers = headers;
    }

    public abstract void consumeResponse(InputStream responseBody) throws Exception;

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public boolean isSuccess() {
        return (httpResponseCode /100==2);
    }

    public boolean isUnauthorized() {
        return httpResponseCode == 401;
    }
}
