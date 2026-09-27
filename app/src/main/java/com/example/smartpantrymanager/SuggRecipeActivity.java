package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



// activity_sugg_recipe.xml
// SuggRecipeAdapter.java
// TheDatabase.java



public class SuggRecipeActivity
        extends AppCompatActivity {

    RecyclerView recyclerRecipes;
    TextView txtNoRecipes;

    ArrayList<Long> recipeIds;
    ArrayList<String> recipeNames;

    SuggRecipeAdapter recipeAdapter;
    TheDatabase theDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_sugg_recipe
        );

        recyclerRecipes =
                findViewById(
                        R.id.recyclerRecipes
                );

        txtNoRecipes =
                findViewById(
                        R.id.txtNoRecipes
                );

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        theDatabase =
                new TheDatabase(this);

        recipeIds =
                new ArrayList<>();

        recipeNames =
                new ArrayList<>();

        recipeAdapter =
                new SuggRecipeAdapter(
                        recipeIds,
                        recipeNames,

                        // VIEW Recipe.
                        id -> openRecipeDetail(id)
                );

        recyclerRecipes.setAdapter(
                recipeAdapter
        );

        loadSuggRecipes();
    }



    private void loadSuggRecipes() {

        recipeIds.clear();
        recipeNames.clear();

        Cursor cursor =
                theDatabase.retrieveRecipes();

        if (cursor.moveToFirst()) {

            do {

                long recipeId =
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow(
                                        TheDatabase.RECIPE_ID
                                )
                        );

                String recipeName =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        TheDatabase.RECIPE_NAME
                                )
                        );



                // STRICT MATCHING.
                if (
                        theDatabase.recipeCanCook(
                                recipeId
                        )
                ) {

                    recipeIds.add(
                            recipeId
                    );

                    recipeNames.add(
                            recipeName
                    );
                }

            } while (cursor.moveToNext());
        }

        cursor.close();



        recipeAdapter.notifyDataSetChanged();



        if (recipeNames.isEmpty()) {

            txtNoRecipes.setVisibility(
                    TextView.VISIBLE
            );

            recyclerRecipes.setVisibility(
                    RecyclerView.GONE
            );

        } else {

            txtNoRecipes.setVisibility(
                    TextView.GONE
            );

            recyclerRecipes.setVisibility(
                    RecyclerView.VISIBLE
            );
        }
    }



    private void openRecipeDetail(long id) {

        Intent intent =
                new Intent(
                        SuggRecipeActivity.this,
                        RecipeDetailActivity.class
                );

        intent.putExtra(
                "RECIPE_ID",
                id
        );

        startActivity(intent);
    }
}