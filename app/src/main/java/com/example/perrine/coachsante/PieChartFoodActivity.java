package com.example.perrine.coachsante;

import android.database.Cursor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.CoachSanteDbHelper;
import com.example.perrine.coachsante.database.Food;
import com.example.perrine.coachsante.database.FoodQuantityPair;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Created by Perrine on 18/12/2017.
 */

public class PieChartFoodActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<Food> availableFood;
    ArrayList<FoodQuantityPair> foodRepresentation;
    PieChart pc;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_piechart);

        toolbar = (Toolbar) findViewById(R.id.mainToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pc = (PieChart)findViewById(R.id.pieChart);

        availableFood = new ArrayList<>();
        getAllFood();
        foodRepresentation = new ArrayList<>();
        foodRepresentation = getRepresentationFood();

        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<Integer>();
        Random rand = new Random();
        for(FoodQuantityPair fqp : foodRepresentation) {
            if((int)fqp.getFoodQuantity() == 0) {

            } else {

                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);
                entries.add(new PieEntry((float)fqp.getFoodQuantity(), fqp.getFood().getName()));
                int c = Color.rgb(r, g, b);
                colors.add(c);
            }

        }

        PieDataSet set = new PieDataSet(entries, getResources().getString(R.string.foodrepresentation));
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextSize(20f);
        pc.setData(data);
        pc.getLegend().setEnabled(false);
        pc.getDescription().setEnabled(false);
        pc.valuesToHighlight();
        pc.invalidate();



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

    public ArrayList<FoodQuantityPair> getRepresentationFood() {

        ArrayList<FoodQuantityPair> fqp = new ArrayList<>();

        for(Food temp : availableFood){
            fqp.add(new FoodQuantityPair(temp, CoachSanteContentProvider.getRepresentationOfFood(temp.getId())));
        }

        return fqp;
    }


}
