package com.sola_sky.zyt.linktolove.features.Login;

import com.sola_sky.zyt.linktolove.core.repository.DataRepository;
import com.sola_sky.zyt.linktolove.network.okhttp.HttpRequest;
import com.sola_sky.zyt.linktolove.network.okhttp.OkHttpUtils;

/**
 * Created by Li on 2016/2/27.
 */
public class LoginDataRepository implements DataRepository{

    private UserInfo mUserInfo;

    public LoginDataRepository(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    @Override
    public String getData() {
        HttpRequest request = new OkHttpUtils();
        request.login();
        return null;
    }
}
