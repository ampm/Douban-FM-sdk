package com.zzxhdzj.http.util;

import java.io.*;

public class Strings {
    public static boolean isEmptyOrWhitespace(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    public static String fromStream(InputStream inputStream) throws IOException {
        char[] buffer = new char[0x10000];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        int read;
        while ((read = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, read);
        }
        return out.toString();
    }

    public static InputStream asStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }


}
