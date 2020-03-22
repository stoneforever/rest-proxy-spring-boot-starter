package com.econage.core.web.extension.restproxy.autoconfiguration;


import com.econage.core.web.extension.restproxy.httppool.HttpClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("econage.rest-proxy")
public class RestProxyProperties {


    private HttpClientProperties httpClient;

    public HttpClientProperties getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClientProperties httpClient) {
        this.httpClient = httpClient;
    }
}
