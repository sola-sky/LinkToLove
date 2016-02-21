package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.executor.BackgroundRun;
import com.sola_sky.zyt.linktolove.core.interator.AbstractInteractor;

/**
 * Created by Li on 2016/2/21.
 */
public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor {

    private LoginInteractor.Callback mCallback;


    public LoginInteractorImpl(BackgroundRun executor, LoginInteractor.Callback callback) {
        super(executor);
        mCallback = callback;
    }

    private void postMessage(final String msg) {

    }

    private void notifyError() {

    }

    @Override
    public void run() {

        if (true) {
            postMessage("");
        } else {
            notifyError();
        }
    }
}
