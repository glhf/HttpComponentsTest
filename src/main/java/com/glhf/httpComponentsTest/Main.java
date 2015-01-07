package com.glhf.httpComponentsTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 18.12.14
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args){
        String link = "http://www.apache.org/";
        String page = new HttpExample().load(link);
        LOG.info(page);
    }
}
