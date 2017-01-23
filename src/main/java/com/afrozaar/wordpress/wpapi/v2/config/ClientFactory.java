package com.afrozaar.wordpress.wpapi.v2.config;

import com.afrozaar.wordpress.wpapi.v2.Client;
import com.afrozaar.wordpress.wpapi.v2.Wordpress;

import org.springframework.http.client.ClientHttpRequestFactory;

public class ClientFactory {

    public static Wordpress fromConfig(ClientConfig config) {
        final ClientConfig.Wordpress wordpress = config.getWordpress();
        return new Client(wordpress.baseUrl, wordpress.username, wordpress.password, config.debug);
    }

    public static Builder builder(ClientConfig config) {
        Builder builder = new Builder();
        builder.config = config;
        return builder;
    }

    public static class Builder {
        private ClientHttpRequestFactory requestFactory;
        private ClientConfig config;

        private Builder() {

        }

        public Builder withRequestFactory(ClientHttpRequestFactory requestFactory) {
            this.requestFactory = requestFactory;
            return this;
        }

        public Wordpress build() {
            final ClientConfig.Wordpress wordpress = config.getWordpress();
            return new Client(wordpress.baseUrl, wordpress.username, wordpress.password, config.debug, requestFactory);
        }
    }
}
