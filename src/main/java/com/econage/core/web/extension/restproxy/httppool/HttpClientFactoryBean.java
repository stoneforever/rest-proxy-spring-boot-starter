package com.econage.core.web.extension.restproxy.httppool;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/*
* http连接池创建组件
* */
public class HttpClientFactoryBean implements InitializingBean, DisposableBean, FactoryBean<CloseableHttpClient> {
    /*
     * 连接相关参数
     * */
    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager connectionManager;
    private HttpClientProperties properties;

    public HttpClientFactoryBean(HttpClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws NoSuchAlgorithmException, KeyManagementException {
        //忽略ssl证书校验，考虑改造ssl证书管理
        SSLContext theContext = SSLContext.getInstance("SSL");
        theContext.init(null, new TrustManager[]{new PassthroughTrustManager()}, new SecureRandom());
        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(theContext, new NoopHostnameVerifier()))
                .build();

        /*创建连接客户端*/
        connectionManager = new PoolingHttpClientConnectionManager(
                registry, null, null ,null,
                properties.getTimeToLiveInMinus(), TimeUnit.MINUTES
        );
        connectionManager.setMaxTotal(properties.getConnectionMaxTotal());
        connectionManager.setDefaultMaxPerRoute(properties.getConnectionMaxPerRoute());

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                .setSocketTimeout(properties.getSocketTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)    // 设置连接池管理
                .setDefaultRequestConfig(requestConfig)     // 设置请求配置
                .setRetryHandler(new DefaultHttpRequestRetryHandler(properties.getRetryCount(), properties.isRequestSentRetryEnabled()))   // 设置重试次数
                .build();
    }

    @Override
    public void destroy() throws Exception {
        try{
            httpClient.close();
        }finally{
            connectionManager.close();
        }
    }

    @Override
    public CloseableHttpClient getObject() throws Exception {
        return httpClient;
    }

    @Override
    public Class<?> getObjectType() {
        return CloseableHttpClient.class;
    }
}
