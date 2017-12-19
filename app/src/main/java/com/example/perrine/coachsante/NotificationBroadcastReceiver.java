package com.example.perrine.coachsante;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Perrine on 18/12/2017.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        Intent inputMealIntent = new Intent(context, InputMealActivity.class);
        inputMealIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder tsk = TaskStackBuilder.create(context);
        tsk.addParentStack(NotificationSettingsActivity.class);
        tsk.addNextIntent(inputMealIntent);


        //PendingIntent pi = PendingIntent.getActivity(context,  100, inputMealIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pi = tsk.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context);
        Notification notification = notifBuilder.setContentTitle(context.getResources().getString(R.string.titleNotifications))
                        .setContentText(context.getResources().getString(R.string.contentNotifications))
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setAutoCancel(true)
                        .setContentIntent(pi).build();



        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, notification);


    }


}
