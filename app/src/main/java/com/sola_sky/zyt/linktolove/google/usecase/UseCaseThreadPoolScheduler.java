package com.sola_sky.zyt.linktolove.google.usecase;


import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Li on 2016/4/27.
 */
public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private final Handler mHandler = new Handler();

    private static final int POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 5;
    private static final int TIMEOUT = 30;

    private ThreadPoolExecutor mThreadPoolExecutor;

    public UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <T extends UseCase.ResponseValues> void notifyResponse(final T response, final UseCase.UseCaseCallback<T> useCaseCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <T extends UseCase.ResponseValues> void onError(final Error error, final UseCase.UseCaseCallback<T> useCaseCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onError(error);
            }
        });
    }
}
