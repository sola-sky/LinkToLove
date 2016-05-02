package com.sola_sky.zyt.linktolove.features.test.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.Observers;

public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 1; i < 10; i++) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                LogUtils.logd("TAG", "complete");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                LogUtils.logd("TAG", integer + "");
            }
        });

        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return null;
            }
        });

        Observable.empty();
        Observable.never();
        Observable.from(new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        });

        Observable.from(new String[]{"1", "b"});

        Observable.interval(1000, TimeUnit.SECONDS);

        Observable.just("2", "3", "5");
        Observable.just("3","2");
        Observable.just("3");
        Observable.just("4", "33").repeat();
        Observable.just("3").repeat(10);
        Observable.just("f").repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return observable;
            }
        });

        Observable.timer(1000, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {

            }
        });

        Observable.just("1", "2", "3")
                .buffer(2);

        Observable.just("1","2", "3")
                .buffer(new Func0<Observable<String>>() {
                    @Override
                    public Observable<String> call() {
                        return null;
                    }
                });


        Observable.just("2")
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        return null;
                    }
                });
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        }).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return null;
            }
        });

        Observable.just(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        })).flatMap(new Func1<Observable<String>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<String> stringObservable) {
                return null;
            }
        }).groupBy(new Func1<Object, Integer>() {
            @Override
            public Integer call(Object o) {
                return null;
            }
        });


        Observable.range(3, 5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer - integer;
                    }
                });

        Observable.just("2", "3")
                .scan(2, new Func2<Integer, String, Integer>() {
                    @Override
                    public Integer call(Integer integer, String s) {
                        return null;
                    }
                });

        Observable.just("2", "3")
                .window(3);


        Observable.just("3", "4")
                .throttleWithTimeout(5, TimeUnit.SECONDS);

        Observable.just("3", "5")
                .debounce(5, TimeUnit.SECONDS);

        Observable.just("5", "5")
                .debounce(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return null;
                    }
                });

        Observable.just("5", "4")
                .distinct(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return null;
                    }
                });

        Observable.just("t")
                .elementAt(0);

        Observable.from(new String[]{"2,3", "4, 5"})
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("2,3");
                    }
                });

        Observable.from(new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return "ZYT";
            }
        }).ofType(String.class);

        Observable.just("2", "3")
                .ignoreElements();

        Observable.just("3", "3")
                .sample(3, TimeUnit.SECONDS);

        Observable.just(8)
                .limit(8)
                .take(8);


        Observable.just("8")
                .join()

    }
}
