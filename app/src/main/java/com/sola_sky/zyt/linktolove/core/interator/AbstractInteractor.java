package com.sola_sky.zyt.linktolove.core.interator;



import com.sola_sky.zyt.linktolove.core.executor.BaseExecutor;

/**
 * Created by Li on 2016/2/21.
 */
public abstract class AbstractInteractor implements Interactor {
    private BaseExecutor mExecutor;
    public abstract void run();


    protected volatile boolean mIsCancel;
    protected volatile boolean mIsRunning;

    public AbstractInteractor(BaseExecutor executor) {
        mExecutor = executor;
    }
    @Override
    public void execute() {
        this.mIsRunning = true;
        mExecutor.execute(this);
    }
}
