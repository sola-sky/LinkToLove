package com.sola_sky.zyt.linktolove.features.Login;

/**
 * Created by Li on 2016/2/27.
 */
public class UserInfo {


    private String mUserAccount;
    private String mUserPassword;



    public UserInfo(String account, String password) {
        mUserAccount = account;
        mUserPassword = password;
    }

    public String getUserAccount() {
        return mUserAccount;
    }

    public String getUserPassword() {
        return mUserPassword;
    }
}
