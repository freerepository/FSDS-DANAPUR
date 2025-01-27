package com.sedulous.fsdsdanapur.Utils;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HttpsURLConnection;

public class HttpsTrustManager implements X509TrustManager {
    public static void allowAllSSL() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new HttpsTrustManager()
        };
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception ignored) {
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}