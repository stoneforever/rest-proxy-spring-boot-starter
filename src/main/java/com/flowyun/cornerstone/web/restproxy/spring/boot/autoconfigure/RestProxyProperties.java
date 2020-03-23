package com.flowyun.cornerstone.web.restproxy.spring.boot.autoconfigure;


import com.flowyun.cornerstone.web.restproxy.spring.boot.httppool.HttpClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.util.DefaultUriBuilderFactory;

@ConfigurationProperties(prefix = "econage.rest-proxy")
public class RestProxyProperties {
    private boolean enabled;
    private DefaultUriBuilderFactory.EncodingMode encodingMode = DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES;
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

    public DefaultUriBuilderFactory.EncodingMode getEncodingMode() {
        return encodingMode;
    }

    public void setEncodingMode(DefaultUriBuilderFactory.EncodingMode encodingMode) {
        this.encodingMode = encodingMode;
    }
}
