package com.example.avjindersinghsekhon.minimaltodo.AddWorkHours;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;

public class AddWorkActivity extends AppDefaultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_add_work_hours;
    }

    @NonNull
    @Override
    protected Fragment createInitialFragment() {
        return AddWorkFragment.newInstance();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

}
