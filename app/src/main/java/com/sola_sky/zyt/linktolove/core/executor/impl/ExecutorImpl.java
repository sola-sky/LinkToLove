package com.sola_sky.zyt.linktolove.core.executor.impl;

import com.sola_sky.zyt.linktolove.core.executor.BaseExecutor;
import com.sola_sky.zyt.linktolove.core.interator.AbstractInteractor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Li on 2016/2/26.
 */
public class ExecutorImpl implements BaseExecutor {
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ExecutorImpl() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 5, 120, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    @Override
    public void execute(final AbstractInteractor interactor) {
        mThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                interactor.run();
            }
        });
    }

    public static ExecutorImpl getInstance() {
        return ExecutorImplHolder.executor;
    }
    private static class ExecutorImplHolder {
        private static final ExecutorImpl executor = new ExecutorImpl();
    }


}
