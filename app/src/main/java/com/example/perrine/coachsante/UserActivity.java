package com.example.perrine.coachsante;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.CoachSanteDbHelper;
import com.example.perrine.coachsante.database.User;
import com.github.mikephil.charting.renderer.PieChartRenderer;

/**
 * Created by Perrine on 24/11/2017.
 */

public class UserActivity extends AppCompatActivity {

    private Toolbar toolbar;

    EditText userName;
    EditText userWeight;

    EditText minimumCaloriesEditText;
    EditText maximumCaloriesEditText;

    Button pieChartButton;
    Button barChartButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        minimumCaloriesEditText = (EditText)findViewById(R.id.minimumCaloriesEditText);
        maximumCaloriesEditText = (EditText)findViewById(R.id.maximumCaloriesEditText);
        userName = (EditText)findViewById(R.id.userName);
        userWeight = (EditText)findViewById(R.id.userWeight);

        minimumCaloriesEditText.setGravity(Gravity.CENTER_HORIZONTAL);
        maximumCaloriesEditText.setGravity(Gravity.CENTER_HORIZONTAL);
        userName.setGravity(Gravity.CENTER_HORIZONTAL);
        userWeight.setGravity(Gravity.CENTER_HORIZONTAL);

        pieChartButton = (Button) findViewById(R.id.pieChartButton);
        barChartButton = (Button) findViewById(R.id.barChartButton);

        if(!CoachSanteContentProvider.isUserAlreadyDefined()) {
            pieChartButton.setEnabled(false);
            barChartButton.setEnabled(false);
        } else {
            pieChartButton.setEnabled(true);
            barChartButton.setEnabled(true);
        }

        if(!CoachSanteContentProvider.isUserAlreadyDefined()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_input_infos), Toast.LENGTH_LONG).show();
            //TODO mettre dans string.xml
            userName.setHint(getResources().getString(R.string.hint_username));
            userWeight.setHint(getResources().getString(R.string.hint_weight));
            minimumCaloriesEditText.setHint(getResources().getString(R.string.hint_mincal));
            maximumCaloriesEditText.setHint(getResources().getString(R.string.hint_maxcal));
        } else {
            User user = CoachSanteContentProvider.getCurrentUser();
            userName.setText(user.getName());
            userWeight.setText(user.getWeight()+"");
            minimumCaloriesEditText.setText(user.getMinCal()+"");
            maximumCaloriesEditText.setText(user.getMaxCal()+"");
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

    public void confirmUser(View view) {

        if(isInputValid()){

            if(!CoachSanteContentProvider.isUserAlreadyDefined()) {
                addUserToDatabase();
            } else {
                updateUserToDatabase();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

    public void updateUserToDatabase(){

        CoachSanteContentProvider.updateUserToDatabase(userName.getText().toString(), Integer.parseInt(userWeight.getText().toString()), Integer.parseInt(minimumCaloriesEditText.getText().toString()),
                Integer.parseInt(maximumCaloriesEditText.getText().toString()));
    }

    public void addUserToDatabase() {

        ContentValues toInsert = new ContentValues();
        toInsert.put(CoachSanteDbHelper.getUserNameColumn(), userName.getText().toString());
        if(!userWeight.getText().toString().matches("")) {
            toInsert.put(CoachSanteDbHelper.getUserWeightColumn(), Integer.parseInt(userWeight.getText().toString()));
        } else {
            toInsert.put(CoachSanteDbHelper.getUserWeightColumn(), 0);
        }
        toInsert.put(CoachSanteDbHelper.getUserMinCalColumn(), Integer.parseInt(minimumCaloriesEditText.getText().toString()));
        toInsert.put(CoachSanteDbHelper.getUserMaxCalColumn(), Integer.parseInt(maximumCaloriesEditText.getText().toString()));
        Uri uri = getContentResolver().insert(CoachSanteContentProvider.USER_URI, toInsert);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.now_registered), Toast.LENGTH_LONG).show();

    }


    public boolean isInputValid() {

        if (userName.getText().toString().matches("")) {
            Toast.makeText(this, getResources().getString(R.string.need_username), Toast.LENGTH_SHORT).show();
            return false;
        } else if(minimumCaloriesEditText.getText().toString().matches("")) {
            Toast.makeText(this, getResources().getString(R.string.need_mincal), Toast.LENGTH_SHORT).show();
            return false;
        } else if(maximumCaloriesEditText.getText().toString().matches("")) {
            Toast.makeText(this, getResources().getString(R.string.need_maxcal), Toast.LENGTH_SHORT).show();
            return false;
        }

        String minCal = minimumCaloriesEditText.getText().toString();
        String maxCal = maximumCaloriesEditText.getText().toString();
        String weight = userWeight.getText().toString();

        try {
            int temp = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getResources().getString(R.string.error_weight), Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            int temp = Integer.parseInt(minCal);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getResources().getString(R.string.error_mincal), Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            int temp = Integer.parseInt(maxCal);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getResources().getString(R.string.error_maxcal), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    /*
    public void goToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    public void goToBarChart(View view) {

        Intent intent = new Intent(this, SimpleBarChartReviewActivity.class);
        startActivity(intent);
    }

    public void goToPieChart(View view) {
        Intent intent = new Intent(this, PieChartFoodActivity.class);
        startActivity(intent);
    }

}
