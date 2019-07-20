package com.example.avjindersinghsekhon.minimaltodo.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class GroupWork {
    private ArrayList<String> emails;
    private String name;
    private String description;
    private String creatorEmail;


    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATOR = "creator";
    private static final String EMAILS = "emails";


    public GroupWork(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString(NAME);
        description = jsonObject.getString(DESCRIPTION);
        creatorEmail = jsonObject.getString(CREATOR);
        JSONArray ja = jsonObject.getJSONArray(EMAILS);
        for (int i = 0; i < ja.length() ; i++) {
            emails.add(ja.getString(i));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME, name);
        jsonObject.put(CREATOR, creatorEmail);
        jsonObject.put(DESCRIPTION, description);
        jsonObject.put(EMAILS, new JSONArray(emails));

        return jsonObject;
    }



    public GroupWork(String creatorEmail){
        this.creatorEmail = creatorEmail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmails(String email) {
        emails.add(email);
    }
}
