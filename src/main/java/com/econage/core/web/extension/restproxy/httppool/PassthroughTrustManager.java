package com.econage.core.web.extension.restproxy.httppool;


import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PassthroughTrustManager implements X509TrustManager
{
    public void checkClientTrusted(X509Certificate[] chain,
                                   String authType) throws CertificateException
    {
    }

    public void checkServerTrusted(X509Certificate[] chain,
                                   String authType) throws CertificateException
    {
    }

    public X509Certificate[] getAcceptedIssuers()
    {
        return null;
    }
}
