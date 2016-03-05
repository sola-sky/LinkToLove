package com.sola_sky.zyt.linktolove;

import com.sola_sky.zyt.linktolove.core.executor.BaseExecutor;
import com.sola_sky.zyt.linktolove.core.repository.DataRepository;
import com.sola_sky.zyt.linktolove.features.Login.LoginInteractor;
import com.sola_sky.zyt.linktolove.features.Login.LoginInteractorImpl;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Li on 2016/2/23.
 */
public class LoginMessageTest {


//    @Test
//    public void testLogin() {
//        String msg = "Login Success";
//        DataRepository mRepository = mock(DataRepository.class);
//        when(mRepository.getMessage()).thenReturn(msg);
//
//        BaseExecutor run = mock(BaseExecutor.class);
//        LoginInteractor.Callback mCallback = mock(LoginInteractor.Callback.class);
//
//        LoginInteractorImpl interactor = new LoginInteractorImpl(run, mCallback, mRepository);
//        interactor.run();
//        Mockito.verify(mRepository).getMessage();
//        Mockito.verifyNoMoreInteractions(mRepository);
//        Mockito.verify(mCallback).onLoginSuccess(msg);
//
//    }

}
