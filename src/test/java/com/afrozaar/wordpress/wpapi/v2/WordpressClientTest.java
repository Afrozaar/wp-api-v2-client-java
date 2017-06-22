package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;

import org.junit.Test;

/**
 * @author johan
 */
public class WordpressClientTest {

    @Test
    public void TestCreateClient() {
        Wordpress wordpress = ClientFactory.fromConfig(ClientConfig.of("http://localhost", "username", "password", false, true));

        System.out.println("media = " + wordpress.getMedia(10L));
    }

}