package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.executor.BaseExecutor;
import com.sola_sky.zyt.linktolove.core.interator.AbstractInteractor;
import com.sola_sky.zyt.linktolove.core.repository.DataRepository;

/**
 * Created by Li on 2016/2/23.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.Callback{

    private LoginPresenter.View mView;
    private UserInfo mUserInfo;

    LoginPresenterImpl(LoginPresenter.View view, UserInfo userInfo) {
        mView = view;
        mUserInfo = userInfo;
    }
    @Override
    public void resume() {
        LoginInteractor interactor = new LoginInteractorImpl(this,
                new LoginDataRepository(mUserInfo));
        interactor.execute();
    }

    @Override
    public void onLoginSuccess(String message) {
        mView.loginSuccess(message);
    }

    @Override
    public void onLoginFailure() {
        mView.loginError();
    }
}
