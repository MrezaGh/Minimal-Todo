package com.example.avjindersinghsekhon.minimaltodo.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Utility.FilterConstraints;
import com.example.avjindersinghsekhon.minimaltodo.Utility.SortConstraints;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SortFragment extends AppDefaultFragment {

    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.avjindersekhon.themepref";
    public static final String RECREATE_ACTIVITY = "com.avjindersekhon.recreateactivity";
    public static final String THEME_SAVED = "com.avjindersekhon.savedtheme";
    public static final String DARKTHEME = "com.avjindersekon.darktheme";
    public static final String LIGHTTHEME = "com.avjindersekon.lighttheme";
    private AnalyticsApplication app;


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (AnalyticsApplication) getActivity().getApplication();

        //We recover the theme we've set and setTheme accordingly
        theme = getActivity().getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);

        if (theme.equals(LIGHTTHEME)) {
            mTheme = R.style.CustomStyle_LightTheme;
        } else {
            mTheme = R.style.CustomStyle_DarkTheme;
        }
        this.getActivity().setTheme(mTheme);

        super.onCreate(savedInstanceState);



        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_sort);

        SortConstraints s = StoreRetrieveData.getSorts(getActivity());
        if (s.getSortBy().equals("date"))
            radioGroup.check(R.id.sort_date);
        else
            radioGroup.check(R.id.sort_importance);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioImportanceButton = (RadioButton) view.findViewById(selectedId);
                String sortBy = radioImportanceButton.getText().toString();
                SortConstraints s = StoreRetrieveData.getSorts(getActivity());
                s.setSortBy(sortBy);
                StoreRetrieveData.saveSort(getActivity(), s);
            }
        });

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.radio_group_inc);

        if (s.isIncrease())
            radioGroup1.check(R.id.sort_inc);
        else
            radioGroup1.check(R.id.sort_dec);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioImportanceButton = (RadioButton) view.findViewById(selectedId);
                String inc = radioImportanceButton.getText().toString();
                SortConstraints s = StoreRetrieveData.getSorts(getActivity());
                if (inc.equals("increase"))
                    s.setIncrease();
                else
                    s.setDecrease();
                StoreRetrieveData.saveSort(getActivity(), s);
            }
        });

        Button apply = (Button) view.findViewById(R.id.sort_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        app.send(this);

    }

    @Override
    public void onStart() {
        app = (AnalyticsApplication) getActivity().getApplication();
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sort;
    }
}
