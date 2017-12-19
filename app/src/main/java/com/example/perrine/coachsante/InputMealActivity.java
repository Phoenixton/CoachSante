package com.example.perrine.coachsante;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.CoachSanteDbHelper;
import com.example.perrine.coachsante.database.Food;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Perrine on 05/12/2017.
 */

public class InputMealActivity extends AppCompatActivity {

    ArrayList<Food> foodAvailable;
    ListView displayFoodAvailable;
    Button validateMeal;
    boolean buttonIsOn;
    private ArrayList<FoodCustomAdapter.ViewHolder> views;
    ArrayList<Integer> checkedMeals;
    ArrayList<String> spinnerValues;

    Toolbar toolbar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_input_meal);

        buttonIsOn = false;
        checkedMeals = new ArrayList<Integer>();

        toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerValues = new ArrayList<>();
        spinnerValues.add("0.5");
        spinnerValues.add("1");
        spinnerValues.add("1.5");
        spinnerValues.add("2");
        spinnerValues.add("3");

        views = new ArrayList<FoodCustomAdapter.ViewHolder>();


        validateMeal = (Button)findViewById(R.id.validateMeal);
        validateMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues createdMeal = new ContentValues();

                int totalCaloriesOfMeal = 0;
                for(FoodCustomAdapter.ViewHolder temp : views) {
                    if(temp.foodCheckbox.isSelected()) {
                        double portion = getAccuratePortion(temp.selectPortions);
                        for(Food tempFood: foodAvailable)
                        {
                            if(tempFood.getName() == temp.foodCheckbox.getText().toString()) {
                                totalCaloriesOfMeal += ((double)tempFood.getCalories() * portion);
                            }
                        }
                    }
                }


                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date now = new Date();
                createdMeal.put(CoachSanteDbHelper.getDateTimeColumn(), dateFormat.format(now));
                createdMeal.put(CoachSanteDbHelper.getTotalCaloriesMealColumn(), totalCaloriesOfMeal);
                Uri uri = getContentResolver().insert(CoachSanteContentProvider.MEAL_URI, createdMeal);
                long idMeal = Long.valueOf(uri.getLastPathSegment());


                //EATEN FOOD
                ContentValues toInsert = new ContentValues();
                for(FoodCustomAdapter.ViewHolder temp: views) {
                    if(temp.foodCheckbox.isSelected()) {
                        for(Food tempFood: foodAvailable)
                        {

                            if(tempFood.getName() == temp.foodCheckbox.getText().toString()) {

                                toInsert.put(CoachSanteDbHelper.getIdEatenFoodColumn(), tempFood.getId());
                                toInsert.put(CoachSanteDbHelper.getIdMealConcernedColumn(), (Long)(idMeal));
                                toInsert.put(CoachSanteDbHelper.getQuantityEatenColumn(), getAccuratePortion(temp.selectPortions));
                                Uri uriEatenFood = getContentResolver().insert(CoachSanteContentProvider.EATEN_FOOD_URI, toInsert);
                            }
                        }
                    }

                }
                Intent intent = new Intent(v.getContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });
        validateMeal.setEnabled(false);

        foodAvailable = new ArrayList<Food>();
        getAllFood();

        displayFoodAvailable = (ListView) findViewById(R.id.displayFoodAvailable);
       if(savedInstanceState != null) {
           Parcelable listInstanceState =  savedInstanceState.getParcelable("listView");
           final FoodCustomAdapter adapter = new FoodCustomAdapter(InputMealActivity.this,
                   android.R.layout.simple_list_item_1, foodAvailable);
           displayFoodAvailable.setAdapter(adapter);
           displayFoodAvailable.onRestoreInstanceState(listInstanceState);
       } else {
           System.out.println("WHY AM I HERE");
           final FoodCustomAdapter adapter = new FoodCustomAdapter(InputMealActivity.this,
                   android.R.layout.simple_list_item_1, foodAvailable);
           displayFoodAvailable.setAdapter(adapter);
       }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }


    public double getAccuratePortion(Spinner spinner) {
        String value = spinner.getSelectedItem().toString();
        double f = 0;
        switch (value) {
            case "0.5":
                f = 0.5;
                break;
            case "1":
                f = 1;
                break;
            case "1.5":
                f= 1.5;
                break;
            case "2":
                f = 2;
                break;
            case "3":
                f=3;
                break;
            default:
                f=1;
                break;
        }
        return f;
    }

    public void getAllFood() {
        String orderBy = CoachSanteDbHelper.getFoodColumn() + " ASC";

        Cursor cursor = getContentResolver().query(CoachSanteContentProvider.FOOD_URI, null, null, null, orderBy);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Food temp = null;
            do {
                temp = new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                foodAvailable.add(temp);
            } while (cursor.moveToNext());
        }
    }


    private class FoodCustomAdapter extends ArrayAdapter<Food>{

        private ArrayList<Food> foodAvailable;

        public FoodCustomAdapter(Context context, int i, ArrayList<Food> food) {
            super(context, i, food);
            this.foodAvailable = new ArrayList<Food>();
            this.foodAvailable.addAll(food);
        }

        private class ViewHolder {
            TextView nameFood;
            CheckBox foodCheckbox;
            Spinner selectPortions;


            public String getFoodName(){
                return foodCheckbox.getText().toString();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.food_select, null);

                holder = new ViewHolder();
                holder.nameFood = (TextView) convertView.findViewById(R.id.foodName);
                holder.foodCheckbox = (CheckBox) convertView.findViewById(R.id.foodCheckbox);
                holder.selectPortions = (Spinner) convertView.findViewById(R.id.spinner);
                convertView.setTag(holder);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(InputMealActivity.this, android.R.layout.simple_spinner_item, spinnerValues);
                holder.selectPortions.setAdapter(adapter);
                holder.selectPortions.setSelection(1);

                final long pid = getItemId(position);

                if (checkedMeals.contains(pid)){
                    holder.foodCheckbox.setChecked(true);

                } else {
                    holder.foodCheckbox.setChecked(false);

                }

                holder.foodCheckbox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;

                        if (checkedMeals.contains(pid)){
                            checkedMeals.remove(pid);
                        } else {
                            checkedMeals.add((int)pid);
                        }

                        cb.setSelected(cb.isChecked());

                        boolean isSomethingUp = false;
                        for(ViewHolder temp: views) {
                            if(temp.foodCheckbox.isSelected()) {
                                isSomethingUp = true;
                            }
                        }

                        //sets the background to white if not
                        if(!isSomethingUp) {
                            validateMeal.setBackgroundColor(Color.WHITE);
                            buttonIsOn = false;
                            validateMeal.setEnabled(false);
                        }

                        if(cb.isSelected()) {
                            if(!buttonIsOn) {
                                buttonIsOn = true;
                            }
                            validateMeal.setBackgroundColor(Color.RED);
                            validateMeal.setEnabled(true);
                        }

                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Food food = foodAvailable.get(position);
            holder.nameFood.setText(" (" + food.getCalories() + ")");
            holder.foodCheckbox.setText(food.getName());
            holder.foodCheckbox.setChecked(false);
            holder.foodCheckbox.setTag(food);

            if(!belongInArrayList(holder.getFoodName(),views)) {
                views.add(holder);

            }

            return convertView;

        }


    }

    private boolean belongInArrayList(String foodName, ArrayList<FoodCustomAdapter.ViewHolder> e) {

        for(FoodCustomAdapter.ViewHolder temp : e) {

            if(temp.getFoodName().equals(foodName)) {
                return true;
            }

        }

        return false;

    }

    protected void onSaveInstanceState(Bundle outState){
        outState.putIntegerArrayList("checkedMeals", checkedMeals);
        outState.putParcelable("listView", displayFoodAvailable.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkedMeals = (ArrayList<Integer>)savedInstanceState.getIntegerArrayList("checkedMeals");

    }
}









