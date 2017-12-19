package com.example.perrine.coachsante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.CoachSanteDbHelper;
import com.example.perrine.coachsante.database.Food;

import java.util.ArrayList;

/**
 * Created by Perrine on 05/12/2017.
 */

public class FoodDatabaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText foodName;
    SeekBar caloriesPerPortion;
    TextView displayCalories;

    ArrayList<Food> availableFood;

    ListView displayFoodAlreadyInDatabase;

    private ArrayList<FoodCustomAdapter.ViewHolder> views;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_database);

        toolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        availableFood = new ArrayList<Food>();
        getAllFood();

        foodName = (EditText) findViewById(R.id.foodName);
        caloriesPerPortion = (SeekBar) findViewById(R.id.caloriesPerPortion);
        caloriesPerPortion.setMax(1000);

        displayCalories = (TextView) findViewById(R.id.displayCalories);

        String calories = "Calories : " + Integer.toString(caloriesPerPortion.getProgress());
        displayCalories.setText(calories);

        caloriesPerPortion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                String calories = Integer.toString(seekBar.getProgress());
                displayCalories.setText("Calories : " + calories);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        displayFoodAlreadyInDatabase = (ListView)findViewById(R.id.displayFoodList);
        final FoodCustomAdapter adapter = new FoodCustomAdapter(FoodDatabaseActivity.this,
                android.R.layout.simple_list_item_1, availableFood);
        displayFoodAlreadyInDatabase.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }


    public void addFoodToDatabase(View view) {

        String foodToAdd = foodName.getText().toString();
        //TODO : Verifier qu'elle n'existe pas deja

        if(foodToAdd.equals("")) {

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_foodname), Toast.LENGTH_LONG).show();
        } else {
            if(CoachSanteContentProvider.checkIfFoodAlreadyExists(foodToAdd)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_duplicate), Toast.LENGTH_LONG).show();
            } else {
                ContentValues toInsert = new ContentValues();
                toInsert.put(CoachSanteDbHelper.getFoodColumn(), foodToAdd);
                toInsert.put(CoachSanteDbHelper.getEstimatedCaloriesForAPortion(), Integer.parseInt(Integer.toString(caloriesPerPortion.getProgress())));

                Uri uri = getContentResolver().insert(CoachSanteContentProvider.FOOD_URI, toInsert);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.we_added) + foodToAdd + " !", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
        }

    }

    public void updateFoodToDatabase(View view) {

        for(FoodCustomAdapter.ViewHolder temp : views) {
            CoachSanteContentProvider.updateFoodToDatabase(temp.foodCheckbox.getText().toString(), Integer.parseInt(Integer.toString(temp.selectCalories.getProgress())));

        }

    }

    public void deleteFoodFromDatabase(View view) {

        for(FoodCustomAdapter.ViewHolder temp: views) {
            if(temp.foodCheckbox.isChecked()) {
                CoachSanteContentProvider.deleteFood(temp.foodCheckbox.getText().toString());
            }
        }

        finish();
        startActivity(getIntent());

    }


    public void getAllFood() {
        String orderBy = CoachSanteDbHelper.getFoodColumn() + " ASC";

        Cursor cursor = getContentResolver().query(CoachSanteContentProvider.FOOD_URI, null, null, null, orderBy);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Food temp = null;
            do {
                temp = new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                availableFood.add(temp);
            } while (cursor.moveToNext());
        }
    }


    private class FoodCustomAdapter extends ArrayAdapter<Food> {

        private ArrayList<Food> foodAvailable;

        public FoodCustomAdapter(Context context, int i, ArrayList<Food> food) {
            super(context, i, food);
            this.foodAvailable = new ArrayList<Food>();
            this.foodAvailable.addAll(food);
            views = new ArrayList<FoodCustomAdapter.ViewHolder>();
        }

        private class ViewHolder {
            TextView nameFood;
            CheckBox foodCheckbox;
            SeekBar selectCalories;
        }


        public View getView(int position, View convertView, ViewGroup parent) {

            FoodCustomAdapter.ViewHolder holder = null;


            Food food = foodAvailable.get(position);
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.food_display, null);

                holder = new FoodCustomAdapter.ViewHolder();

                holder.nameFood = (TextView) convertView.findViewById(R.id.foodName);
                holder.foodCheckbox = (CheckBox) convertView.findViewById(R.id.foodCheckbox);
                holder.selectCalories = (SeekBar) convertView.findViewById(R.id.caloriesSeeker);

                holder.selectCalories.setMax(1000);

                holder.selectCalories.setProgress(food.getCalories());
                final ViewHolder finalHolder = holder;
                holder.selectCalories.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        String calories = Integer.toString(seekBar.getProgress());
                        finalHolder.nameFood.setText("(" + calories + ")");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                convertView.setTag(holder);


                final long pid = getItemId(position);

                holder.foodCheckbox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;

                        cb.setSelected(cb.isChecked());

                    }
                });
            } else {
                holder = (FoodCustomAdapter.ViewHolder) convertView.getTag();
            }

            holder.nameFood.setText(" (" + food.getCalories() + ")");
            holder.foodCheckbox.setText(food.getName());
            holder.foodCheckbox.setChecked(false);
            holder.foodCheckbox.setTag(food);

            views.add(holder);

            return convertView;

        }


    }

}