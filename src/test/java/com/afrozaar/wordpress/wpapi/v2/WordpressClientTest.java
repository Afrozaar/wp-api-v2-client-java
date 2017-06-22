package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.model.Media;

import org.junit.Test;

import java.util.List;

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