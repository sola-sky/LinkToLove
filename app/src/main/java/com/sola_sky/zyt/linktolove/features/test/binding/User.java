package com.sola_sky.zyt.linktolove.features.test.binding;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by Li on 2016/4/29.
 */
public class User {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
}
