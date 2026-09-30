package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;



// TheDatabase.java
// activity_recipe_detail.xmk



// Recipe DISPLAY.
public class RecipeDetailActivity extends AppCompatActivity {

    TextView txtRecipeName;
    TextView txtRecipeIngredients;
    TextView txtRecipeInstructions;
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





    ImageView imgRecipe;
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        imgRecipe =
                findViewById(
                        R.id.imgRecipe
                );
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        theDatabase = new TheDatabase(this);



        // RecipeID.
        recipeId = getIntent().getLongExtra(
                "RECIPE_ID",
                -1
        );

        if (recipeId == -1) {

            Toast.makeText(
                    this,
                    "Recipe couldn't be found.",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        loadRecipeImage(recipeId);
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        loadRecipe();
    }
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





    private void loadRecipeImage(long recipeId) {

        if (recipeId == 1) {

            imgRecipe.setImageResource(
                    R.drawable.cheese_toast
            );

        } else if (recipeId == 2) {

            imgRecipe.setImageResource(
                    R.drawable.scrambled_eggs
            );

        } else if (recipeId == 3) {

            imgRecipe.setImageResource(
                    R.drawable.chicken_curry
            );

        } else if (recipeId == 4) {

            imgRecipe.setImageResource(
                    R.drawable.mutton_curry
            );

        } else if (recipeId == 5) {

            imgRecipe.setImageResource(
                    R.drawable.butter_chicken_pasta
            );

        } else if (recipeId == 6) {

            imgRecipe.setImageResource(
                    R.drawable.potato_bake
            );

        } else if (recipeId == 7) {

            imgRecipe.setImageResource(
                    R.drawable.the_perfect_chips
            );

        } else if (recipeId == 8) {

            imgRecipe.setImageResource(
                    R.drawable.roasted_tomato_and_jalapeno_salsita
            );

        } else if (recipeId == 9) {

            imgRecipe.setImageResource(
                    R.drawable.roasted_chicken_skins
            );

        } else if (recipeId == 10) {

            imgRecipe.setImageResource(
                    R.drawable.tomato_chips
            );

        } else if (recipeId == 11) {

            imgRecipe.setImageResource(
                    R.drawable.potato_chips
            );

        } else if (recipeId == 12) {

            imgRecipe.setImageResource(
                    R.drawable.tomato_soup
            );

        } else if (recipeId == 13) {

            imgRecipe.setImageResource(
                    R.drawable.mashed_potato
            );

        } else if (recipeId == 14) {

            imgRecipe.setImageResource(
                    R.drawable.baked_potato
            );

        } else if (recipeId == 15) {

            imgRecipe.setImageResource(
                    R.drawable.chile_con_queso
            );
        }
    }
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\



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