package de.schulzgette.thes_o_naise.utils;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import de.schulzgette.thes_o_naise.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



/**
 * Statischer HTTP Client
 */
public class HttpClient extends Application {
    //private static final String BASE_URL ="https://192.168.188.35:3001/";

    //SERVER URL für GENYMOTION
    private static final String BASE_URL ="https://10.0.3.2:3001/";

    //SERVER URL für Android Studio VD
    //private static final String BASE_URL ="https://10.0.2.2:3001/";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");




    public HttpClient() {
    }

    public static OkHttpClient verifyCert(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // loading CAs from an InputStream
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream cert = context.getResources().openRawResource(R.raw.server);
        Certificate ca;
        try {
            ca = cf.generateCertificate(cert);
        } finally { cert.close(); }

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if ("10.0.3.2".equalsIgnoreCase(hostname) || "10.0.2.2".equalsIgnoreCase(hostname) ) {
                    return true;
                }
                return false;
            }
        });
        OkHttpClient client = builder.build();
        return client;
    }

    public static Call POST(String path, String json, Context context, Callback callback) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        String url = BASE_URL + path;
        RequestBody body = RequestBody.create(JSON, json);

        OkHttpClient client = verifyCert(context);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Call GET(String path, Context context, Callback callback) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url = BASE_URL + path;
        OkHttpClient client = verifyCert(context);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static Call PUT(String path, String json, Context context, Callback callback) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException{
        OkHttpClient client = verifyCert(context);
        String url = BASE_URL + path;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static Call DELETE (String path, String json, Context context, Callback callback) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException{
        OkHttpClient client = verifyCert(context);
        String url = BASE_URL + path;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(BASE_URL+url)
                .delete(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }
}


