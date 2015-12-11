package com.afrozaar.wordpress.wpapi.v2.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ClientConfig {

    Wordpress wordpress;
    boolean debug;

    public ClientConfig() {
    }

    private ClientConfig(boolean debug, Wordpress wordpress) {
        this.debug = debug;
        this.wordpress = wordpress;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Wordpress getWordpress() {
        return wordpress;
    }

    public void setWordpress(Wordpress wordpress) {
        this.wordpress = wordpress;
    }

    public static ClientConfig load(InputStream inputStream) {
        return new Yaml().loadAs(inputStream, ClientConfig.class);
    }

    public static ClientConfig of(String baseUrl, String username, String password, boolean debug) {
        return new ClientConfig(debug, new Wordpress(baseUrl, username, password));
    }

    public static class Wordpress {
        String username;
        String password;
        String baseUrl;

        Wordpress() {
        }

        private Wordpress(String baseUrl, String username, String password) {
            this.baseUrl = baseUrl;
            this.password = password;
            this.username = username;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
