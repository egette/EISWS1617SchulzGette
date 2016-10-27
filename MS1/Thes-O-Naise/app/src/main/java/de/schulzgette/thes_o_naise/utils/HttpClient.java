package de.schulzgette.thes_o_naise.utils;

import android.app.Application;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
//import okhttp3.Response;


/**
 * Statischer HTTP Client
 */
public class HttpClient extends Application {


    private static final String BASE_URL ="http://10.0.2.2:3000/";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static String token = Authentication.token;

    public static OkHttpClient client = new OkHttpClient();

    public HttpClient() {
    }


    public static Call POST(String url, String json, Callback callback) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
//                .addHeader("Cookie", token)
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Call GET(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Cookie", token)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static Call PUT(String url, String json, Callback callback) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Cookie", token)
                .url(url)
                .put(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static Call DELETE (String url, String json, Callback callback) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Cookie", token)
                .url(BASE_URL+url)
                .delete(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }
}


