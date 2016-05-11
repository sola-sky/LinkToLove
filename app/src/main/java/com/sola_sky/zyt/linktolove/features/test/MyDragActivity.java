package com.sola_sky.zyt.linktolove.features.test;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.databinding.ActivityMyDragBinding;

public class MyDragActivity extends AppCompatActivity {

    private ActivityMyDragBinding binding;

    private boolean mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_drag);
//        setContentView(R.layout.activity_my_drag);
//        binding =  ActivityMyDragBinding.bind(findViewById(R.id.root));
        binding.setContent("first");
        binding.setName("I Love You");
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClick) {
                    binding.tv.setText("true");
                } else {
                    binding.tv.setText("false");
                }
                mClick = !mClick;
            }
        });
    }
}
