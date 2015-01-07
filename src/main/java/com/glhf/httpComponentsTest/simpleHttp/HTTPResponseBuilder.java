package com.glhf.httpComponentsTest.simpleHttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrap-class for HTTP protocol requests.
 * Created request instance from standard <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6">Response</a>
 *
 * @author goodvin Mykola Polonskyi
 *         on 22.12.14
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class HTTPResponseBuilder {
    private static final String NEWLINE="\r\n";

    private final Map<String,String> headers = new HashMap<String, String>();;
    private String ver = null;
    private String responseCode = null;
    private String responseStatus = null;
    private String message = null;

    /**
     * Method for use if you have response in String
     * @param response
     */
    public HTTPResponseBuilder(String response) {
        parse(response);
    }

    /**
     * Method for use if you have response in InputStream
     * socket.getInputStream for example
     * @param response
     * @throws IOException
     */
    public HTTPResponseBuilder(InputStream response) throws IOException{
        parse(response);
    }

    /**
     * Parsing from response-string
     * @param response
     */
    private void parse(String response) {
        Map<String,String> result = new HashMap<>();
        String[] lines=response.split("\n");
        ver=lines[0].substring(0,8);
        responseCode=lines[0].substring(9,12);
        responseStatus=lines[0].substring(13,lines[0].length());

        int i=1;
        while (!lines[i].equals("")){
            String tmp[] = lines[i].split(":");

            String key = tmp[0];
            String value = tmp[1].trim();
            headers.put(key,value);
            i++;
        }
        i++;
        StringBuffer temp = new StringBuffer();
        while (i<lines.length-1){
            temp.append(lines[i]).append("\n");
            i++;
        }
        this.message = temp.toString();
    }

    /**
     * prsing from inputstream with response
     * @param response
     * @throws IOException
     */
    private void parse(InputStream response) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(response));
        String[] str = in.readLine().split(" ");
        ver = str[0];
        responseCode = str[1];
        responseStatus = str[2];
        String tmp=null;
        while ((tmp=in.readLine()).equals("")){
            String[] strs = tmp.split(":");
            String key = strs[0];
            String value = strs[1].trim();
            headers.put(key,value);
        }
        StringBuffer temp = new StringBuffer();
        while ((tmp=in.readLine())!=null){
            temp.append(tmp).append("\n");
        }
        message=temp.toString();
        in.close();
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public String getVer() {
        return ver;
    }

    public String getMessage() {
        return message;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }
}
