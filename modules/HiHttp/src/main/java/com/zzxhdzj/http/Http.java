package com.zzxhdzj.http;

import android.content.Context;
import android.text.TextUtils;
import org.apache.http.HttpEntity;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 * !!WARNING:"请勿添加业务相关代码"!!
 */
public class Http {

    public static void initCookieManager(Context context) {
        CookieManager cmrCookieMan = new CookieManager(new PrefsCookieStore(context), CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cmrCookieMan);
    }

    public static void clearCookie(Context context) {
        context.getSharedPreferences(PrefsCookieStore.COOKIE_PREFS_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }


    public Response get(String url, Map<String, String> headers, boolean allowRedirect)
            throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        InputStream in = null;
        setCommonProperties(headers, urlConnection, allowRedirect);
        try {
            in = readInputStream(urlConnection);
        } catch (Exception e) {
            throw e;
        } finally {
            urlConnection.disconnect();
        }
        return new Response(urlConnection.getResponseCode(), in, urlConnection.getHeaderFields());
    }


    public Response post(String url, Map<String, String> headers, HttpEntity httpEntity, boolean allowRedirect)
            throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        InputStream in = null;
        urlConnection.setInstanceFollowRedirects(allowRedirect);
        urlConnection.setRequestProperty(httpEntity.getContentType().getName(), httpEntity.getContentType().getValue());
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        setCommonProperties(headers, urlConnection, allowRedirect);
        try {
            DataOutputStream wr = new DataOutputStream(
                    urlConnection.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(httpEntity.getContent());
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                wr.write(buffer, 0, len);
            }
            wr.flush();
            wr.close();
            bis.close();
            in = readInputStream(urlConnection);
        } catch (Exception e) {
            throw e;
        } finally {
            urlConnection.disconnect();
        }
        return new Response(urlConnection.getResponseCode(), in, urlConnection.getHeaderFields());
    }

    private void setCommonProperties(Map<String, String> headers, HttpURLConnection urlConnection, boolean allowRedirect) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        urlConnection.setRequestProperty("Accept-Encoding", "gzip");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        urlConnection.setInstanceFollowRedirects(allowRedirect);
        urlConnection.setConnectTimeout(60 * 1000);
        urlConnection.setReadTimeout(60 * 1000);
    }

    public static class Response {
        private Map<String, List<String>> headerFields;
        private int statusCode;
        private InputStream responseBody;


        public Response(int responseCode, InputStream in, Map<String, List<String>> headerFields) {
            this.statusCode = responseCode;
            this.responseBody = in;
            this.headerFields = headerFields;
        }

        public Map<String, List<String>> getHeaderFields() {
            return headerFields;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public InputStream getResponseBody() {
            return responseBody;
        }
    }

    public static byte[] unGZIP(byte[] zipped) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(zipped);
        GZIPInputStream zipIn = null;
        try {
            zipIn = new GZIPInputStream(in);
            byte[] orginal = new byte[1024];
            int len = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = zipIn.read(orginal)) > 0) {
                out.write(orginal, 0, len);
            }
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new Exception("unGZIP failed", e);
        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }

    public static ByteArrayInputStream readDataForZgip(InputStream inStream, String charsetName) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(inStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = bis.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        byte[] data;
        data = unGZIP(baos.toByteArray());
        baos.close();
        inStream.close();
        inStream.close();
        return new ByteArrayInputStream(data);
    }

    public static ByteArrayInputStream read(InputStream inStream, String charsetName) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(inStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = bis.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        byte[] data;
        data = baos.toByteArray();
        baos.close();
        inStream.close();
        inStream.close();
        return new ByteArrayInputStream(data);
    }

    private InputStream readInputStream(HttpURLConnection urlConnection) throws Exception {
        InputStream in = null;
        if (urlConnection.getResponseCode() / 100 != 2) {
            in = urlConnection.getErrorStream();
        } else {
            in = urlConnection.getInputStream();
        }
        if (!TextUtils.isEmpty(urlConnection.getContentEncoding()) && "gzip".equals(urlConnection.getContentEncoding())) {
            in = readDataForZgip(in, "UTF-8");
        } else {
            in = read(in, "UTF-8");
        }
        return in;
    }

}
