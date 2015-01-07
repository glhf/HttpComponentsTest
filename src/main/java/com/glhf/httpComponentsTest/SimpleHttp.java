package com.glhf.httpComponentsTest;

import com.glhf.httpComponentsTest.simpleHttp.HTTPRequestBuilder;
import com.glhf.httpComponentsTest.simpleHttp.HTTPResponseBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 19.12.14
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class SimpleHttp {
    private static final Logger LOG = LogManager.getLogger(SimpleHttp.class);

    public String get(){
        //http://www.ex.ua/ru/video/foreign?r=23775
        try {
            int i=0;
            Socket soc = new Socket("www.ex.ua", 80);
            LOG.info("Socet open!");

            /*
            <!--
                                f***** http
            in this kind of URL ALWAYS WRITE protocol modifier!!!
                       ALWAYS WRITE PROTOCOL MODIFIER!!!
                             Like   " http:// "
            -->
            */

            HTTPRequestBuilder rb = new HTTPRequestBuilder("GET", "http://www.ex.ua/ru/video/foreign?r=23775");
            rb.addHeader("Connection", "close");
            rb.addHeader("Host", "www.ex.ua");
            LOG.info(rb.getRequest());

            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);

            out.write(rb.getRequest());
            out.flush();


            //BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            /*StringBuffer answer = new StringBuffer();
            String tmp ="";
            while ((tmp=in.readLine())!=null){
                answer.append(tmp+"\n");
            }*/

            HTTPResponseBuilder resp = new HTTPResponseBuilder(soc.getInputStream());
            LOG.info(resp.getVer());
            LOG.info(resp.getResponseCode());
            LOG.info(resp.getResponseStatus());
            resp.getHeaders().forEach((key, value) -> {
                        LOG.info(key + "=" + value);
                    }
            );
            LOG.info(resp.getMessage());

            //in.close();
            out.close();
            soc.close();
        } catch (IOException e) {
            LOG.error("IO ERROR! ", e);
        } catch (IllegalArgumentException e){
            LOG.error("Arg error", e);
        }
        return null;
    }
}
