package com.sola_sky.zyt.linktolove.features.test;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.databinding.ActivityMyBindingBinding;

public class MyBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyBindingBinding  binding = DataBindingUtil.setContentView(this, R.layout.activity_my_binding);
        binding.setName("hhhhh");
    }
}
