package com.econage.core.web.extension.restproxy.spring.boot.autoconfigure;

import com.econage.core.web.extension.restproxy.httppool.HttpClientFactoryBean;
import com.econage.core.web.extension.restproxy.scanner.RestProxyScannerConfigurer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Configuration
@EnableConfigurationProperties(RestProxyProperties.class)
@ConditionalOnProperty(value = "econage.rest-proxy.enabled",havingValue = "true")
@AutoConfigureAfter(RestTemplateAutoConfiguration.class)
public class RestProxyConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RestProxyConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public HttpClientFactoryBean HttpClientConnectPool(RestProxyProperties restProxyProperties){
        return new HttpClientFactoryBean(restProxyProperties.getHttpClient());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(
            RestProxyProperties restProxyProperties,
            RestTemplateBuilder builder,
            CloseableHttpClient httpClient
    ) {
        DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory();
        uriFactory.setEncodingMode(restProxyProperties.getEncodingMode());

        return builder
                .uriTemplateHandler(uriFactory)
                .requestFactory( ()->new HttpComponentsClientHttpRequestFactory(httpClient) )
                .build();
    }


    /**
     * 仅扫描Spring boot相同的位置。如果想要更多设置，可以使用
     * {@link com.econage.core.web.extension.restproxy.annotations.RestProxyScan}
     */
    public static class AutoConfiguredRestProxyScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

        private BeanFactory beanFactory;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

            if (!AutoConfigurationPackages.has(this.beanFactory)) {
                logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.");
                return;
            }

            logger.debug("Searching for mappers annotated with @RestProxyScan");

            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
            if (logger.isDebugEnabled()) {
                packages.forEach(pkg -> logger.debug("Using auto-configuration base package '{}'", pkg));
            }

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RestProxyScannerConfigurer.class);

            builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));

            registry.registerBeanDefinition(RestProxyScannerConfigurer.class.getName(), builder.getBeanDefinition());
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

    }

    @Configuration
    @Import(AutoConfiguredRestProxyScannerRegistrar.class)
    @ConditionalOnMissingBean({ RestProxyScannerConfigurer.class })
    public static class RestProxyScannerRegistrarNotFoundConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            logger.debug(
                    "Not found configuration for registering mapper bean using @RestProxyScan and RestProxyScannerConfigurer.");
        }

    }


}
