package com.example.perrine.coachsante;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.CoachSanteDbHelper;
import com.example.perrine.coachsante.database.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        User user = CoachSanteContentProvider.getCurrentUser();

        userName = (TextView)findViewById(R.id.userName);
        userName.setText(getResources().getString(R.string.hello) + " " + user.getName() + " !");
        userName.setGravity(Gravity.CENTER_HORIZONTAL);


        if(!CoachSanteContentProvider.isUserAlreadyDefined()) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        }

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


    public void goToUserPage(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void goToReview(View view) {
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    public void goToInputMeal(View view) {
        Intent intent = new Intent(this, InputMealActivity.class);
        startActivity(intent);
    }

    public void goToDatabase(View view) {
        Intent intent = new Intent(this, FoodDatabaseActivity.class);
        startActivity(intent);
    }


}
