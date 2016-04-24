package com.sola_sky.zyt.linktolove.features.test.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sola_sky.zyt.linktolove.BR;

/**
 * Created by Li on 2016/4/25.
 */
public class TaskObservable extends BaseObservable {
    public String firstName = "Li Minyi";

    @Bindable
    public String getName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.name);
    }
}
