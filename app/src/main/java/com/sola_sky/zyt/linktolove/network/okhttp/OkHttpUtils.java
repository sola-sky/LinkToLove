package com.sola_sky.zyt.linktolove.network.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Li on 2016/2/27.
 */
public class OkHttpUtils implements HttpRequest{
    private static OkHttpClient mClient = new OkHttpClient();

    @Override
    public void login() {
        final Request request = new Request.Builder()
                .url("https://github.com/sola-sky")
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
