package com.example.smartpantrymanager;

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



        // ADDITIONAL TEST DATA.
        pantryNames = new ArrayList<>();

        pantryNames.add("Tomatoes");
        pantryNames.add("Eggs");
        pantryNames.add("Milk");

        pantryQuantities = new ArrayList<>();

        pantryQuantities.add("5 pieces");
        pantryQuantities.add("6 pieces");
        pantryQuantities.add("2 litres");



        pantryAdapter = new PantryAdapter(
                pantryNames,
                pantryQuantities
        );

        recyclerPantry.setAdapter(
                pantryAdapter
        );
    }
}