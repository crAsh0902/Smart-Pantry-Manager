package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.TextView;



// activity_main.xml
// TheDatabase.java
// PantryAdapter.java



public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerPantry;

    ArrayList<Long> pantryIds;
    ArrayList<String> pantryNames;
    ArrayList<String> pantryQuantities;

    PantryAdapter pantryAdapter;

    TheDatabase theDatabase;

    TextView txtNoPantryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(
                R.id.recyclerPantry
        );
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        txtNoPantryItems =
                findViewById(
                        R.id.txtNoPantryItems
                );
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );



        theDatabase = new TheDatabase(this);



// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        // THE 15 RECIPES.
        theDatabase.seedRecipes();
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\



        pantryIds = new ArrayList<>();
        pantryNames = new ArrayList<>();
        pantryQuantities = new ArrayList<>();

        pantryAdapter = new PantryAdapter(
                pantryIds,
                pantryNames,
                pantryQuantities,

                // EDIT.
                id -> openEditScreen(id),

                // DELETE.
                id -> confirmDelete(id)
        );

        recyclerPantry.setAdapter(
                pantryAdapter
        );

        // ADD.
        findViewById(R.id.btnAddIng)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            MainActivity.this,
                            AddEditActivity.class
                    );

                    startActivity(intent);
                });





        // SuggRecipes.
        findViewById(R.id.btnSuggRecipes)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    SuggRecipeActivity.class
                            );

                    startActivity(intent);
                });
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        // NAVIGATION.
        findViewById(R.id.btnNavPantry)
                .setOnClickListener(v -> {
                });



        findViewById(R.id.btnNavRecipes)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    SuggRecipeActivity.class
                            );

                    startActivity(intent);
                });



        findViewById(R.id.btnNavSettings)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    SettingsActivity.class
                            );

                    startActivity(intent);
                });
    }
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\



    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        pantryIds.clear();
        pantryNames.clear();
        pantryQuantities.clear();

        Cursor cursor =
                theDatabase.retrievePantryItem();

        if (cursor.moveToFirst()) {

            do {

                long id =
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow(
                                        TheDatabase.PANTRY_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        TheDatabase.PANTRY_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        TheDatabase.PANTRY_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        TheDatabase.PANTRY_UNIT
                                )
                        );

                pantryIds.add(id);

                pantryNames.add(name);

                pantryQuantities.add(
                        quantity + " " + unit
                );

            } while (cursor.moveToNext());
        }

        cursor.close();

        pantryAdapter.notifyDataSetChanged();
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\





        if (pantryNames.isEmpty()) {

            txtNoPantryItems.setVisibility(
                    TextView.VISIBLE
            );

            recyclerPantry.setVisibility(
                    RecyclerView.GONE
            );

        } else {

            txtNoPantryItems.setVisibility(
                    TextView.GONE
            );

            recyclerPantry.setVisibility(
                    RecyclerView.VISIBLE
            );
        }
    }



    private void openEditScreen(long id) {

        Intent intent = new Intent(
                MainActivity.this,
                AddEditActivity.class
        );

        intent.putExtra(
                "PANTRY_ID",
                id
        );

        startActivity(intent);
    }

    private void confirmDelete(long id) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage(
                        "Delete this ingredient?"
                )
                .setPositiveButton(
                        "DELETE",
                        (dialog, which) -> {

                            deleteIngredient(id);

                        }
                )
                .setNegativeButton(
                        "CANCEL",
                        null
                )
                .show();
    }

    private void deleteIngredient(long id) {

        int result =
                theDatabase.deletePantryItem(id);

        if (result > 0) {

            loadPantryItems();
        }
    }
}