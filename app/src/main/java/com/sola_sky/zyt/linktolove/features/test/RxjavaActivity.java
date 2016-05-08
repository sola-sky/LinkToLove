package com.sola_sky.zyt.linktolove.features.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.utils.LogUtils;
import com.sola_sky.zyt.linktolove.utils.ToastUtils;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {

    private static final String TAG = "RxjavaActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        testRxjava();
    }

    public void testRxjava() {
        Subscriber<String> observer = new Subscriber<String>() {
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
        observer.unsubscribe();

        Observable.from(new String[]{"lmy", "zyt", "lsk", "zpp", "zss"})
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtils.logd(TAG, s);
                    }
                });

        Observable.just("i", "love", "you", "zyt")
                .subscribe(new Action1<String>() {

                    @Override
                    public void call(String s) {
                        LogUtils.logd(TAG, s);
                    }
                });

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtils.logd(TAG, "number:" + integer);
                    }
                });

        Observable.from(new String[]{"i", "u"})
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        LogUtils.logd(TAG, "main thread");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        ToastUtils.showToastLong(RxjavaActivity.this, s);
                    }
                });
    }

}
