package com.example.avjindersinghsekhon.minimaltodo.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Utility.FilterConstraints;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FilterFragment extends AppDefaultFragment {

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        Button f1 = (Button) view.findViewById(R.id.filter_importance);
        final String[] items = {"very important", "important", "less important", "not important"};
        final boolean[] checked = new boolean[4];
        final ArrayList<Integer> choices = new ArrayList<>();

        FilterConstraints f = StoreRetrieveData.getFilters(getActivity());
        for (int i = 0; i < f.getImportanceConstraints().size() ; i++) {
            String s = f.getImportanceConstraints().get(i);
            if (s.equals("very important")){
                checked[0] = true;
                choices.add(0);
            }
            if (s.equals("important")){
                checked[1] = true;
                choices.add(1);
            }
            if (s.equals("less important")){
                checked[2] = true;
                choices.add(2);
            }
            if (s.equals("not important")){
                checked[3] = true;
                choices.add(3);
            }
        }

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Categories");
                builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if (!choices.contains(i)){
                            choices.add(i);
                        } else {
                            choices.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FilterConstraints f = StoreRetrieveData.getFilters(getActivity());
                        f.clearImportance();
                        for (int j = 0; j < choices.size() ; j++) {
                            f.addImportanceConstraint(items[choices.get(j)]);
                        }
                        StoreRetrieveData.saveFilter(getActivity(), f);
                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        ///////////////////////////////////////type

        Button t = (Button) view.findViewById(R.id.filter_type);
        final String[] items1 = {"No Type", "Work", "University", "Recreation"};
        final boolean[] checked1 = new boolean[4];
        final ArrayList<Integer> choices1 = new ArrayList<>();

        FilterConstraints f2 = StoreRetrieveData.getFilters(getActivity());
        for (int i = 0; i < f2.getTypeConstraints().size() ; i++) {
            String s = f2.getTypeConstraints().get(i);
            int index = -1;
            for (int ii = 0; ii < items1.length; ii++)
                if (s.equals(items1[ii])) {
                    index = ii;
                    break;
                }
                checked1[index] = true;
                choices1.add(index);
        }

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Categories");
                builder.setMultiChoiceItems(items1, checked1, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if (!choices1.contains(i)){
                            choices1.add(i);
                        } else {
                            choices1.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FilterConstraints f = StoreRetrieveData.getFilters(getActivity());
                        f.clearType();
                        for (int j = 0; j < choices1.size() ; j++) {
                            f.addTypeConstraint(items1[choices1.get(j)]);
                        }
                        StoreRetrieveData.saveFilter(getActivity(), f);
                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button apply = (Button) view.findViewById(R.id.filter_apply);
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
        return R.layout.fragment_filter;
    }
}
