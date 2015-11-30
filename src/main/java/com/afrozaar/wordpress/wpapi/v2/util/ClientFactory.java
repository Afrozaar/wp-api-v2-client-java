package com.afrozaar.wordpress.wpapi.v2.util;

import com.afrozaar.wordpress.wpapi.v2.Client;

import java.util.Properties;

public class ClientFactory {

    // TODO: this needs some TLC

    Properties properties = new Properties();

    public static Client fromProperties(Properties properties) {
        return new Client(properties.getProperty("baseUrl"), properties.getProperty("username"), properties.getProperty("password"));
    }
}
