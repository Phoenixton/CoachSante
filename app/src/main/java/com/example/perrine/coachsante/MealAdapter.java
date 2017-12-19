package com.example.perrine.coachsante;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.perrine.coachsante.database.CoachSanteContentProvider;
import com.example.perrine.coachsante.database.Food;
import com.example.perrine.coachsante.database.FoodQuantityPair;
import com.example.perrine.coachsante.database.Meal;

import java.util.ArrayList;

/**
 * Created by Perrine on 15/12/2017.
 */

public class MealAdapter extends ArrayAdapter<Meal>{


    private ArrayList<Meal> meals;
    private Context context;

    public MealAdapter(Context context, int i, ArrayList<Meal> meals) {
        super(context, i, meals);
        this.meals = new ArrayList<Meal>();
        this.meals.addAll(meals);
        this.context = context;
    }

    private class ViewHolder {
        TextView mealDate;
        TextView totalCaloriesOfMeal;
        LinearLayout foodOfMealList;
        Button eraseMeal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;


        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.meal_container, null);

            holder = new ViewHolder();
            holder.mealDate = (TextView) convertView.findViewById(R.id.dateOfMeal);
            holder.totalCaloriesOfMeal = (TextView) convertView.findViewById(R.id.totalCaloriesOfMeal);
            holder.foodOfMealList = (LinearLayout) convertView.findViewById(R.id.foodOfMeal);
            holder.eraseMeal = (Button) convertView.findViewById(R.id.eraseMeal);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Meal meal = meals.get(position);
        final int mealId = meal.getId();

        holder.eraseMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog deleteThisMeal = askIfConfirmDelete(mealId);
                deleteThisMeal.show();

            }
        });

        holder.mealDate.setText("" + meal.getDate());
        holder.totalCaloriesOfMeal.setText(meal.getTotalCalories() +"");
        ArrayList<FoodQuantityPair> foodOfMealWithQuantity = CoachSanteContentProvider.getEatenFoodWithQuantityDuringMeal(meal.getId());
        holder.foodOfMealList.removeAllViews();

        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (FoodQuantityPair temp : foodOfMealWithQuantity) {

            View line = vi.inflate(R.layout.display_meal, null);
            TextView foodName = (TextView) line.findViewById(R.id.foodName);
            TextView foodQuantity = (TextView) line.findViewById(R.id.foodQuantity);
            foodName.setText(temp.getFood().getName());
            foodQuantity.setText(temp.getFoodQuantity() + "");

            holder.foodOfMealList.addView(line);
        }
        return convertView;

    }

    private AlertDialog askIfConfirmDelete(final int idMeal){
        AlertDialog deleteThisMeal =new AlertDialog.Builder(getContext())
                .setTitle("Delete this meal")
                .setMessage("Do you confirm ?")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        CoachSanteContentProvider.deleteMeal(idMeal);


                        if(context instanceof ReviewActivity){
                            ((ReviewActivity)context).finish();

                            Intent intent = new Intent(context, ReviewActivity.class);
                            ((ReviewActivity)context).startActivity(intent);
                        }
                        dialog.dismiss();

                    }

                })



                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return deleteThisMeal;
    }


}
