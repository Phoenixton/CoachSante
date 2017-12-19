package com.example.perrine.coachsante.database;

/**
 * Created by Perrine on 16/12/2017.
 */

public class User {

    private int id;
    private String name;
    private int weight;
    private int minCal;
    private int maxCal;

    public User(int id, String name, int weight, int minCal, int maxCal) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.minCal = minCal;
        this.maxCal = maxCal;
    }

    public User(int id, String name, int minCal, int maxCal) {
        this.id = id;
        this.name = name;
        this.weight = 0;
        this.minCal = minCal;
        this.maxCal = maxCal;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getMinCal() {
        return minCal;
    }

    public int getMaxCal() {
        return maxCal;
    }
}
