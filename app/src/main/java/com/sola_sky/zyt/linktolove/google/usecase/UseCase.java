package com.sola_sky.zyt.linktolove.google.usecase;

/**
 * Created by Li on 2016/4/27.
 */
public abstract class UseCase<Q extends UseCase.RequestValues,
        V extends UseCase.ResponseValues> {

    public Q mRequestValues;
    public UseCaseCallback<V> mUseCaseCallback;

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(Q requestValues) {
        this.mRequestValues = requestValues;
    }

    public UseCaseCallback<V> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<V> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    void run() {
        executeUseCase(mRequestValues);
    }
    protected abstract void executeUseCase(Q requestValues);

    public interface RequestValues {

    }

    public interface ResponseValues {

    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);
        void onError();
    }
}
