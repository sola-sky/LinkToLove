package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.executor.BaseExecutor;
import com.sola_sky.zyt.linktolove.core.executor.MainThread;
import com.sola_sky.zyt.linktolove.core.executor.impl.ExecutorImpl;
import com.sola_sky.zyt.linktolove.core.executor.impl.MainThreadImpl;
import com.sola_sky.zyt.linktolove.core.interator.AbstractInteractor;
import com.sola_sky.zyt.linktolove.core.repository.DataRepository;

import java.util.concurrent.Executor;

/**
 * Created by Li on 2016/2/21.
 */
public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor {

    private LoginInteractor.Callback mCallback;
    private DataRepository mRepository;
    private MainThread mMainThread;


    public LoginInteractorImpl(LoginInteractor.Callback callback,
                               DataRepository dataRepository ) {
        super(ExecutorImpl.getInstance());
        mCallback = callback;
        mRepository = dataRepository;
        mMainThread = MainThreadImpl.getInstance();
    }




    private void postMessage(final String msg) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginSuccess(msg);
            }
        });

    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginFailure();
            }
        });

    }

    @Override
    public void run() {
        postMessage("success");
    }
}
