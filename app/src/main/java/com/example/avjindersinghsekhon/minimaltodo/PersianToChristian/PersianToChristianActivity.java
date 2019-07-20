package com.example.avjindersinghsekhon.minimaltodo.PersianToChristian;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;

public class PersianToChristianActivity extends AppDefaultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_persian_to_christian;
    }

    @NonNull
    @Override
    protected Fragment createInitialFragment() {
        return PersianToChristianFragment.newInstance();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

}
