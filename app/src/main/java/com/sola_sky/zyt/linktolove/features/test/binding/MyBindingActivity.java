package com.sola_sky.zyt.linktolove.features.test.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.databinding.ActivityMyBindingBinding;

public class MyBindingActivity extends AppCompatActivity {

    private TaskObservable taskObservable;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyBindingBinding  binding = DataBindingUtil.setContentView(this, R.layout.activity_my_binding);
      //  ActivityMyBindingBinding binding = ActivityMyBindingBinding.inflate(getLayoutInflater());
        binding.setName("hhhhh");
        taskObservable = new TaskObservable();
        binding.setTask(taskObservable);
//        mBtn = (Button) findViewById(R.id.btn);
//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                taskObservable.setName("Zhang");
//            }
//        });
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskObservable.setName("zhang ya...");
                user.lastName.set("Li Minyi");
            }
        });
        binding.setUser(user);
    }


}
