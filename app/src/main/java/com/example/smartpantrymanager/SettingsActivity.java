package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class SettingsActivity
        extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_settings
        );



        // NAVIGATION.
        findViewById(R.id.btnNavPantry)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    SettingsActivity.this,
                                    com.example.smartpantrymanager.MainActivity.class
                            );

                    startActivity(intent);
                });

        findViewById(R.id.btnNavRecipes)
                .setOnClickListener(v -> {

                    Intent intent =
                            new Intent(
                                    SettingsActivity.this,
                                    com.example.smartpantrymanager.SuggRecipeActivity.class
                            );

                    startActivity(intent);
                });

        findViewById(R.id.btnNavSettings)
                .setOnClickListener(v -> {
                });
    }
}