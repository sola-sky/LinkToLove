package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.executor.BackgroundRun;
import com.sola_sky.zyt.linktolove.core.interator.AbstractInteractor;
import com.sola_sky.zyt.linktolove.core.interator.Interactor;
import com.sola_sky.zyt.linktolove.core.repository.DataRepository;

/**
 * Created by Li on 2016/2/23.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.Callback{

    private LoginPresenter.View mView;

    LoginPresenterImpl(LoginPresenter.View view) {
        mView = view;
    }
    @Override
    public void resume() {
        LoginInteractor interactor = new LoginInteractorImpl(new BackgroundRun() {
            @Override
            public void exectue(AbstractInteractor interactor) {

            }
        }, this, new DataRepository() {
            @Override
            public String getMessage() {
                return null;
            }
        });
        interactor.execute();
    }

    @Override
    public void onLoginSuccess(String message) {
        mView.loginSuccess();
    }

    @Override
    public void onLoginFailure() {
        mView.loginError();
    }
}
