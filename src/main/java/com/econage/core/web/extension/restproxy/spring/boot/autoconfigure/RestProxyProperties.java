package com.econage.core.web.extension.restproxy.spring.boot.autoconfigure;


import com.econage.core.web.extension.restproxy.httppool.HttpClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "econage.rest-proxy",ignoreUnknownFields = false)
public class RestProxyProperties {
    private boolean enabled;
    private List<String> basePackages;
    private HttpClientProperties httpClient = new HttpClientProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    public HttpClientProperties getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClientProperties httpClient) {
        this.httpClient = httpClient;
    }
}
