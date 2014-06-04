package com.zzxhdzj.http;

import android.util.Log;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/24/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextApiResponse extends ApiResponse {
    private String resp;

    public TextApiResponse(int httpCode, Map<String, List<String>> headers) {
        super(httpCode,headers);
    }

    @Override
    public void consumeResponse(InputStream responseBody) throws IOException, SAXException, ParserConfigurationException {
        if (isSuccess()) {
            resp = getRespJsonString(responseBody);
            Log.d("TextApiResponse","[response body " + resp + "]\n");
        }
    }
    private String getRespJsonString(InputStream responseBody) throws IOException {
        boolean test=true;
//        if(test){
//               return readDataForZgip(responseBody,"UTF-8");
//        }else {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(responseBody, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            return responseStrBuilder.toString();
//        }

    }

    public String getResp() {
        return resp;
    }

    public static String readDataForZgip(InputStream inStream, String charsetName) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(inStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len=0;
        while((len=bis.read(buf))!=-1){
            baos.write(buf,0,len);
        }

        byte[] data = baos.toByteArray();
        baos.close();
        inStream.close();
        inStream.close();
        return new String(data, charsetName);
    }
}