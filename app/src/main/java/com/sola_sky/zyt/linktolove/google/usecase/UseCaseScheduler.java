package com.sola_sky.zyt.linktolove.google.usecase;

/**
 * Created by Li on 2016/4/27.
 */
public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <T extends UseCase.ResponseValues> void notifyResponse(final T response,
                                                           final UseCase.UseCaseCallback<T> useCaseCallback);
    <T extends UseCase.ResponseValues> void onError(final Error error,
                                                    final UseCase.UseCaseCallback<T> useCaseCallback);
}
