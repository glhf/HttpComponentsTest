package com.glhf.httpComponentsTest.simpleHttp;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 22.12.14
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class HTTPRequestBuilderOld {
    /**
     * Wrap-class for HTTP protocol requests.
     * Created request instance from standard <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec5">Request</a>
     *
     *
     *
     * <h1><b>Warning!!!</b></h1>
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

    private static final String VERSION = "HTTP/1.1".intern();
    private static final String NEWLINE = "\r\n".intern();
    private static final String SEPARATOR = " ".intern();

    private static final String[] HEADERS =  {
            "OPTIONS".intern(),
            "GET".intern(),
            "HEAD".intern(),
            "POST".intern(),
            "PUT".intern(),
            "DELETE".intern(),
            "TRACE".intern(),
            "CONNECT".intern(),

            "Cache-Control".intern(),
            "Connection".intern(),
            "Date".intern(),
            "Pragma".intern(),
            "Trailer".intern(),
            "Transfer-Encoding".intern(),
            "Upgrade".intern(),
            "Via".intern(),
            "Warning".intern(),

            "Accept".intern(),
            "Accept-Charset".intern(),
            "Accept-Encoding".intern(),
            "Accept-Language".intern(),
            "Authorization".intern(),
            "Expect".intern(),
            "From".intern(),
            "Host".intern(),
            "If-Match".intern(),
            "If-Modified-Since".intern(),
            "If-None-Match".intern(),
            "If-Range".intern(),
            "If-Unmodified-Since".intern(),
            "Max-Forwards".intern(),
            "Proxy-Authorization".intern(),
            "Range".intern(),
            "Referer".intern(),
            "TE".intern(),
            "User-Agent".intern(),

            "Allow".intern(),
            "Content-Encoding".intern(),
            "Content-Language".intern(),
            "Content-Length".intern(),
            "Content-Location".intern(),
            "Content-MD5".intern(),
            "Content-Range".intern(),
            "Content-Type".intern(),
            "Expires".intern(),
            "Last-Modified".intern()
    };




    private static final String[] METHODS = {   "OPTIONS".intern(),
            "GET".intern(),
            "HEAD".intern(),
            "POST".intern(),
            "PUT".intern(),
            "DELETE".intern(),
            "TRACE".intern(),
            "CONNECT".intern() };

    private static final String[] GENERALHEADERS = {
            "Cache-Control".intern(),
            "Connection".intern(),
            "Date".intern(),
            "Pragma".intern(),
            "Trailer".intern(),
            "Transfer-Encoding".intern(),
            "Upgrade".intern(),
            "Via".intern(),
            "Warning".intern()
    };

    private static final String[] REQUESTHEADERS = {
            "Accept".intern(),
            "Accept-Charset".intern(),
            "Accept-Encoding".intern(),
            "Accept-Language".intern(),
            "Authorization".intern(),
            "Expect".intern(),
            "From".intern(),
            "Host".intern(),
            "If-Match".intern(),
            "If-Modified-Since".intern(),
            "If-None-Match".intern(),
            "If-Range".intern(),
            "If-Unmodified-Since".intern(),
            "Max-Forwards".intern(),
            "Proxy-Authorization".intern(),
            "Range".intern(),
            "Referer".intern(),
            "TE".intern(),
            "User-Agent".intern()
    };

    private static final String[] ENTITYHEADERS = {
            "Allow".intern(),
            "Content-Encoding".intern(),
            "Content-Language".intern(),
            "Content-Length".intern(),
            "Content-Location".intern(),
            "Content-MD5".intern(),
            "Content-Range".intern(),
            "Content-Type".intern(),
            "Expires".intern(),
            "Last-Modified".intern()
    };

    /**
     *  general-header =    Cache-Control            ; Section 14.9
     *                      Connection               ; Section 14.10
     *                      Date                     ; Section 14.18
     *                      Pragma                   ; Section 14.32
     *                      Trailer                  ; Section 14.40
     *                      Transfer-Encoding        ; Section 14.41
     *                      Upgrade                  ; Section 14.42
     *                      Via                      ; Section 14.45
     *                      Warning                  ; Section 14.46
     */
    private final HashMap<String,String> generalHeader = new HashMap<String, String>();

    /**
     * request-header = Accept                   ; Section 14.1
     *                  Accept-Charset           ; Section 14.2
     *                  Accept-Encoding          ; Section 14.3
     *                  Accept-Language          ; Section 14.4
     *                  Authorization            ; Section 14.8
     *                  Expect                   ; Section 14.20
     *                  From                     ; Section 14.22
     *                  Host                     ; Section 14.23
     *                  If-Match                 ; Section 14.24
     *                  If-Modified-Since        ; Section 14.25
     *                  If-None-Match            ; Section 14.26
     *                  If-Range                 ; Section 14.27
     *                  If-Unmodified-Since      ; Section 14.28
     *                  Max-Forwards             ; Section 14.31
     *                  Proxy-Authorization      ; Section 14.34
     *                  Range                    ; Section 14.35
     *                  Referer                  ; Section 14.36
     *                  TE                       ; Section 14.39
     *                  User-Agent               ; Section 14.43
     */
    private final HashMap<String,String> requestHeader = new HashMap<String, String>();

    /**
     * entity-header  = Allow                    ; Section 14.7
     *                  Content-Encoding         ; Section 14.11
     *                  Content-Language         ; Section 14.12
     *                  Content-Length           ; Section 14.13
     *                  Content-Location         ; Section 14.14
     *                  Content-MD5              ; Section 14.15
     *                  Content-Range            ; Section 14.16
     *                  Content-Type             ; Section 14.17
     *                  Expires                  ; Section 14.21
     *                  Last-Modified            ; Section 14.29
     *                  extension-header
     *
     * extension-header = message-header
     */
    private final HashMap<String,String> entityHeader =  new HashMap<String, String>();

    private final HashMap<String,String> headers = new HashMap<String, String>();

    private StringBuffer request;
    private String messageBody = "".intern();

    public HTTPRequestBuilderOld(String method, String uri) throws IllegalArgumentException{
        if (method==null || uri == null)              throw new IllegalArgumentException();
        if (!isLegalHeader(METHODS, method.intern())) throw new IllegalArgumentException("Uknown request method: "+method);
        this.request = new StringBuffer(method).append(SEPARATOR).append(uri).append(SEPARATOR).append(VERSION).append(NEWLINE);
    }

    public void addHeader(String key, String value){
        if (isLegalHeader(HEADERS,key)) this.generalHeader.put(key,value);
    }

    public void addGeneralHeader(String key, String value){
        if (isLegalHeader(GENERALHEADERS,key)) this.generalHeader.put(key,value);
    }

    public void addRequestHeader(String key, String value){
        if (isLegalHeader(REQUESTHEADERS,key)) this.requestHeader.put(key, value);
    }

    public void addEntityHeader(String key, String value){
        if (isLegalHeader(ENTITYHEADERS,key)) this.entityHeader.put(key, value);
    }

    /**
     * Method set body of message - entity data if it is!
     * @param messageBody
     */
    public void addEntity(String messageBody){
        this.messageBody = messageBody;
    }

    public String getRequest(){
        if (!generalHeader.isEmpty()){
            generalHeader.forEach( (key,value) -> {
                request.append(key).append(": ").append(value).append(NEWLINE);
            });
        }
        if (!requestHeader.isEmpty()){
            requestHeader.forEach( (key,value) -> {
                request.append(key).append(": ").append(value).append(NEWLINE);
            });
        }
        if (!entityHeader.isEmpty()){
            entityHeader.forEach( (key,value) -> {
                request.append(key).append(": ").append(value).append(NEWLINE);
            });
        }
        request.append(NEWLINE);
        if (this.messageBody!="".intern()) {
            request.append(this.messageBody);
        }
        return request.toString();
    }

    /**
     * Method check specified <p>header</p> on the available <p>headers</p> array
     * @param headers
     * @param header
     * @return
     */
    private boolean isLegalHeader(String[] headers, String header){
        return Arrays.asList(headers).contains(header);
    }
}

