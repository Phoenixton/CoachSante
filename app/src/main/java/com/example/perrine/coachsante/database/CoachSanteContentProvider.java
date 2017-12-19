package com.example.perrine.coachsante.database;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by perri on 30/11/2017.
 */

public class CoachSanteContentProvider extends ContentProvider {


    private static final int URI_FOOD = 100;
    private static final int URI_FOOD_ID = 101;
    private static final int URI_USER = 102;
    private static final int URI_USER_ID = 103;
    private static final int URI_MEAL = 104;
    private static final int URI_MEAL_ID = 105;
    private static final int URI_EATEN_FOOD = 106;
    private static final int URI_EATEN_FOOD_ID = 107;

    private static CoachSanteDbHelper databaseHelper;

    public static final String AUTHORITY_NAME = "com.example.perrine.coachsante.database";

    public static final Uri FOOD_URI =
            Uri.parse("content://"+ AUTHORITY_NAME + "/" + CoachSanteDbHelper.getTableFood());
    public static final Uri USER_URI =
            Uri.parse("content://"+ AUTHORITY_NAME + "/" + CoachSanteDbHelper.getTableUser());
    public static final Uri MEAL_URI =
            Uri.parse("content://"+ AUTHORITY_NAME + "/" + CoachSanteDbHelper.getTableMeal());
    public static final Uri EATEN_FOOD_URI =
            Uri.parse("content://"+ AUTHORITY_NAME + "/" + CoachSanteDbHelper.getTableEatenFood());


    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableFood(), URI_FOOD);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableFood()+"/#", URI_FOOD_ID);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableUser(), URI_USER);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableUser()+"/#", URI_USER_ID);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableMeal(), URI_MEAL);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableMeal()+"/#", URI_MEAL_ID);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableEatenFood(), URI_EATEN_FOOD);
        uriMatcher.addURI(AUTHORITY_NAME, CoachSanteDbHelper.getTableEatenFood()+"/#", URI_EATEN_FOOD_ID);
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        databaseHelper = new CoachSanteDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String orderBy) {

        Cursor cursor = null;
        final SQLiteDatabase coachSanteDatabase = databaseHelper.getReadableDatabase();

        switch(uriMatcher.match(uri)){
            case URI_FOOD:
                cursor = coachSanteDatabase.query(CoachSanteDbHelper.getTableFood(), strings, s,
                        strings1, null, null, orderBy);
                break;
            case URI_USER:
                cursor = coachSanteDatabase.query(CoachSanteDbHelper.getTableUser(), strings, s,
                        strings1, null, null, orderBy);
                break;
            case URI_MEAL:
                cursor = coachSanteDatabase.query(CoachSanteDbHelper.getTableMeal(), strings, s,
                        strings1, null, null, orderBy);
                break;
            case URI_EATEN_FOOD:
                cursor = coachSanteDatabase.query(CoachSanteDbHelper.getTableEatenFood(), strings, s,
                        strings1, null, null, orderBy);
                break;
            default:
                throw new IllegalArgumentException("Unexpected uri: " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        Uri toReturn = null;
        final SQLiteDatabase coachSanteDatabase = databaseHelper.getWritableDatabase();
        switch(uriMatcher.match(uri)) {
            case URI_FOOD:
                long _ID1 = coachSanteDatabase.insert(CoachSanteDbHelper.getTableFood(), null, contentValues);
                if(_ID1 > 0) {
                    toReturn = ContentUris.withAppendedId(FOOD_URI, _ID1);
                    getContext().getContentResolver().notifyChange(toReturn, null);
                }
                break;
            case URI_USER:
                long _ID2 = coachSanteDatabase.insert(CoachSanteDbHelper.getTableUser(), null, contentValues);
                if(_ID2 > 0) {
                    toReturn = ContentUris.withAppendedId(USER_URI, _ID2);
                    getContext().getContentResolver().notifyChange(toReturn, null);
                }
                break;
            case URI_MEAL:
                long _ID3 = coachSanteDatabase.insert(CoachSanteDbHelper.getTableMeal(), null, contentValues);
                if(_ID3 > 0) {
                    toReturn = ContentUris.withAppendedId(MEAL_URI, _ID3);
                    getContext().getContentResolver().notifyChange(toReturn, null);
                }
                break;
            case URI_EATEN_FOOD:
                long _ID4 = coachSanteDatabase.insert(CoachSanteDbHelper.getTableEatenFood(), null, contentValues);
                if(_ID4 > 0) {
                    toReturn = ContentUris.withAppendedId(EATEN_FOOD_URI, _ID4);
                    getContext().getContentResolver().notifyChange(toReturn, null);
                }
                break;
            default:
                throw new SQLException("failure to write this new row " + uri);
        }

        return toReturn;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;
        final SQLiteDatabase coachSanteDatabase = databaseHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case URI_FOOD:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableFood(), s, strings);
                break;
            case URI_USER:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableUser(), s, strings);
                break;
            case URI_MEAL:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableMeal(), s, strings);
                break;
            case URI_EATEN_FOOD:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableEatenFood(), s, strings);
                break;
            case URI_FOOD_ID:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableFood(), s, strings);
                break;
            case URI_USER_ID:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableUser(), s, strings);
                break;
            case URI_MEAL_ID:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableMeal(), s, strings);
                break;
            case URI_EATEN_FOOD_ID:
                count = coachSanteDatabase.delete(CoachSanteDbHelper.getTableEatenFood(), s, strings);
                break;
            default:
                throw new IllegalArgumentException("URI non supportée : " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int count = 0;
        final SQLiteDatabase coachSanteDatabase = databaseHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)) {
            case URI_FOOD:
                count = coachSanteDatabase.update(CoachSanteDbHelper.getTableFood(), contentValues, s, strings);
                break;
            case URI_USER:
                count = coachSanteDatabase.update(CoachSanteDbHelper.getTableUser(), contentValues, s, strings);
                break;
            case URI_MEAL:
                count = coachSanteDatabase.update(CoachSanteDbHelper.getTableMeal(), contentValues, s, strings);
                break;
            case URI_EATEN_FOOD:
                count = coachSanteDatabase.update(CoachSanteDbHelper.getTableEatenFood(), contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("We don't know this uri " + uri);

        }

        return count;
    }


    public static boolean isUserAlreadyDefined() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String Query = "Select * from " + CoachSanteDbHelper.getTableUser() + ";";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        return true;
    }

    public static User getCurrentUser() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String Query = "Select * from " + CoachSanteDbHelper.getTableUser() + ";";
        Cursor cursor = db.rawQuery(Query, null);

        User user = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                if(!cursor.isNull(0)) {
                    user = new User(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                }

            } while (cursor.moveToNext());

        }

        return user;
    }

    public static void updateUserToDatabase(String name, int newWeight, int newMinCal, int newMaxCal) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        User currentUser = getCurrentUser();
        System.out.println("id : " + currentUser.getId() + ", name: " + currentUser.getName() + ", weight : " + currentUser.getWeight() + ", min : " + currentUser.getMinCal() + ", max : " + currentUser.getMaxCal());
        ContentValues toUpdate = new ContentValues();
        toUpdate.put(CoachSanteDbHelper.getUserNameColumn(),name);
        toUpdate.put(CoachSanteDbHelper.getUserWeightColumn(),newWeight);
        toUpdate.put(CoachSanteDbHelper.getUserMinCalColumn(),newMinCal);
        toUpdate.put(CoachSanteDbHelper.getUserMaxCalColumn(),newMaxCal);

        db.update(CoachSanteDbHelper.getTableUser(), toUpdate, CoachSanteDbHelper.getUserIdColumn()+"="+currentUser.getId(), null);

        db.close();

    }


    public static boolean checkIfFoodAlreadyExists(String foodName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String Query = "Select * from " + CoachSanteDbHelper.getTableFood() + " where " + CoachSanteDbHelper.getFoodColumn() + " = \"" + foodName + "\";";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public static ArrayList<Integer> getIdOfEatenFoodDuringMeal (int idMeal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String Query = "Select * from " + CoachSanteDbHelper.getTableEatenFood()+ " where " + CoachSanteDbHelper.getIdMealConcernedColumn() + " = \"" + idMeal + "\";";
        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Integer> ids = new ArrayList<Integer>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                if(!cursor.isNull(1)) {

                    System.out.println("Nous avons pour le meal  " + cursor.getInt(2) + " le eaten food numero " + cursor.getInt(1) +
                            " avec le nombre de portions " + cursor.getInt(3));
                    ids.add(cursor.getInt(1));
                }

            } while (cursor.moveToNext());

        }

        return ids;
    }

    public static double getRepresentationOfFood (int idFood) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String Query = "Select * from " + CoachSanteDbHelper.getTableEatenFood()+ " where " + CoachSanteDbHelper.getIdEatenFoodColumn() + " = \"" + idFood + "\";";
        Cursor cursor = db.rawQuery(Query, null);
        double count = 0;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                if(!cursor.isNull(1)) {
                    count += cursor.getDouble(3);
                }

            } while (cursor.moveToNext());

        } else {
            return 0;
        }
        return count;
    }


    public static void deleteMeal(int idMeal){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            db.delete(CoachSanteDbHelper.getTableMeal(), CoachSanteDbHelper.getIdMealColumn() + "=" + idMeal, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFood(String foodName) {

        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            db.delete(CoachSanteDbHelper.getTableFood(), CoachSanteDbHelper.getFoodColumn() + "='" + foodName +"'", null);
            recalculateMealsTotalCalories();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateFoodToDatabase(String foodName, int newCalories) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues toUpdate = new ContentValues();
        toUpdate.put(CoachSanteDbHelper.getEstimatedCaloriesForAPortionColumn(),newCalories);
        // Uri uri = getContentResolver().update(CoachSanteContentProvider.FOOD_URI, toUpdate);
        db.update(CoachSanteDbHelper.getTableFood(), toUpdate, CoachSanteDbHelper.getFoodColumn()+"='"+foodName+"'", null);

        recalculateMealsTotalCalories();

    }

    public static void recalculateMealsTotalCalories() {
        ArrayList<Integer> idMeals = new ArrayList<Integer>();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        final String Query = "SELECT * FROM " + CoachSanteDbHelper.getTableMeal() + " ORDER BY " + CoachSanteDbHelper.getIdMealColumn() + " DESC";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                if (!cursor.isNull(2)) {
                    idMeals.add(cursor.getInt(0));
                }
            } while (cursor.moveToNext());
        }

        for(int temp : idMeals) {
            int newTotal = 0;
            ArrayList<FoodQuantityPair> foodEaten = getEatenFoodWithQuantityDuringMeal(temp);
            if(foodEaten.isEmpty()) {
                deleteMeal(temp);
            } else {
                for(FoodQuantityPair fqp : foodEaten) {
                    newTotal += fqp.getFood().getCalories() * fqp.getFoodQuantity();
                }
                ContentValues toUpdate = new ContentValues();
                toUpdate.put(CoachSanteDbHelper.getTotalCaloriesMealColumn(),newTotal);
                // Uri uri = getContentResolver().update(CoachSanteContentProvider.FOOD_URI, toUpdate);
                db.update(CoachSanteDbHelper.getTableMeal(), toUpdate, CoachSanteDbHelper.getIdMealColumn()+"="+temp, null);

            }

        }

    }

    public static ArrayList<Food> getEatenFoodDuringMeal (int idMeal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String Query = "SELECT * FROM " + CoachSanteDbHelper.getTableFood() + " a INNER JOIN " + CoachSanteDbHelper.getTableEatenFood() + " b ON a.idFood=b.idEatenFood WHERE b.idMealConcerned=" +idMeal;

        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<Food> foods = new ArrayList<Food>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                if(!cursor.isNull(0)) {
                    Food temp = new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                    foods.add(temp);
                }

            } while (cursor.moveToNext());

        }
        db.close();
        return foods;
    }



    public static ArrayList<FoodQuantityPair> getEatenFoodWithQuantityDuringMeal (int idMeal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        //String Query = "Select * from " + CoachSanteDbHelper.getTableEatenFood()+ " where " + CoachSanteDbHelper.getIdMealConcernedColumn() + " = \"" + idMeal + "\";";
        String Query = "SELECT * FROM " + CoachSanteDbHelper.getTableFood() + " a INNER JOIN " + CoachSanteDbHelper.getTableEatenFood() + " b ON a.idFood=b.idEatenFood WHERE b.idMealConcerned=" +idMeal;

        Cursor cursor = db.rawQuery(Query, null);
        ArrayList<FoodQuantityPair> foods = new ArrayList<FoodQuantityPair>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                if(!cursor.isNull(0)) {
                    FoodQuantityPair temp = new FoodQuantityPair(new Food(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)), cursor.getDouble(6));
                    foods.add(temp);
                }

            } while (cursor.moveToNext());

        }
        return foods;
    }
    /*
    public static ArrayList<Integer> getAllMealWeekID() {
        ArrayList<Integer> idMealOfTheWeek = new ArrayList<Integer>();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        final String Query = "SELECT * FROM " + CoachSanteDbHelper.getTableMeal() + " ORDER BY " + CoachSanteDbHelper.getIdMealColumn() + " DESC";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {
                if (!cursor.isNull(2)) {
                    System.out.println("Nous avons add " + cursor.getInt(0) + " le " + cursor.getString(1) + " Number of calories " + cursor.getInt(2));
                    idMealOfTheWeek.add(cursor.getInt(0));
                }

            } while (cursor.moveToNext());
        }
        return idMealOfTheWeek;
    }
    */
    public static ArrayList<Meal> getAllMealWeek() {

        ArrayList<Meal> mealsOfTheWeek = new ArrayList<Meal>();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = getEndWeek();
        Date endDate = getStartWeek(now);

        final String Query = "SELECT * FROM " + CoachSanteDbHelper.getTableMeal() + " WHERE " + CoachSanteDbHelper.getDateTimeColumn() + " BETWEEN \'" + dateFormat.format(endDate) + "\' AND \'" + dateFormat.format(now)
               + "\' ORDER BY " + CoachSanteDbHelper.getDateTimeColumn() + " DESC";
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Meal temp = null;
            do {
                temp = new Meal(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
                mealsOfTheWeek.add(temp);
            } while (cursor.moveToNext());
        }
        return mealsOfTheWeek;
    }

    public static ArrayList<Meal> getMealOfOneDay(Date date) {

        ArrayList<Meal> mealsOfTheDay = new ArrayList<Meal>();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("NEW NOW "+ dateFormat.format(date));

        final String Query = "SELECT * FROM " + CoachSanteDbHelper.getTableMeal() + " WHERE " + CoachSanteDbHelper.getDateTimeColumn() + " BETWEEN \'"
                + dateFormat.format(date) + " 00:00:01\' AND \'" + dateFormat.format(date)
                + " 23:59:59\' ORDER BY " + CoachSanteDbHelper.getIdMealColumn() + " DESC";
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Meal temp = null;
            do {
                temp = new Meal(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
                mealsOfTheDay.add(temp);
            } while (cursor.moveToNext());
        }


        return mealsOfTheDay;


    }

    public static Date getEndWeek() {

        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        return now;
    }

    public static Date getStartWeek(Date now) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, -7);
        Date end = cal.getTime();
        return end;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
