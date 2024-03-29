package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Reminder.ReminderActivity;

import java.util.ArrayList;
import java.util.UUID;

public class TodoNotificationService extends IntentService {
    public static final String TODOTEXT = "com.avjindersekhon.todonotificationservicetext";
    public static final String TODOUUID = "com.avjindersekhon.todonotificationserviceuuid";
    private String mTodoText;
    private UUID mTodoUUID;
    private Context mContext;
    private String importance;
    public TodoNotificationService() {
        super("TodoNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mTodoText = intent.getStringExtra(TODOTEXT);
        mTodoUUID = (UUID) intent.getSerializableExtra(TODOUUID);
        ArrayList<ToDoItem> mToDoItemsArrayList= MainFragment.mToDoItemsArrayList;
        for (ToDoItem var : mToDoItemsArrayList)
        {
            UUID a = var.getIdentifier();
            if(var.getIdentifier().equals(mTodoUUID)){
                importance=var.getImportance();
            }
        }

        Log.d("OskarSchindler", "onHandleIntent called");
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, ReminderActivity.class);
        i.putExtra(TodoNotificationService.TODOUUID, mTodoUUID);
        Intent deleteIntent = new Intent(this, DeleteNotificationService.class);
        deleteIntent.putExtra(TODOUUID, mTodoUUID);

        switch(importance) {
            case "very important":
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                Notification notification = new Notification.Builder(this)
                        .setContentTitle(mTodoText)
                        .setSmallIcon(R.drawable.ic_done_white_24dp)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSound(alarmSound)
                        .setDeleteIntent(PendingIntent.getService(this, mTodoUUID.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                        .build();


                manager.notify(100, notification);
                break;
            case "important":
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notification = new Notification.Builder(this)
                        .setContentTitle(mTodoText)
                        .setSmallIcon(R.drawable.ic_done_white_24dp)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSound(alarmSound)
                        .setDeleteIntent(PendingIntent.getService(this, mTodoUUID.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                        .build();


                manager.notify(100, notification);
                break;
            case "less important":

                notification = new Notification.Builder(this)
                        .setContentTitle(mTodoText)
                        .setSmallIcon(R.drawable.ic_done_white_24dp)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setDeleteIntent(PendingIntent.getService(this, mTodoUUID.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                        .build();


                manager.notify(100, notification);
                break;
            case "not important":

                notification = new Notification.Builder(this)
                        .setContentTitle(mTodoText)
                        .setSmallIcon(R.drawable.ic_done_white_24dp)
                        .setAutoCancel(true)

                        .setDeleteIntent(PendingIntent.getService(this, mTodoUUID.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                        .build();


                manager.notify(100, notification);
        }

//        Uri defaultRingone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        MediaPlayer mp = new MediaPlayer();
//        try{
//            mp.setDataSource(this, defaultRingone);
//            mp.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
//            mp.prepare();
//            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.release();
//                }
//            });
//            mp.start();
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
