package com.example.perrine.coachsante.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by perri on 30/11/2017.
 */

public class CoachSanteDbHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "CoachSanteDb";

    // Table Names
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_MEAL = "meal";
    private static final String TABLE_USER = "user";
    private static final String TABLE_EATEN_FOOD = "eaten";


    //User attributes
    private static final String USER_ID = "idUser";
    private static final String USER_NAME = "username";
    private static final String USER_WEIGHT = "weight";
    private static final String USER_MIN_CALORIES = "minCal";
    private static final String USER_MAX_CALORIES = "maxCal";

    //Food attributes
    private static final String ID_FOOD = "idFood";
    private static final String FOOD = "food";
    private static final String ESTIMATED_CALORIES_FOR_A_PORTION = "estimatedCalories";

    //Eaten Food Attributes
    private static final String ID_EATEN = "idEaten";
    private static final String ID_EATEN_FOOD = "idEatenFood";
    private static final String ID_MEAL_CONCERNED = "idMealConcerned";
    private static final String QUANTITY_EATEN = "quantityEaten";

    //Meal attributes
    private static final String MEAL_NAME = "meal";
    private static final String MEAL_ID = "idMeal";
    private static final String MEAL_DATE = "dateMeal";
    private static final String TOTAL_CALORIES = "totalCalories";

    // Table User create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + USER_NAME + " TEXT,"
            + USER_WEIGHT + " INTEGER,"
            + USER_MIN_CALORIES + " INTEGER,"
            + USER_MAX_CALORIES + " INTEGER" + ")";

    private static final String DELETE_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;


    //Table Food create statement

    private static final String CREATE_TABLE_FOOD = "CREATE TABLE "
            + TABLE_FOOD + "("
            + ID_FOOD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + FOOD + " TEXT,"
            + ESTIMATED_CALORIES_FOR_A_PORTION + " INTEGER" + ")";

    private static final String DELETE_TABLE_FOOD = "DROP TABLE IF EXISTS " + TABLE_FOOD;

    //Table Meal create statement

    private static final String CREATE_TABLE_MEAL = "CREATE TABLE "
            + TABLE_MEAL + "("
            + MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + MEAL_DATE + " DATETIME,"
            + TOTAL_CALORIES + " REAL,"
            + MEAL_NAME + " TEXT" + ")";

    private static final String DELETE_TABLE_MEAL = "DROP TABLE IF EXISTS " + TABLE_MEAL;


    //Table Eaten Food create statement
    private static final String CREATE_TABLE_EATEN_FOOD = "CREATE TABLE "
            + TABLE_EATEN_FOOD  + " ( "
            + ID_EATEN + " INTEGER PRIMARY KEY NOT NULL, "
            + ID_EATEN_FOOD + " INTEGER,"
            + ID_MEAL_CONCERNED + " INTEGER,"
            + QUANTITY_EATEN + " REAL,"
            + " FOREIGN KEY ( "+ID_EATEN_FOOD+" ) REFERENCES "+TABLE_FOOD+" ( "+ID_FOOD+" ), "
            + " FOREIGN KEY ( "+ID_MEAL_CONCERNED+" ) REFERENCES "+TABLE_MEAL+" ( "+ID_MEAL_CONCERNED+" ) );";

    private static final String DELETE_TABLE_EATEN_FOOD = "DROP TABLE IF EXISTS " + TABLE_FOOD;




    public CoachSanteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_FOOD);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_MEAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_EATEN_FOOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " +oldVersion + " to " +newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EATEN_FOOD);
        onCreate(sqLiteDatabase);
    }


    //Common attributes
    private static final String ID = "id";

    public static String getTableFood() {
        return TABLE_FOOD;
    }

    public static String getTableMeal() {
        return TABLE_MEAL;
    }

    public static String getTableUser() {
        return TABLE_USER;
    }

    public static String getTableEatenFood() {
        return TABLE_EATEN_FOOD;
    }



    public static String getFoodColumn() {
        return FOOD;
    }

    public static String getEstimatedCaloriesForAPortion() {
        return ESTIMATED_CALORIES_FOR_A_PORTION;
    }

    public static String getIdMealColumn() {
        return MEAL_ID;
    }

    public static String getDateTimeColumn(){
        return MEAL_DATE;
    }

    public static String getIdEatenFoodColumn() {
        return ID_EATEN_FOOD;
    }

    public static String getIdMealConcernedColumn() {
        return ID_MEAL_CONCERNED;
    }

    public static String getTotalCaloriesMealColumn() {
        return TOTAL_CALORIES;
    }

    public static String getEstimatedCaloriesForAPortionColumn() {
        return ESTIMATED_CALORIES_FOR_A_PORTION;
    }

    public static String getQuantityEatenColumn() {
        return QUANTITY_EATEN;
    }

    public static String getUserIdColumn(){
        return USER_ID;
    }

    public static String getUserNameColumn() {
        return USER_NAME;
    }

    public static String getUserWeightColumn() {
        return USER_WEIGHT;
    }

    public static String getUserMinCalColumn() {
        return USER_MIN_CALORIES;
    }

    public static String getUserMaxCalColumn() {
        return USER_MAX_CALORIES;
    }

}
