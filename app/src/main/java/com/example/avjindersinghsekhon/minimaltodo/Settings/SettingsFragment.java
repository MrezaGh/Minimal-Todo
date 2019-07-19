package com.example.avjindersinghsekhon.minimaltodo.Settings;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;

import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Utility.LocaleHelper;
import com.example.avjindersinghsekhon.minimaltodo.Utility.PreferenceKeys;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    AnalyticsApplication app;
    private static final String TAG = "SettingFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_layout);
        app = (AnalyticsApplication) getActivity().getApplication();
        findPreference("newTypePreference").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceKeys preferenceKeys = new PreferenceKeys(getResources());
                EditTextPreference editTextPreference = (EditTextPreference) findPreference(preferenceKeys.newType_pref_key);
                String newType = editTextPreference.getEditText().getText().toString();
                try {
                    StoreRetrieveData.addType(getContext(),newType);
                    ArrayList<String> array = StoreRetrieveData.getTypes(getContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PreferenceKeys preferenceKeys = new PreferenceKeys(getResources());
        if (key.equals(preferenceKeys.night_mode_pref_key)) {
            SharedPreferences themePreferences = getActivity().getSharedPreferences(MainFragment.THEME_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor themeEditor = themePreferences.edit();
            //We tell our MainLayout to recreate itself because mode has changed
            themeEditor.putBoolean(MainFragment.RECREATE_ACTIVITY, true);

            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(preferenceKeys.night_mode_pref_key);
            if (checkBoxPreference.isChecked()) {
                //Comment out this line if not using Google Analytics
                app.send(this, "Settings", "Night Mode used");
                themeEditor.putString(MainFragment.THEME_SAVED, MainFragment.DARKTHEME);
            } else {
                themeEditor.putString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
            }
            themeEditor.apply();

            getActivity().recreate();
        }else if(key.equals(preferenceKeys.language_pref_key)){
            String mLanguageCode;
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(preferenceKeys.language_pref_key);
            if (checkBoxPreference.isChecked()) {
                mLanguageCode="fa";
//                LocaleHelper.setLocale(getActivity(), mLanguageCode);
//                  getActivity().finish();
                String languageToLoad  = "fa";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getActivity().getResources().updateConfiguration(config,
                        getActivity().getResources().getDisplayMetrics());
//                getActivity().setContentView(R.layout.activity_settings);
                getActivity().finish();

            } else {
                mLanguageCode="en";
//                LocaleHelper.setLocale(getActivity(), mLanguageCode);
//                getActivity().finish();
                String languageToLoad  = "en";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getActivity().getResources().updateConfiguration(config,
                        getActivity().getResources().getDisplayMetrics());
                getActivity().finish();
            }

        }else if (key.equals(preferenceKeys.newType_pref_key)){
            EditTextPreference editTextPreference = (EditTextPreference) findPreference(preferenceKeys.newType_pref_key);
            String newType = editTextPreference.getEditText().getText().toString();
            try {
                StoreRetrieveData.addType(getContext(),newType);
                ArrayList<String> array = StoreRetrieveData.getTypes(getContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


}
