package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;





public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TheDatabase theDatabase = new TheDatabase(this);

        

        // DOES PANTRY CONTAIN ITEM?
        Cursor existingItems = theDatabase.retrievePantryItem();

        if (!existingItems.moveToFirst()) {

            long result = theDatabase.insertPantryItem(
                    "Tomatoes",
                    5,
                    "pieces",
                    null
            );

            Log.d(
                    "PANTRY_TEST",
                    "Tomatoes inserted. ID: " + result
            );

        } else {

            Log.d(
                    "PANTRY_TEST",
                    "Pantry item already exists."
            );
        }

        existingItems.close();


        
        // RETRIEVE ITEMS.
        Cursor cursor = theDatabase.retrievePantryItem();

        if (cursor.moveToFirst()) {

            do {

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                TheDatabase.PANTRY_NAME
                        )
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                TheDatabase.PANTRY_QUANTITY
                        )
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                TheDatabase.PANTRY_UNIT
                        )
                );

                Log.d(
                        "PANTRY_TEST",
                        "Pantry item: " +
                                name +
                                " | " +
                                quantity +
                                " | " +
                                unit
                );

            } while (cursor.moveToNext());

        } else {

            Log.d(
                    "PANTRY_TEST",
                    "No pantry items found."
            );
        }

        cursor.close();
    }
}
