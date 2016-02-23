package com.sola_sky.zyt.linktolove.core.interator;



import com.sola_sky.zyt.linktolove.core.executor.BackgroundRun;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Li on 2016/2/21.
 */
public abstract class AbstractInteractor implements Interactor {
    private BackgroundRun mExecutor;
    public abstract void run();


    protected volatile boolean mIsCancel;
    protected volatile boolean mIsRunning;

    public AbstractInteractor(BackgroundRun executor) {
        mExecutor = executor;
    }
    @Override
    public void execute() {
        this.mIsRunning = true;
        mExecutor.exectue(this);
    }
}
