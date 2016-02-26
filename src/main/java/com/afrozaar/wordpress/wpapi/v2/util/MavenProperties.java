package com.afrozaar.wordpress.wpapi.v2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MavenProperties {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProperties.class);

    public static Properties getProperties() {

        InputStream is = MavenProperties.class.getClassLoader().getResourceAsStream("META-INF/maven/com.afrozaar.wordpress/wp-api-v2-client-java/pom.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            LOG.error("Error loading properties ", e);
        }
        return properties;
    }

    public static void main(String [] args){
        getProperties().list(System.out);
    }
}
