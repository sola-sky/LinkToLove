package com.sola_sky.zyt.linktolove.core.executor.impl;

import android.os.Handler;
import android.os.Looper;

import com.sola_sky.zyt.linktolove.core.executor.MainThread;



/**
 * Created by Li on 2016/2/26.
 */
public class MainThreadImpl implements MainThread {
    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static MainThreadImpl getInstance() {
        return MainThreadImplHolder.thread;
    }

    private static class MainThreadImplHolder {
        private static final MainThreadImpl thread = new MainThreadImpl();
    }
}
