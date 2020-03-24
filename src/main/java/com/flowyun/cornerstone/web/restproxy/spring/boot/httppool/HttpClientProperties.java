package com.flowyun.cornerstone.web.restproxy.spring.boot.httppool;

public class HttpClientProperties {
    private int timeToLiveInMinus = 10;
    private int connectionMaxTotal = 1000;
    private int connectionMaxPerRoute = 100;
    private int socketTimeout = 5*60*1000;
    private int connectTimeout = 60 * 1000;
    private int connectionRequestTimeout = 60*1000;
    private int retryCount = 2;
    private boolean requestSentRetryEnabled = true;

    public int getTimeToLiveInMinus() {
        return timeToLiveInMinus;
    }

    public void setTimeToLiveInMinus(int timeToLiveInMinus) {
        this.timeToLiveInMinus = timeToLiveInMinus;
    }

    public int getConnectionMaxTotal() {
        return connectionMaxTotal;
    }

    public void setConnectionMaxTotal(int connectionMaxTotal) {
        this.connectionMaxTotal = connectionMaxTotal;
    }

    public int getConnectionMaxPerRoute() {
        return connectionMaxPerRoute;
    }

    public void setConnectionMaxPerRoute(int connectionMaxPerRoute) {
        this.connectionMaxPerRoute = connectionMaxPerRoute;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isRequestSentRetryEnabled() {
        return requestSentRetryEnabled;
    }

    public void setRequestSentRetryEnabled(boolean requestSentRetryEnabled) {
        this.requestSentRetryEnabled = requestSentRetryEnabled;
    }
}
