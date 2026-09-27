package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



// TheDatabase.java
// activity_recipe_detail.xmk



// Recipe DISPLAY.
public class RecipeDetailActivity extends AppCompatActivity {

    TextView txtRecipeName;
    TextView txtRecipeIngredients;
    TextView txtRecipeInstructions;

    TheDatabase theDatabase;
    long recipeId = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        txtRecipeName = findViewById(
                R.id.txtRecipeName
        );

        txtRecipeIngredients = findViewById(
                R.id.txtRecipeIng
        );

        txtRecipeInstructions = findViewById(
                R.id.txtRecipeInst
        );

        theDatabase = new TheDatabase(this);



        // RecipeID.
        recipeId = getIntent().getLongExtra(
                "RECIPE_ID",
                -1
        );

        if (recipeId == -1) {

            Toast.makeText(
                    this,
                    "Recipe could not be found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadRecipe();
    }



    // RecipeDetails LOADING.
    private void loadRecipe() {

        Cursor recipeCursor =
                theDatabase.getRecipeById(
                        recipeId
                );

        if (recipeCursor.moveToFirst()) {

            String name =
                    recipeCursor.getString(
                            recipeCursor.getColumnIndexOrThrow(
                                    TheDatabase.RECIPE_NAME
                            )
                    );

            String instructions =
                    recipeCursor.getString(
                            recipeCursor.getColumnIndexOrThrow(
                                    TheDatabase.RECIPE_INSTRUCTIONS
                            )
                    );

            txtRecipeName.setText(name);

            txtRecipeInstructions.setText(
                    instructions
            );
        }

        recipeCursor.close();



        Cursor ingredientCursor =
                theDatabase.getRecipeIng(
                        recipeId
                );

        StringBuilder ingredients =
                new StringBuilder();

        if (ingredientCursor.moveToFirst()) {

            do {

                String name =
                        ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow(
                                        TheDatabase.RECIPE_INGREDIENT_NAME
                                )
                        );

                double quantity =
                        ingredientCursor.getDouble(
                                ingredientCursor.getColumnIndexOrThrow(
                                        TheDatabase.RECIPE_INGREDIENT_QUANTITY
                                )
                        );

                String unit =
                        ingredientCursor.getString(
                                ingredientCursor.getColumnIndexOrThrow(
                                        TheDatabase.RECIPE_INGREDIENT_UNIT
                                )
                        );



                // Recipe Ingredients' LAYOUT.
                ingredients.append(
                        "▶ "
                );

                ingredients.append(name);

                ingredients.append(
                        "   =   "
                );

                ingredients.append(quantity);

                ingredients.append(
                        " "
                );

                ingredients.append(unit);

                ingredients.append(
                        "\n"
                );

            } while (ingredientCursor.moveToNext());
        }

        ingredientCursor.close();

        txtRecipeIngredients.setText(
                ingredients.toString()
        );
    }
}