package com.econage.core.web.extension.restproxy.spring.boot.autoconfigure;


import com.econage.core.web.extension.restproxy.httppool.HttpClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "econage.rest-proxy")
public class RestProxyProperties {
    private boolean enabled;
    private HttpClientProperties httpClient = new HttpClientProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public HttpClientProperties getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClientProperties httpClient) {
        this.httpClient = httpClient;
    }
}
