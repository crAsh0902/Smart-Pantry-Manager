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





    // Recipe INSERTION.
    public long insertRecipe(
            String name,
            String instructions) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, name);
        values.put(RECIPE_INSTRUCTIONS, instructions);

        return db.insert(
                TABLE_RECIPE,
                null,
                values
        );
    }



    // RecipeIngredient INSERTION.
    public long insertRecipeIng(
            long recipeId,
            String name,
            double quantity,
            String unit) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(
                RECIPE_INGREDIENT_RECIPE_ID,
                recipeId
        );

        values.put(
                RECIPE_INGREDIENT_NAME,
                name
        );

        values.put(
                RECIPE_INGREDIENT_QUANTITY,
                quantity
        );

        values.put(
                RECIPE_INGREDIENT_UNIT,
                unit
        );

        return db.insert(
                TABLE_RECIPE_INGREDIENT,
                null,
                values
        );
    }



    // Recipe COUNT.
    public int getRecipeCount() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM " +
                                TABLE_RECIPE,
                        null
                );

        int count = 0;

        if (cursor.moveToFirst()) {

            count = cursor.getInt(0);
        }

        cursor.close();

        return count;
    }





    // Recipe DATABASE SEEDING.
    public void seedRecipes() {

        if (getRecipeCount() > 0) {

            return;
        }

        seedCheeseToast();
        seedScrambledEggs();
        seedChickenCurry();
        seedMuttonCurry();
        seedButterChickenPasta();
        seedPotatoBake();
        seedPerfectChips();
        seedTomatoJalapenoSalsita();
        seedRoastedChickenSkins();
        seedTomatoChips();
        seedMicrowavePotatoChips();
        seedTomatoSoup();
        seedMashedPotatoes();
        seedBakedPotatoes();
        seedChileConQueso();
    }










    // RECIPE 1: Cheese Toast.
    private void seedCheeseToast() {

        long recipeId =
                insertRecipe(
                        "Cheese Toast",
                        "1. Butter one side of each bread. " +
                                "2. Add shredded cheese on top of the plain side of 1 bread." +
                                "3. Sandwich cheese side with the plain side of the other bread." +
                                "4. Toast sandwich with a frying pan until golden."
                );

        insertRecipeIng(
                recipeId,
                "Bread",
                2,
                "slices"
        );

        insertRecipeIng(
                recipeId,
                "Cheese",
                60,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Butter",
                1,
                "tablespoon"
        );
    }



    // RECIPE 2: Scrambled Eggs.
    private void seedScrambledEggs() {

        long recipeId =
                insertRecipe(
                        "Scrambled Eggs",
                        "1. Crack eggs into a bowl, and mix." +
                                "2. Add salt and black pepper seasonings, a little milk, and mix." +
                                "3. Add butter into a low heat, frying pan." +
                                "4. Pour the bowl's contents into frying pan." +
                                "5. With a spatula, bring the liquid to the middle every few seconds." +
                                "6. Cook until completion."
                );

        insertRecipeIng(
                recipeId,
                "Egg",
                2,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                3,
                "shakes"
        );

        insertRecipeIng(
                recipeId,
                "Black Pepper",
                3,
                "shakes"
        );

        insertRecipeIng(
                recipeId,
                "Milk",
                60,
                "ml"
        );

        insertRecipeIng(
                recipeId,
                "Butter",
                1,
                "teaspoon"
        );
    }



    // Recipe 3: Chicken Curry.
    private void seedChickenCurry() {

        long recipeId =
                insertRecipe(
                        "Chicken Curry",
                        "1. On low heat, pour sunflower oil until it covers the bottom of the pot." +
                                "2. Add onion slices, cinnamon sticks, star anise, bay leaves, and ginger-garlic paste." +
                                "3. Cook until onions are easy to cut through with a pot spoon." +
                                "4. Add chaat masala, and mix." +
                                "5. Add all cut chicken pieces, and mix." +
                                "6. Add salt, and cover with pot with lid to cook for 15 minutes." +
                                "7. Throughout 15 minutes, periodically stir the contents in the pot." +
                                "8. Peel and cut potatoes, and throw them in the pot" +
                                "9. Add hot water until it almost covers the chicken and potatoes." +
                                "10. Cook until potatoes are soft."
                );

        insertRecipeIng(
                recipeId,
                "Sunflower Oil",
                2.3,
                "cup"
        );

        insertRecipeIng(
                recipeId,
                "Onion",
                1.2,
                "slice"
        );

        insertRecipeIng(
                recipeId,
                "Cinnamon Sticks",
                3,
                "sticks"
        );

        insertRecipeIng(
                recipeId,
                "Star Anise",
                3,
                "cloves"
        );

        insertRecipeIng(
                recipeId,
                "Bay Leaves",
                3,
                "leaves"
        );

        insertRecipeIng(
                recipeId,
                "Ginger-garlic Paste",
                2,
                "teaspoons"
        );

        insertRecipeIng(
                recipeId,
                "Chaat Masala",
                2,
                "pot spoons"
        );

        insertRecipeIng(
                recipeId,
                "Cut Chicken",
                8,
                "pieces"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                30,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Potatoes",
                2,
                "whole"
        );
    }



    // RECIPE 4: Mutton Curry.
    private void seedMuttonCurry() {

        long recipeId =
                insertRecipe(
                        "Mutton Curry",
                        "1. On low heat, pour sunflower oil until it covers the bottom of the pot." +
                                "2. Add onion slices, cinnamon sticks, star anise, bay leaves, and ginger-garlic paste." +
                                "3. Cook until onions are soft to cut through with a pot spoon." +
                                "4. Add chaat masala, sliced tomatoes, and mix." +
                                "5. Add all cut mutton pieces, and mix." +
                                "6. Add salt, and cover with the pit with lid to cook for 15 minutes." +
                                "7. Throughout 15 minutes, periodically stir the contents in the pot." +
                                "8. Peel and cut potatoes, and throw them in the pot" +
                                "9. Add hot water until it almost covers the chicken and potatoes." +
                                "10. Cook until potatoes are soft."
                );

        insertRecipeIng(
                recipeId,
                "Sunflower Oil",
                2.3,
                "cup"
        );

        insertRecipeIng(
                recipeId,
                "Onion",
                1.2,
                "slice"
        );

        insertRecipeIng(
                recipeId,
                "Cinnamon Sticks",
                3,
                "sticks"
        );

        insertRecipeIng(
                recipeId,
                "Star Anise",
                3,
                "cloves"
        );

        insertRecipeIng(
                recipeId,
                "Bay Leaves",
                3,
                "leaves"
        );

        insertRecipeIng(
                recipeId,
                "Ginger-garlic Paste",
                2,
                "teaspoons"
        );

        insertRecipeIng(
                recipeId,
                "Tomatoes",
                2,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Chaat Masala",
                2,
                "pot spoons"
        );

        insertRecipeIng(
                recipeId,
                "Cut Mutton",
                8,
                "pieces"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                30,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Potatoes",
                2,
                "whole"
        );
    }



    // RECIPE 5: Butter-Chicken Pasta.
    private void seedButterChickenPasta() {

        long recipeId =
                insertRecipe(
                        "Butter-Chicken Pasta",
                        "1. On medium heat, add pasta into a pot of boiling water." +
                                "2. Add salt and sunflower oil." +
                                "3. Cook pasta for 7 minutes." +
                                "4. In the meantime, slice tomatos and puree them." +
                                "5. Drain water, and set pasta aside." +
                                "6. With the same pot on low heat, pour sunflower oil until it covers the bottom." +
                                "7. Add onion, chilly, and ginger-garlic paste." +
                                "8. Cook until onions are soft to cut through with a pot spoon." +
                                "9. Add cut chicken, and chaat masala." +
                                "10. Cook for 5 minutes" +
                                "11. Add salt" +
                                "12. Cook for 5 minutes" +
                                "13. Add tomato puree." +
                                "14. Cook for 5 minutes" +
                                "15. Pour in fresh cream." +
                                "16. Cook for 2 minutes." +
                                "17. Turn off heat, and add in the cooked pasta."
                );

        insertRecipeIng(
                recipeId,
                "Pasta",
                1,
                "cup"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                30,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Tomato",
                2,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Sunflower Oil",
                2.3,
                "cups"
        );

        insertRecipeIng(
                recipeId,
                "Onion",
                1.2,
                "slices"
        );

        insertRecipeIng(
                recipeId,
                "Chilly",
                3,
                "slices"
        );

        insertRecipeIng(
                recipeId,
                "Ginger-garlic Paste",
                1,
                "teaspoon"
        );

        insertRecipeIng(
                recipeId,
                "Cut Chicken",
                250,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Chaat Masala",
                1,
                "pot spoon"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                30,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Cream",
                1,
                "cup"
        );
    }



    // RECIPE 6: Potato Bake.
    private void seedPotatoBake() {

        long recipeId =
                insertRecipe(
                        "Potato Bake",
                        "1. Preheat oven to 180 degrees Celsius. " +
                                "2. Slice thin potatoes, and layer them in a dish." +
                                "3. Going layer-by-layer, sprinkle salt and black pepper seasonings." +
                                "4. Pour fresh cream over potatoes, and add bay leaves." +
                                "5. Bake for 1 hour until potatoes are cooked."
                );

        insertRecipeIng(
                recipeId,
                "Potato",
                10,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                500,
                "ml"
        );

        insertRecipeIng(
                recipeId,
                "Black Pepper",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Fresh Cream",
                1,
                "litre"
        );

        insertRecipeIng(
                recipeId,
                "Bay Leaves",
                3,
                "leaves"
        );
    }



    // RECIPE 7: Potato Chips.
    private void seedPerfectChips() {

        long recipeId =
                insertRecipe(
                        "The Perfect Chips",
                        "1. Peel and cut potatoes into finger-sized chips." +
                                "2. On a high heat, medium pan, pour oil until 8cm deep." +
                                "3. Add potato chips in a metal sieve, and lower into pan." +
                                "4. Cook for 8 minutes." +
                                "5. Season with salt."
                );

        insertRecipeIng(
                recipeId,
                "Sunflower Oil",
                500,
                "ml"
        );

        insertRecipeIng(
                recipeId,
                "Potato",
                800,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );
    }



    // RECIPE 8: Roasted Tomato & Jalapeño Salsita.
    private void seedTomatoJalapenoSalsita() {

        long recipeId =
                insertRecipe(
                        "Roasted Tomato & Jalapeno Salsita",
                        "1. Preheat boiler." +
                                "2. Place tomatoes and chillies on baking sheet lined with aluminum foil." +
                                "3. Place under boiler and roast for 15 minutes while flipping in between." +
                                "4. Season with salt."
                );

        insertRecipeIng(
                recipeId,
                "Tomato",
                5,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Chilly",
                2,
                "slices"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );
    }



    // RECIPE 9: Roasted Chicken Skins.
    private void seedRoastedChickenSkins() {

        long recipeId =
                insertRecipe(
                        "Roasted Chicken Skins",
                        "1. Preheat oven to 450 degrees Celsius." +
                                "2. Line baking sheet with aluminum foil, and lightly oil it." +
                                "3. Pat dry the chicken skin, and lay it flat on foil." +
                                "4. Season with salt and black pepper." +
                                "5. Bake chicken skins for 15 minutes." +
                                "6. Lay chicken skins on paper towels to soak up grease."
                );

        insertRecipeIng(
                recipeId,
                "Sunflower Oil",
                1,
                "teaspoon"
        );

        insertRecipeIng(
                recipeId,
                "Chicken",
                8,
                "skins"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Black Pepper",
                1,
                "tsp"
        );
    }



    // RECIPE 10: Tomato Chips.
    private void seedTomatoChips() {

        long recipeId =
                insertRecipe(
                        "Tomato Chips",
                        "1. Slice tomato about 1 inch thick, and pat dry with paper towels." +
                                "2. Season with salt, and let it sit for 15 minutes." +
                                "3. Lay tomato slices flat on plate sprayed with cooking spray." +
                                "4. Mist tomato slices with cooking spray, and microwave for 5 minutes." +
                                "5. Take out for cooling."
                );

        insertRecipeIng(
                recipeId,
                "Tomato",
                2,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );
    }



    // RECIPE 11: Microwave Potato Chips.
    private void seedMicrowavePotatoChips() {

        long recipeId =
                insertRecipe(
                        "Microwave Potato Chips",
                        "1. Clean and slice potatoes paper thin." +
                                "2. Place potato slices on baking sheet, and season with salt." +
                                "3. Cover potato slices with another baking sheet." +
                                "4. Microwave for 8 minutes." +
                                "5. Take out to cool, repeat with other potato slices."
                );

        insertRecipeIng(
                recipeId,
                "Potato",
                4,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );
    }



    // Recipe 12: Tomato Soup.
    private void seedTomatoSoup() {

        long recipeId =
                insertRecipe(
                        "Tomato Soup",
                        "1. Blanch tomato before chopping into small pieces." +
                                "2. Add butter, chopped onions,and tomatoes into a pot filled with a little water." +
                                "3. Cook on medium heat till contents thicken." +
                                "4. Remove from heat, and blend content in a blender." +
                                "5. Add water for preferred consistency." +
                                "6. Season with salt and black pepper."
                );

        insertRecipeIng(
                recipeId,
                "Tomato",
                2,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Onion",
                2,
                "tbsp"
        );

        insertRecipeIng(
                recipeId,
                "Butter",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Black Pepper",
                1,
                "tsp"
        );
    }



    // RECIPE 13: Yukon Gold Mashed Potatoes.
    private void seedMashedPotatoes() {

        long recipeId =
                insertRecipe(
                        "Yukon Gold Mashed Potatoes",
                        "1. Peel and cut potatoes into 1 inch chunks" +
                                "2. Add potato chunks and salt into pot with cold water." +
                                "3. Boil, then simmer, and cook for 20 minutes." +
                                "4. Meanwhile, warm fresh cream and butter separately until it melts." +
                                "5. Drain potatoes, and dry over low heat in the same pot." +
                                "6. Mast potatoes." +
                                "7. Pour melted butter, then warm cream gently." +
                                "8. Season with salt and black pepper."
                );

        insertRecipeIng(
                recipeId,
                "Potato",
                900,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Butter",
                113,
                "g"
        );

        insertRecipeIng(
                recipeId,
                "Cream",
                120,
                "ml"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Black Pepper",
                1,
                "tsp"
        );
    }



    // Recipe 14: Crock Pot Baked Potatoes.
    private void seedBakedPotatoes() {

        long recipeId =
                insertRecipe(
                        "Crock Pot Baked Potatoes",
                        "1. Prick potatoes with fork." +
                                "2. Wrap each potato in aluminium foil with a slice of onion, butter, salt, and black pepper." +
                                "3. Layer potatoes at the bottom of crock pot." +
                                "4. Pour half-a-cup of water in crock pot." +
                                "5. Cook on high heat for 5/6 hours." +
                                "6. Rotate potatoes occasionally." +
                                "7. Turn crock pot to warm to serve better."
                );

        insertRecipeIng(
                recipeId,
                "Potato",
                5,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Onion",
                1,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Butter",
                5,
                "tbsp"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Black Pepper",
                1,
                "tsp"
        );
    }



    // RECIPE 15: Chile Con Queso.
    private void seedChileConQueso() {

        long recipeId =
                insertRecipe(
                        "Chile Con Queso",
                        "1. Melt butter in skillet" +
                                "2. Add onions and cook till soft." +
                                "3. Add tomatoes, chillies, and salt into onion mixture." +
                                "4. Reduce to low heat for 10 minutes for liquid to evaporate." +
                                "5. Add cheese and cook till melted."
                );

        insertRecipeIng(
                recipeId,
                "Butter",
                1,
                "tbsp"
        );

        insertRecipeIng(
                recipeId,
                "Onion",
                1.2,
                "cup"
        );

        insertRecipeIng(
                recipeId,
                "Tomato",
                2,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Chilly",
                4,
                "whole"
        );

        insertRecipeIng(
                recipeId,
                "Salt",
                1,
                "tsp"
        );

        insertRecipeIng(
                recipeId,
                "Cheese",
                170,
                "g"
        );
    }
}