package com.sola_sky.zyt.linktolove.features.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.databinding.ActivityMyDragBinding;
import com.sola_sky.zyt.linktolove.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {

    private ActivityTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        binding.setText("sad");
    }

    private void click(View view) {
        binding.setText("happy");
    }
}
