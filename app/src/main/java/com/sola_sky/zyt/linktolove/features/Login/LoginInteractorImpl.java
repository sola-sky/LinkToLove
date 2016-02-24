package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.executor.BackgroundRun;
import com.sola_sky.zyt.linktolove.core.interator.AbstractInteractor;
import com.sola_sky.zyt.linktolove.core.repository.DataRepository;

/**
 * Created by Li on 2016/2/21.
 */
public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor {

    private LoginInteractor.Callback mCallback;
    private DataRepository mRepository;


    public LoginInteractorImpl(BackgroundRun executor, LoginInteractor.Callback callback,
                               DataRepository dataRepository ) {
        super(executor);
        mCallback = callback;
        mRepository = dataRepository;
    }




    private void postMessage(final String msg) {
        mCallback.onLoginSuccess(msg);
    }

    private void notifyError() {
        mCallback.onLoginFailure();
    }

    @Override
    public void run() {
        String msg = mRepository.getMessage();
        if (msg == null || msg.length() == 0) {
           notifyError();
        } else {
           postMessage(msg);
        }
    }
}
