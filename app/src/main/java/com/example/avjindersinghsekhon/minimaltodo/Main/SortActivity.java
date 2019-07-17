package com.example.avjindersinghsekhon.minimaltodo.Main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Utility.SortConstraints;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;

public class SortActivity extends Activity {

    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.avjindersekhon.themepref";
    public static final String RECREATE_ACTIVITY = "com.avjindersekhon.recreateactivity";
    public static final String THEME_SAVED = "com.avjindersekhon.savedtheme";
    public static final String DARKTHEME = "com.avjindersekon.darktheme";
    public static final String LIGHTTHEME = "com.avjindersekon.lighttheme";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //We recover the theme we've set and setTheme accordingly
        theme = getBaseContext().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);

        if (theme.equals(LIGHTTHEME)) {
            mTheme = R.style.CustomStyle_LightTheme;
        } else {
            mTheme = R.style.CustomStyle_DarkTheme;
        }
        getBaseContext().setTheme(mTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sort);



        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_sort);

        SortConstraints s = StoreRetrieveData.getSorts(getBaseContext());
        if (s.getSortBy().equals("date"))
            radioGroup.check(R.id.sort_date);
        else if (s.getSortBy().equals("create time"))
            radioGroup.check(R.id.sort_create);
        else
            radioGroup.check(R.id.sort_importance);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioImportanceButton = (RadioButton) findViewById(selectedId);
                String sortBy = radioImportanceButton.getText().toString();
                SortConstraints s = StoreRetrieveData.getSorts(getBaseContext());
                s.setSortBy(sortBy);

                StoreRetrieveData.saveSort(getBaseContext(), s);
            }
        });

        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radio_group_inc);

        if (s.isIncrease())
            radioGroup1.check(R.id.sort_inc);
        else
            radioGroup1.check(R.id.sort_dec);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioImportanceButton = (RadioButton) findViewById(selectedId);
                String inc = radioImportanceButton.getText().toString();
                SortConstraints s = StoreRetrieveData.getSorts(getBaseContext());
                if (inc.equals("increase"))
                    s.setIncrease();
                else
                    s.setDecrease();
                StoreRetrieveData.saveSort(getBaseContext(), s);
            }
        });

        Button apply = (Button) findViewById(R.id.sort_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(-1);
                finish();
            }
        });
    }

}
