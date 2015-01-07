package com.glhf.httpComponentsTest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 18.12.14
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class HttpExample {
    private static final Logger LOG = LogManager.getLogger(HttpExample.class);

    public HttpExample(){
        LOG.info("HTTPEXAMPL created!");
    }

    public String load(String link){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpHead get = new HttpHead(link);
        CloseableHttpResponse response = null;
        StringBuilder out = new StringBuilder();

        try {
            response = client.execute(get);
            Header[] ent = response.getAllHeaders();

            LOG.info("length = "+ent.length);



            for (int i=0; i<ent.length; i++){
                out.append(ent[i].getName()+"="+ent[i].getValue()+"\n");
            }
            response.close();
        } catch (IOException e) {
            LOG.error("Execute error!", e);
        }

        LOG.info(out.toString());
        return null;
    }
}
