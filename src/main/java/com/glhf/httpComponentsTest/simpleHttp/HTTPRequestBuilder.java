package com.glhf.httpComponentsTest.simpleHttp;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Wrap-class for HTTP protocol requests.
 * Created request instance from standard <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec5">Request</a>
 *
 *
 *
 * <h3><b>Warning!!!</b></h3>
 * Without implements for extensions elements! (headers, methods etc.)
 *
 *  Request       = Request-Line              ; Section 5.1
 *                  (( general-header         ; Section 4.5
 *                  | request-header          ; Section 5.3
 *                  | entity-header ) CRLF)   ; Section 7.1
 *                  CRLF
 *                  [ message-body ]          ; Section 4.3
 *
 *
 * @author goodvin Mykola Polonskyi
 *         on 22.12.14
 *         github.com/glhf
 *         goodvin4@gmail.com
 */


public class HTTPRequestBuilder {
    private static final String VERSION = "HTTP/1.1";
    private static final String NEWLINE = "\r\n";
    private static final String SEPARATOR = " ";

    private static final String[] HEADERS =  {
            "OPTIONS",
            "GET",
            "HEAD",
            "POST",
            "PUT",
            "DELETE",
            "TRACE",
            "CONNECT",

            "Cache-Control",
            "Connection",
            "Date",
            "Pragma",
            "Trailer",
            "Transfer-Encoding",
            "Upgrade",
            "Via",
            "Warning",

            "Accept",
            "Accept-Charset",
            "Accept-Encoding",
            "Accept-Language",
            "Authorization",
            "Expect",
            "From",
            "Host",
            "If-Match",
            "If-Modified-Since",
            "If-None-Match",
            "If-Range",
            "If-Unmodified-Since",
            "Max-Forwards",
            "Proxy-Authorization",
            "Range",
            "Referer",
            "TE",
            "User-Agent",

            "Allow",
            "Content-Encoding",
            "Content-Language",
            "Content-Length",
            "Content-Location",
            "Content-MD5",
            "Content-Range",
            "Content-Type",
            "Expires",
            "Last-Modified"
    };

    private String method = "".intern();
    private String uri = "".intern();
    private final HashMap<String,String> headers = new HashMap<String, String>();
    private String messageBody = "".intern();

    public HTTPRequestBuilder(String method, String uri) {
        this.method=method;
        this.uri=uri;
    }

    public void addHeader(String key, String value){
        if (isLegalHeader(key)) this.headers.put(key,value);
    }

    /**
     * Method set body of message - entity data if it is!
     * @param messageBody
     */
    public void addEntity(String messageBody){
        this.messageBody = messageBody;
    }

    public String getRequest() throws IllegalArgumentException{
        if (method==null || uri == null)              throw new IllegalArgumentException();
        if (!isLegalHeader(method.intern())) throw new IllegalArgumentException("Uknown request method: "+method);
        String requestLine = new StringBuffer(method).append(SEPARATOR).append(uri).append(SEPARATOR).append(VERSION).append(NEWLINE).toString();
        return new StringBuffer().append(requestLine).append(this.headersToString()).append(messageBody).toString();
    }

    private String headersToString(){
        StringBuffer sb = new StringBuffer("");
        if (!headers.isEmpty()){
            headers.forEach( (key,value) -> {
                sb.append(key).append(": ").append(value).append(NEWLINE);
            });
        }
        sb.append(NEWLINE);
        return sb.toString();
    }

    /**
     * Method check specified <p>header</p> on the available <p>headers</p> array
     * @param header
     * @return
     */
    private boolean isLegalHeader(String header){
        return Arrays.asList(HEADERS).contains(header);
    }
}
