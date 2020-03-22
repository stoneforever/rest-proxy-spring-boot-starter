package com.econage.core.web.extension.restproxy.autoconfiguration;

import com.econage.core.web.extension.restproxy.httppool.HttpClientFactoryBean;
import com.econage.core.web.extension.restproxy.scanner.RestProxyScannerConfigurer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestProxyProperties.class)
public class RestProxyConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RestProxyConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public HttpClientFactoryBean HttpClientConnectPool(RestProxyProperties restProxyProperties){
        return new HttpClientFactoryBean(restProxyProperties.getHttpClient());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder builder, CloseableHttpClient httpClient) {
        return builder
                .requestFactory( ()->new HttpComponentsClientHttpRequestFactory(httpClient) )
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestProxyScannerConfigurer restProxyScanner(RestProxyProperties restProxyProperties){

    }


}
