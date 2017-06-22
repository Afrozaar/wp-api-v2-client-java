package com.afrozaar.wordpress.wpapi.v2.config;

import org.springframework.core.io.Resource;

import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
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

    public static ClientConfig load(Resource resource) throws IOException {
        LoggerFactory.getLogger(ClientConfig.class).info("Loading ClientConfig using resource: {}", resource);
        return load(resource.getInputStream());
    }

    public static ClientConfig of(String baseUrl, String username, String password, boolean usePermalinkEndpoint, boolean debug) {
        return new ClientConfig(debug, new Wordpress(baseUrl, username, password, usePermalinkEndpoint));
    }

    public static class Wordpress {
        String username;
        String password;
        String baseUrl;
        boolean usePermalinkEndpoint;

        Wordpress() {
        }

        private Wordpress(String baseUrl, String username, String password, boolean usePermalinkEndpoint) {
            this.baseUrl = baseUrl;
            this.password = password;
            this.username = username;
            this.usePermalinkEndpoint = usePermalinkEndpoint;
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

        public boolean isUsePermalinkEndpoint() {
            return usePermalinkEndpoint;
        }

        public void setUsePermalinkEndpoint(boolean usePermalinkEndpoint) {
            this.usePermalinkEndpoint = usePermalinkEndpoint;
        }
    }
}
