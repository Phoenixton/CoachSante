package com.example.perrine.coachsante;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Perrine on 18/12/2017.
 */

public class NotificationSettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button addAlarm;
    private TimePicker tp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tp = (TimePicker) findViewById(R.id.timePicker);

        addAlarm = (Button)findViewById(R.id.addAlarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar cal = Calendar.getInstance();

                if(Build.VERSION.SDK_INT >= 23){
                    cal.set(
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH),
                            tp.getHour(),
                            tp.getMinute(),
                            0
                    );

                } else {

                    cal.set(
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH),
                            tp.getCurrentHour(),
                            tp.getCurrentMinute(),
                            0
                    );
                }


                //Intent intentBroadcastReceiver = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
                Intent intent = new Intent("com.example.perrine.action.NOTIFICATION");

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                        100,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


                Toast.makeText(getApplicationContext(), getResources().getString(R.string.alarmset), Toast.LENGTH_SHORT).show();

            }
        });





    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notifications:
                Intent intent_notifications = new Intent(getApplicationContext(), NotificationSettingsActivity.class);
                startActivity(intent_notifications);
                return true;

        }
        return false;
    }

}
