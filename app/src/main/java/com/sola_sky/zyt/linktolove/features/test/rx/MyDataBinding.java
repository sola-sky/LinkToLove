package com.sola_sky.zyt.linktolove.features.test.rx;

import android.databinding.BindingAdapter;
import android.widget.Button;

/**
 * Created by Li on 2016/5/8.
 */
public class MyDataBinding {
    @BindingAdapter({"android:click", "android:text"})
    public static void display(Button btn, String name, String name2) {
        btn.setText(name + ":" + name2);
    }
}
