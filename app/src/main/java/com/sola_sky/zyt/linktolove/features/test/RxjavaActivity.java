package com.sola_sky.zyt.linktolove.features.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.utils.LogUtils;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class RxjavaActivity extends AppCompatActivity {

    private static final String TAG = "RxjavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        testRxjava();
    }

    public void testRxjava() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                LogUtils.logd(TAG, "ni hao:" + s);
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world");
            }
        });

        observable.subscribe(observer);
    }
}
