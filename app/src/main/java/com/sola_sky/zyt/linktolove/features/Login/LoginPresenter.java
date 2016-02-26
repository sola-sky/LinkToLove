package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.presenter.BasePresenter;
import com.sola_sky.zyt.linktolove.presenter.BaseView;

/**
 * Created by Li on 2016/2/23.
 */
public interface LoginPresenter extends BasePresenter {
    interface View extends BaseView {
        void loginSuccess(String msg);
        void loginError();
    }
}
