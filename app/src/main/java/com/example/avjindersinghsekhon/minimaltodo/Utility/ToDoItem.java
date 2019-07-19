package com.example.avjindersinghsekhon.minimaltodo.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ToDoItem implements Serializable {
    private String mToDoText;
    private boolean mHasReminder;
    //add description
    private String mToDoDescription;
    //    private Date mLastEdited;
    private int mTodoColor;
    private Date mToDoDate;
    private Date createTime;
    private UUID mTodoIdentifier;
    private ArrayList<String> attachPaths = new ArrayList<>();
    private String importance;//////////
    private String type;//////////
    //add description
    private static final String TODODESCRIPTION = "tododescription";
    private static final String TODOTEXT = "todotext";
    private static final String TODOREMINDER = "todoreminder";
    //    private static final String TODOLASTEDITED = "todolastedited";
    private static final String TODOCOLOR = "todocolor";
    private static final String TYPE = "type";
    private static final String IMPORTANCE = "import";
    private static final String ATTACH = "attach";
    private static final String CREATETIME = "c_time";
    private static final String TODODATE = "tododate";
    private static final String TODOIDENTIFIER = "todoidentifier";


    public ToDoItem(String todoBody,String tododescription, String type, String importance, Date createTime, boolean hasReminder, Date toDoDate) {
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mToDoDescription = tododescription;
        mTodoColor = 1677725;
        mTodoIdentifier = UUID.randomUUID();
        this.importance = importance;
        this.type = type;
        this.createTime = createTime;
    }

    public ToDoItem(JSONObject jsonObject) throws JSONException {
        mToDoText = jsonObject.getString(TODOTEXT);
        mToDoDescription = jsonObject.getString(TODODESCRIPTION);
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
        mTodoColor = jsonObject.getInt(TODOCOLOR);
        type = jsonObject.getString(TYPE);
        importance = jsonObject.getString(IMPORTANCE);
        JSONArray ja = jsonObject.getJSONArray(ATTACH);
        for (int i = 0; i < ja.length() ; i++) {
            attachPaths.add(ja.getString(i));
        }
        createTime = new Date(jsonObject.getLong(CREATETIME));

        mTodoIdentifier = UUID.fromString(jsonObject.getString(TODOIDENTIFIER));

//        if(jsonObject.has(TODOLASTEDITED)){
//            mLastEdited = new Date(jsonObject.getLong(TODOLASTEDITED));
//        }
        if (jsonObject.has(TODODATE)) {
            mToDoDate = new Date(jsonObject.getLong(TODODATE));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TYPE, type);
        jsonObject.put(IMPORTANCE, importance);
        jsonObject.put(ATTACH, new JSONArray(attachPaths));
        jsonObject.put(CREATETIME, createTime.getTime());
        jsonObject.put(TODOREMINDER, mHasReminder);
        jsonObject.put(TODODESCRIPTION, mToDoDescription);
//        jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
        if (mToDoDate != null) {
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        return jsonObject;
    }


    public String getmToDoDescription() { return mToDoDescription;}

    public void setmToDoDescription(String mToDoDescription){this.mToDoDescription = mToDoDescription;}

    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public boolean hasReminder() {
        return mHasReminder;
    }

    public void setHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getToDoDate() {
        return mToDoDate;
    }

    public int getTodoColor() {
        return mTodoColor;
    }

    public void setTodoColor(int mTodoColor) {
        this.mTodoColor = mTodoColor;
    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }


    public UUID getIdentifier() {
        return mTodoIdentifier;
    }

    public String getType() {
        return type;
    }

    public String getImportance() {
        return importance;
    }

    public ArrayList<String> getAttachPaths() {
        return attachPaths;
    }

    public void addAttachPath(String attachPath) {
        this.attachPaths.add(attachPath);
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAttahPaths(ArrayList<String> mUserAttachs) {
        this.attachPaths = mUserAttachs;
    }
}

