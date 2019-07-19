package com.example.avjindersinghsekhon.minimaltodo.AddWorkHours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoFragment;
import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;

import java.util.ArrayList;

public class AddWorkFragment extends AppDefaultFragment {
    AnalyticsApplication app;
    private ArrayList<String> days = new ArrayList<>();
    {
        days.add("Saturdays");
        days.add("Sundays");
        days.add("Mondays");
        days.add("Tuesdays");
        days.add("Wednesdays");
        days.add("Thursdays");
        days.add("Fridays");
    }



    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (AnalyticsApplication) getActivity().getApplication();

        setDaysSpinner(view);

    }

    private void setDaysSpinner(View view) {
        Spinner spin = (Spinner) view.findViewById(R.id.work_days_spinner);
        ArrayAdapter aa = new ArrayAdapter<>(getContext(),R.layout.spinner_item,this.days);
        aa.setDropDownViewResource(R.layout.spinner_item);
        spin.setAdapter(aa);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_add_work_hours;
    }
    public static AddWorkFragment newInstance() {
        return new AddWorkFragment();
    }
}
