package com.example.avjindersinghsekhon.minimaltodo.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.avjindersinghsekhon.minimaltodo.About.AboutActivity;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Settings.SettingsActivity;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;

import java.util.ArrayList;
import java.util.Date;

import static com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment.getLocallyStoredData;

public class MainActivity extends AppDefaultActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        StoreRetrieveData.saveFullFilter(this);
        StoreRetrieveData.saveFullSort(this);
    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected Fragment createInitialFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem warner = menu.findItem(R.id.menu_warner);
        int pastNearSafe = check_if_deadline_is_near();// safe = 0, near = 1, past = 2
        if (pastNearSafe == 0)
            warner.setIcon(R.drawable.done_icon);
        else if(pastNearSafe == 1){
            warner.setIcon(R.drawable.near_deadline_icon);
        }
        else if (pastNearSafe == 2){
            warner.setIcon(R.drawable.warning_icon);
        }

        return true;
    }

    private int check_if_deadline_is_near() {

        boolean pastDeadline = false;
        boolean nearDeadline = false;
        Date currentTime = new Date();
        StoreRetrieveData storeRetrieveData = new StoreRetrieveData(this, "todoitems.json");
        ArrayList<ToDoItem> mToDoItemsArrayList = getLocallyStoredData(storeRetrieveData);
        for (ToDoItem item : mToDoItemsArrayList){
//            Log.i("item time: ", item.getToDoDate().toString());
//            Log.i("current time: ", currentTime.toString());
//            Log.i("is it before: ", String.valueOf(item.getToDoDate().before(currentTime)));
            if (item.getToDoDate().before(new Date())){
                pastDeadline = true;
            }
            long hoursToDeadline = (item.getToDoDate().getTime() - currentTime.getTime())/(1000*60*60);
            if (hoursToDeadline >= 0 && hoursToDeadline <= 2){
                nearDeadline = true;
            }

        }
        if (pastDeadline)
            return 2;
        if (nearDeadline)
            return 1;
        return 0;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutMeMenuItem:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
//            case R.id.switch_themes:
//                if(mTheme == R.style.CustomStyle_DarkTheme){
//                    addThemeToSharedPreferences(LIGHTTHEME);
//                }
//                else{
//                    addThemeToSharedPreferences(DARKTHEME);
//                }
//
////                if(mTheme == R.style.CustomStyle_DarkTheme){
////                    mTheme = R.style.CustomStyle_LightTheme;
////                }
////                else{
////                    mTheme = R.style.CustomStyle_DarkTheme;
////                }
//                this.recreate();
//                return true;
            case R.id.preferences:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


