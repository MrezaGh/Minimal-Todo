package com.example.avjindersinghsekhon.minimaltodo.AddWorkHours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoFragment;
import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;

import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class AddWorkFragment extends AppDefaultFragment {

    AnalyticsApplication app;
    FloatingActionButton mAddWorkFloatingActionButton;

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

    private EditText workFromTime;
    private EditText workToTime;
    private Spinner workDays;




    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (AnalyticsApplication) getActivity().getApplication();

        setDaysSpinner(view);
        
        workFromTime = (EditText) view.findViewById(R.id.work_from);
        workToTime = (EditText) view.findViewById(R.id.work_to);
        workDays = (Spinner) view.findViewById(R.id.work_days_spinner); 
        mAddWorkFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.addWorkFloatingActionButton);
        

        mAddWorkFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workFromTime.length() <= 0) {
                    workFromTime.setError(getString(R.string.todo_error));
                } else {
                    app.send(this, "Action", "Add Work Hours");
                    makeResult(RESULT_OK);
                    getActivity().finish();
                }

                hideKeyboard(workFromTime);
                hideKeyboard(workToTime);
            }
        });

    }

    private void makeResult(int result) {
        Log.i("workDay ", workDays.getSelectedItem().toString());
        Log.i("workFrom ", workFromTime.getText().toString());
        Log.i("workTo ", workToTime.getText().toString());

    }

    private void setDaysSpinner(View view) {
        Spinner spin = (Spinner) view.findViewById(R.id.work_days_spinner);
        ArrayAdapter aa = new ArrayAdapter<>(getContext(),R.layout.spinner_item,this.days);
        aa.setDropDownViewResource(R.layout.spinner_item);
        spin.setAdapter(aa);
    }

    public void hideKeyboard(EditText et) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_add_work_hours;
    }
    public static AddWorkFragment newInstance() {
        return new AddWorkFragment();
    }
}
