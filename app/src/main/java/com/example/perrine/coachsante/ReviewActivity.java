package com.example.perrine.coachsante;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.CoachSanteDbHelper;
import com.example.perrine.coachsante.database.Meal;

import java.util.ArrayList;

/**
 * Created by Perrine on 24/11/2017.
 */

public class ReviewActivity extends AppCompatActivity {


    ArrayList<Integer> idMealOfTheWeek;
    ListView displayMeals;
    ArrayList<Meal> mealAvailable;
    Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mealAvailable = CoachSanteContentProvider.getAllMealWeek();
        /*ArrayList<Integer> idFoodEatenDuringMeal = CoachSanteContentProvider.getIdOfEatenFoodDuringMeal(idMealOfTheWeek.get(0));
        for(int i = 0; i < idFoodEatenDuringMeal.size(); i++) {
            System.out.println("The ID ARE : " + idFoodEatenDuringMeal.get(i));*/
       /* ArrayList<Integer> idFoodEatenDuringMeal = CoachSanteContentProvider.getIdOfEatenFoodDuringMeal(2);
        for(int a : idMealOfTheWeek) {
            System.out.println("MEAL "  + a);
        }*/

        displayMeals = (ListView) findViewById(R.id.displayMealAvailable);
        final MealAdapter adapter = new MealAdapter(ReviewActivity.this,
                android.R.layout.simple_list_item_1, mealAvailable);
        displayMeals.setAdapter(adapter);

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
    /*
    public ArrayList<Integer> getAllMealWeek() {

        //Sorts by name
        String orderBy = CoachSanteDbHelper.getIdMealColumn() + " ASC WHERE ";
        //private final String MY_QUERY = "SELECT * FROM " + CoachSanteContentProvider.MEAL_URI + " a " +
                //" INNER JOIN " + CoachSanteContentProvider.MEAL_URI + " b ON a.id=b.other_id WHERE b.property_id=?"

        final String MY_QUERY = "SELECT * FROM" + CoachSanteContentProvider.MEAL_URI + "WHERE strftime('%Y-%m-%d',DATE) >= date('now','-6 days') AND strftime('%Y-%m-%d',DATE)<=date('now') order by DATE";

        Cursor cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                System.out.println(" ------------------------------------------------------- ");
                if(!cursor.isNull(2)) {
                    System.out.println("Nous avons add " + cursor.getInt(0) + " le " + cursor.getString(1) + " Number of calories " + cursor.getInt(2));
                    idMealOfTheWeek.add(cursor.getInt(0));
                }

            } while (cursor.moveToNext());

        }

        return idMealOfTheWeek;
    }
    */

}
