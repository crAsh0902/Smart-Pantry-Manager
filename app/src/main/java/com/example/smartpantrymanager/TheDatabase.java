package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class TheDatabase extends SQLiteOpenHelper {

    // SmartPantry Database INFO.
    private static final String DATABASE_NAME = "ThePantry.db";
    private static final int DATABASE_VERSION = 1;



    // Pantry TABLE.
    public static final String TABLE_PANTRY = "PantryItem";
    public static final String PANTRY_ID = "PantryID";
    public static final String PANTRY_NAME = "name";
    public static final String PANTRY_QUANTITY = "quantity";
    public static final String PANTRY_UNIT = "unit";
    public static final String PANTRY_EXPIRY_DATE = "exDate";



    // Recipe TABLE.
    public static final String TABLE_RECIPE = "Recipe";
    public static final String RECIPE_ID = "ID";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INSTRUCTIONS = "instructions";



    // RecipeIngredient TABLE.
    public static final String TABLE_RECIPE_INGREDIENT = "recipeIng";
    public static final String RECIPE_INGREDIENT_ID = "ID";
    public static final String RECIPE_INGREDIENT_RECIPE_ID = "recipeID";
    public static final String RECIPE_INGREDIENT_NAME = "ingName";
    public static final String RECIPE_INGREDIENT_QUANTITY = "reqQuantity";
    public static final String RECIPE_INGREDIENT_UNIT = "unit";





    // CONST.
    public TheDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // Database Table CREATION.
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Pantry TABLE.
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PANTRY_NAME + " TEXT NOT NULL, " +
                PANTRY_QUANTITY + " REAL NOT NULL, " +
                PANTRY_UNIT + " TEXT NOT NULL, " +
                PANTRY_EXPIRY_DATE + " TEXT" +
                ")";

        db.execSQL(createPantryTable);



        // Recipe TABLE.
        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPE + " (" +
                RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " TEXT NOT NULL, " +
                RECIPE_INSTRUCTIONS + " TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipeTable);



        // Recipe Ingredient TABLE.
        String createRecipeIngredientTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENT + " (" +
                RECIPE_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                RECIPE_INGREDIENT_NAME + " TEXT NOT NULL, " +
                RECIPE_INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                RECIPE_INGREDIENT_UNIT + " TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipeIngredientTable);
    }





    // Database UPGRADE.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);

        onCreate(db);
    }



    // PantryItem INSERTION.
    public long insertPantryItem(
            String name,
            double quantity,
            String unit,
            String exDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PANTRY_NAME, name);
        values.put(PANTRY_QUANTITY, quantity);
        values.put(PANTRY_UNIT, unit);
        values.put(PANTRY_EXPIRY_DATE, exDate);

        long result = db.insert(TABLE_PANTRY, null, values);

        db.close();

        return result;
    }



    // PantryItem READ.
    public Cursor retrievePantryItem() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                PANTRY_ID + " ASC"
        );
    }





    // PantryItem UPDATE
    public int updatePantryItem(
            long id,
            String name,
            double quantity,
            String unit,
            String exDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PANTRY_NAME, name);
        values.put(PANTRY_QUANTITY, quantity);
        values.put(PANTRY_UNIT, unit);
        values.put(PANTRY_EXPIRY_DATE, exDate);

        int result = db.update(
                TABLE_PANTRY,
                values,
                PANTRY_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result;
    }



    // PantryItem DELETE.
    public int deletePantryItem(long id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_PANTRY,
                PANTRY_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result;
    }



    // PantryItem ID RETRIEVAL.
    public Cursor getPantryItemById(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_PANTRY,
                null,
                PANTRY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
    }
}