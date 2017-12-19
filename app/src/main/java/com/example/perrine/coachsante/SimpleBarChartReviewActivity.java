package com.example.perrine.coachsante;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.Meal;
import com.example.perrine.coachsante.database.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Perrine on 17/12/2017.
 */

public class SimpleBarChartReviewActivity extends AppCompatActivity{

    Toolbar toolbar;
    BarChart bc;
    ArrayList<String> daysOfTheWeek;
    User currentUser;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_barchart);

        toolbar = (Toolbar) findViewById(R.id.mainToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bc = (BarChart)findViewById(R.id.simpleBC);

        currentUser = CoachSanteContentProvider.getCurrentUser();

        //Gets the days for XLabel
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        int currentDay = cal.get(Calendar.DAY_OF_WEEK);

        int count = currentDay + 1;
        daysOfTheWeek = new ArrayList<>();
        while((count%7) != currentDay) {
            daysOfTheWeek.add(getDayOfWeak(count%7));

            count+=1;
        }
        daysOfTheWeek.add(getDayOfWeak(currentDay));

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, - 6);
        Date start = c.getTime();


        ArrayList<BarEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<Integer>();

        int green = Color.rgb(0, 255, 0);
        int red = Color.rgb(255, 0, 0);
        int blue = Color.rgb(0, 0, 255);

        ArrayList<Meal> mealsOfTheDay;
        for(int day = 0; day < 7; day++) {

            mealsOfTheDay = CoachSanteContentProvider.getMealOfOneDay(start);
            int totalCalOfDay = 0;
            for(Meal m : mealsOfTheDay) {
                totalCalOfDay += m.getTotalCalories();
            }
            BarEntry temp = new BarEntry(day, totalCalOfDay);
            entries.add(temp);
            if(totalCalOfDay < currentUser.getMinCal()) {
                colors.add(blue);
            } else if(totalCalOfDay > currentUser.getMinCal() && totalCalOfDay < currentUser.getMaxCal()) {
                colors.add(green);
            } else {
                colors.add(red);
            }

            c.add(Calendar.DATE, 1);
            start = c.getTime();

        }

        XAxis xAxis = bc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return daysOfTheWeek.get((int)value);
            }
        });


        BarDataSet set = new BarDataSet(entries, "Calories this week");
        set.setColors(colors);
        BarData data = new BarData(set);
        data.setBarWidth(0.9f);
        bc.setData(data);
        bc.setFitBars(true);
        bc.invalidate();

    }

    public String getDayOfWeak(int i) {
        String s="";
        switch(i) {
            case 1:
                s = "Sun.";
                break;
            case 2:
                s = "Mon.";
                break;
            case 3:
                s = "Tues.";
                break;
            case 4:
                s = "Wed.";
                break;
            case 5:
                s = "Thur.";
                break;
            case 6:
                s = "Fri.";
                break;
            case 0:
                s = "Sat.";
                break;
            default:
                s = "Sunday";
                break;
        }

        return s;
    }


}
