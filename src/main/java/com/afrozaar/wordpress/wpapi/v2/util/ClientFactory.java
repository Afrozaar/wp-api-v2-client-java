package com.afrozaar.wordpress.wpapi.v2.util;

import com.afrozaar.wordpress.wpapi.v2.Client;

import java.util.Properties;

public class ClientFactory {

    public static Client fromProperties(Properties properties) {
        return new Client(properties.getProperty("baseUrl"), properties.getProperty("username"), properties.getProperty("password"), Boolean.getBoolean("debug"));
    }

    public static Client fromConfig(ClientConfig config) {
        final ClientConfig.Wordpress wordpress = config.getWordpress();
        return new Client(wordpress.baseUrl, wordpress.username, wordpress.password, config.debug);
    }
}
