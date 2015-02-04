package com.zzxhdzj.http;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 5/24/14
 * To change this template use File | Settings | File Templates.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PrefsCookieStore implements CookieStore {

    public static final String TAG = "PrefsCookieStore";
    public static final String COOKIE_PREFS_NAME = "CookiePrefsFile";
    public static final String COLON = ":";
    private static final String DOUBLE_SLASH = "//";
    private final SharedPreferences spePreferences;
    private final ConcurrentHashMap<URI, List<HttpCookie>> cookies;


    public PrefsCookieStore(Context ctxContext) {
        spePreferences = ctxContext.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE);
        cookies = new ConcurrentHashMap<URI, List<HttpCookie>>();
        Map<String, ?> prefsMap = spePreferences.getAll();
        //URI1->a=a,b=b,c=c
        //URI2->a=a,b=b,c=c
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            //loop URI1->a=a,b=b,c=c
            HashSet<String> values = (HashSet<String>) entry.getValue();//a=a,b=b,c=c
            for (String strCookie : values) {//a=a
                //strCookies format:bid="QbJD5At5DXI"; path=/;
                try {
                    URI uri = getUriWithoutPath(new URI(entry.getKey()));
                    if (!cookies.containsKey(uri)) {//
                        List<HttpCookie> lstCookies = Collections.synchronizedList(new ArrayList<HttpCookie>());
                        lstCookies.addAll(HttpCookie.parse(strCookie));
                        cookies.putIfAbsent(uri, lstCookies);
                    } else {
                        List<HttpCookie> lstCookies = cookies.get(uri);
                        lstCookies.addAll(HttpCookie.parse(strCookie));
                        cookies.putIfAbsent(uri, lstCookies);
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, entry.getKey() + ": " + strCookie);
            }
        }
    }

    @Override
    public void add(URI uri, HttpCookie candidatesAddCookie) {
        Log.d(TAG, "add#" + candidatesAddCookie.toString());

        URI uriWithoutPath = getUriWithoutPath(uri);
        if (candidatesAddCookie.hasExpired()) {
            cookies.get(uriWithoutPath).remove(candidatesAddCookie);
        } else {
            cookies.get(uriWithoutPath).add(candidatesAddCookie);
        }
        persistenceCookie(uriWithoutPath.toString(), candidatesAddCookie);
    }

    private URI getUriWithoutPath(URI uri) {
        try {
            return new URI(urlWithoutPath(uri));
        } catch (URISyntaxException e) {
            return null;
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void persistenceCookie(String urlWithoutPath, HttpCookie cookie) {
        HashSet<String> candidateCookieSet = new HashSet<String>();
        Set<String> existCookies = spePreferences.getStringSet(urlWithoutPath, candidateCookieSet);
        Iterator iterator = existCookies.iterator();
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            if (next.split("=")[0].equals(cookie.getName())) {
                iterator.remove();
            }
        }

        candidateCookieSet.addAll(existCookies);
        candidateCookieSet.add(cookie.toString());
        SharedPreferences.Editor ediWriter = spePreferences.edit();
        ediWriter.clear();
        ediWriter.commit();
        ediWriter.putStringSet(urlWithoutPath, candidateCookieSet);
        ediWriter.commit();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public List<HttpCookie> get(URI uri) {
        URI uriWithoutPath = getUriWithoutPath(uri);
        List<HttpCookie> lstCookies = cookies.get(uriWithoutPath);
        if (lstCookies == null) {
            cookies.putIfAbsent(uriWithoutPath, new ArrayList<HttpCookie>());
        }

        return cookies.get(uriWithoutPath);
    }

    private String urlWithoutPath(URI uri) {
        return new StringBuilder()
                .append(uri.getScheme())
                .append(COLON)
                .append(DOUBLE_SLASH)
                .append(uri.getAuthority()).toString();
    }


    @Override
    public List<HttpCookie> getCookies() {
        Collection<List<HttpCookie>> values = cookies.values();
        List<HttpCookie> result = new ArrayList<HttpCookie>();
        for (List<HttpCookie> value : values) {
            result.addAll(value);
        }
        return result;
    }

    @Override
    public List<URI> getURIs() {
        Set<URI> keys = cookies.keySet();
        ArrayList<URI> uriArrayList = new ArrayList<URI>();
        for (URI key : keys) {
            uriArrayList.add(key);
        }
        return uriArrayList;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        List<HttpCookie> lstCookies = cookies.get(urlWithoutPath(uri));
        if (lstCookies == null)
            return false;
        return lstCookies.remove(cookie);
    }

    @Override
    public boolean removeAll() {
        cookies.clear();
        return true;
    }
}
