package com.example.perrine.coachsante.database;

/**
 * Created by Perrine on 16/12/2017.
 */

public class FoodQuantityPair {

    private Food food;
    private double foodQuantity;

    public FoodQuantityPair(Food food, double foodQuantity) {
        this.food = food;
        this.foodQuantity = foodQuantity;
    }

    @Override
    public int hashCode() {

        return food.hashCode() ^ ((Double) foodQuantity).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FoodQuantityPair))
            return false;
        FoodQuantityPair temp = (FoodQuantityPair) obj;
        return this.food.equals(temp.getFood()) &&
                (this.foodQuantity == (temp.getFoodQuantity()));
    }


    public Food getFood(){
        return food;
    }

    public double getFoodQuantity() {
        return foodQuantity;
    }

}
