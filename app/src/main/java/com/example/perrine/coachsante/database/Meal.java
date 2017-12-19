package com.example.perrine.coachsante.database;

import java.sql.Date;

/**
 * Created by Perrine on 15/12/2017.
 */

public class Meal {

    private int id;
    private String date;
    private double totalCalories;


    public Meal(int id, String date, double calories) {
        this.id = id;
        this.date = date;
        this.totalCalories = calories;

    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getTotalCalories() {
        return totalCalories;
    }
}
