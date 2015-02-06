package com.zzxhdzj.http.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/24/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HiUtil {
    public static Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<String, List<String>>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    List<String> values = params.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(key, values);
                    }
                    values.add(value);
                }
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }
    public static List<NameValuePair> convertMapToNameValuePairs(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();

        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = params.get(key);
            NameValuePair pair = new BasicNameValuePair(key, value);
            pairs.add(pair);
        }
        return pairs;
    }
    public static String dump(HttpEntity entity) throws IOException {
        return getStringFromInputStream(entity.getContent(),entity.getContentEncoding().getValue());
    }
    public static List<NameValuePair> covertCookieToNameValuePairs(String cookieString){
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        String[] cookieNameAndValues = cookieString.split(";");
        for (String cookieNameAndValue : cookieNameAndValues) {
            String[] nameAndValues = cookieNameAndValue.split("=");
            if(nameAndValues.length==2){
                NameValuePair pair = new BasicNameValuePair(nameAndValues[0].trim(), nameAndValues[1]);
                pairs.add(pair);
            }
        }
        return pairs;
    }
    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is, String contentEncoding) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is,contentEncoding));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
