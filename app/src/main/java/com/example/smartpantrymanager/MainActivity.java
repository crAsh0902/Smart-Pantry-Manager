package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerPantry;
    ArrayList<String> pantryNames;
    ArrayList<String> pantryQuantities;
    PantryAdapter pantryAdapter;
    TheDatabase theDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(
                R.id.recyclerPantry
        );

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        theDatabase = new TheDatabase(this);

        pantryNames = new ArrayList<>();
        pantryQuantities = new ArrayList<>();

        pantryAdapter = new PantryAdapter(
                pantryNames,
                pantryQuantities
        );

        recyclerPantry.setAdapter(
                pantryAdapter
        );



        // ADD YOUR INGREDIENT.
        findViewById(R.id.btnAddIngredient)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            MainActivity.this,
                            AddEditActivity.class
                    );

                    startActivity(intent);
                });
    }



    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        pantryNames.clear();
        pantryQuantities.clear();

        Cursor cursor =
                theDatabase.retrievePantryItem();

        if (cursor.moveToFirst()) {

            do {

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

                pantryNames.add(name);

                pantryQuantities.add(
                        quantity + " " + unit
                );

            } while (cursor.moveToNext());
        }

        cursor.close();

        pantryAdapter.notifyDataSetChanged();
    }
}