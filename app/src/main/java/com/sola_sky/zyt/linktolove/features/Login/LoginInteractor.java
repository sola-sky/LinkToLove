package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.interator.Interactor;

/**
 * Created by Li on 2016/2/21.
 */
public interface LoginInteractor extends Interactor {
    interface Callback {
        void onLoginSuccess(String message);
        void onLoginFailure(String error);
    }
}
