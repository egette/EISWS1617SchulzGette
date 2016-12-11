package de.schulzgette.thes_o_naise.utils;

import android.app.Application;

import java.io.IOException;

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
    //private static final String BASE_URL ="http://192.168.188.27:3000/";
    //SERVER URL für GENYMOTION
    private static final String BASE_URL ="http://10.0.3.2:3000/";
    //SERVER URL für Android Studio VD
    //private static final String BASE_URL ="http://10.0.2.2:3000/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpClient client = new OkHttpClient();


    public HttpClient() {
    }

    public static Call POST(String path, String json, Callback callback) throws IOException {

        String url = BASE_URL + path;
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Call GET(String path, Callback callback) throws IOException {
        String url = BASE_URL + path;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static Call PUT(String path, String json, Callback callback) throws IOException{
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

    public static Call DELETE (String path, String json, Callback callback) throws IOException{
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


