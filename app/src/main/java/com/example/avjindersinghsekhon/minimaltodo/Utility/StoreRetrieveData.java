package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class StoreRetrieveData {
    private static final String TYPES_ = "types_";
    private Context mContext;
    private String mFileName;

    public StoreRetrieveData(Context context, String filename) {
        mContext = context;
        mFileName = filename;
    }

    public static JSONArray toJSONArray(ArrayList<ToDoItem> items) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (ToDoItem item : items) {
            JSONObject jsonObject = item.toJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static void saveFullFilter(Context activity) {
        SharedPreferences mPrefs = activity.getSharedPreferences("sf", MODE_PRIVATE);
        FilterConstraints filters = new FilterConstraints();
        filters.addAllImportanceConstraint();
        filters.addAllTypeConstraint();

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(filters);
        prefsEditor.putString("filters", json);
        prefsEditor.apply();
    }

    public static void saveFilter(Context activity, FilterConstraints filterConstraints) {
        SharedPreferences mPrefs = activity.getSharedPreferences("sf", MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(filterConstraints);
        prefsEditor.putString("filters", json);
        prefsEditor.apply();
    }

    public static FilterConstraints getFilters(Context activity){
        SharedPreferences mPrefs = activity.getSharedPreferences("sf", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("filters", "");
        return gson.fromJson(json, FilterConstraints.class);
    }


    public static void saveFullSort(Context activity) {
        SharedPreferences mPrefs = activity.getSharedPreferences("sf", MODE_PRIVATE);
        SortConstraints sorts = new SortConstraints();
        sorts.setIncrease();
        sorts.setSortByCreateDate();

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sorts);
        prefsEditor.putString("sorts", json);
        prefsEditor.apply();
    }

    public static void saveSort(Context activity, SortConstraints sortConstraints) {
        SharedPreferences mPrefs = activity.getSharedPreferences("sf", MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sortConstraints);
        prefsEditor.putString("sorts", json);
        prefsEditor.apply();
    }

    public static SortConstraints getSorts(Context activity){
        SharedPreferences mPrefs = activity.getSharedPreferences("sf", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("sorts", "");
        return gson.fromJson(json, SortConstraints.class);
    }

    public static void loadTypes(Context context) throws JSONException {
        SharedPreferences mPrefs = context.getSharedPreferences("types", MODE_PRIVATE);
        JSONObject jsonObject = new JSONObject();
        if (!mPrefs.contains("types_")){
            ArrayList<String> types = new ArrayList<>();
            {
                types.add("No Type");
                types.add("Work");
                types.add("University");
                types.add("Recreation");
            }
            jsonObject.put(TYPES_, new JSONArray(types));
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putString("types_", "");
            prefsEditor.apply();
        }
    }

    public static ArrayList<String> getTypes(Context context) throws JSONException {
        ArrayList<String> types = new ArrayList<>();
        JSONArray ja;
        JSONObject jsonObject = new JSONObject();
        ja = jsonObject.getJSONArray(TYPES_);
        for (int i = 0; i < ja.length() ; i++) {
            types.add(ja.getString(i));
        }
        return types;
    }

    public static void addType(Context context, String newType) throws JSONException {
        ArrayList<String> types = getTypes(context);
        if (types.contains(newType))
            return;
        types.add(newType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPES_, new JSONArray(types));
    }

    public static void removeType(Context context, String removedType) throws JSONException {
        SharedPreferences mPrefs = context.getSharedPreferences("types", MODE_PRIVATE);

        ArrayList<String> types = getTypes(context);
        if (!types.contains(removedType))
            return;
        types.remove(removedType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPES_, new JSONArray(types));
    }

    public void saveToFile(ArrayList<ToDoItem> items) throws JSONException, IOException {
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        fileOutputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(toJSONArray(items).toString());
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    public ArrayList<ToDoItem> loadFromFile() throws IOException, JSONException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = mContext.openFileInput(mFileName);
            StringBuilder builder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(builder.toString()).nextValue();
            for (int i = 0; i < jsonArray.length(); i++) {
                ToDoItem item = new ToDoItem(jsonArray.getJSONObject(i));
                items.add(item);
            }


        } catch (FileNotFoundException fnfe) {
            //do nothing about it
            //file won't exist first time app is run
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }

        }
        return items;
    }

}
