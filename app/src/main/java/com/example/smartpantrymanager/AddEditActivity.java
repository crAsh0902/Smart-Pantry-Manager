package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;



// TheDatabase.java
// activity_add_edit.xml



public class AddEditActivity extends AppCompatActivity {

    EditText edtIngName;
    EditText edtIngQuantity;
    EditText edtIngUnit;
    EditText edtIngExpiryDate;

    Button btnSaveIng;
    TheDatabase theDatabase;
    long pantryId = -1;



    // CONNECTED TO activity_add_edit.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit
        );

        edtIngName = findViewById(
                R.id.edtIngName
        );

        edtIngQuantity = findViewById(
                R.id.edtIngQuantity
        );

        edtIngUnit = findViewById(
                R.id.edtIngUnit
        );

        edtIngExpiryDate = findViewById(
                R.id.edtIngExpiryDate
        );

        btnSaveIng = findViewById(
                R.id.btnSaveIng
        );



        // CONNECTED to TheDatabase.java
        theDatabase = new TheDatabase(this);



        // Existing PantryItem Edit CHECK.
        pantryId = getIntent().getLongExtra(
                "PANTRY_ID",
                -1
        );

        if (pantryId != -1) {
            loadIngForEditing();

        }



        // SAVE.
        btnSaveIng.setOnClickListener(v -> {
            saveIng();

        });
    }

    private void loadIngForEditing() {

        Cursor cursor =
                theDatabase.getPantryItemById(
                        pantryId
                );

        if (cursor.moveToFirst()) {

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

            String expiryDate =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    TheDatabase.PANTRY_EXPIRY_DATE
                            )
                    );

            edtIngName.setText(name);

            edtIngQuantity.setText(
                    String.valueOf(quantity)
            );

            edtIngUnit.setText(unit);

            if (expiryDate != null) {

                edtIngExpiryDate.setText(
                        expiryDate
                );
            }
        }

        cursor.close();

        btnSaveIng.setText("UPDATE");

        TextView title =
                findViewById(
                        R.id.txtIngTitle
                );

        title.setText("EDIT INGREDIENT");
    }

    private void saveIng() {

        String name =
                edtIngName
                        .getText()
                        .toString()
                        .trim();

        String quantityText =
                edtIngQuantity
                        .getText()
                        .toString()
                        .trim();

        String unit =
                edtIngUnit
                        .getText()
                        .toString()
                        .trim();

        String expiryDate =
                edtIngExpiryDate
                        .getText()
                        .toString()
                        .trim();



        // IngName CHECK.
        if (name.isEmpty()) {

            edtIngName.setError(
                    "Please enter an ingredient name."
            );

            edtIngName.requestFocus();

            return;
        }



        // IngQuantity CHECK.
        if (quantityText.isEmpty()) {

            edtIngQuantity.setError(
                    "Please enter ingredient quantity."
            );

            edtIngQuantity.requestFocus();

            return;
        }

        double quantity;

        try {

            quantity = Double.parseDouble(
                    quantityText
            );

        } catch (NumberFormatException e) {

            edtIngQuantity.setError(
                    "Please enter a valid number."
            );

            edtIngQuantity.requestFocus();

            return;
        }

        // > 0
        if (quantity <= 0) {

            edtIngQuantity.setError(
                    "Quantity must be greater than zero."
            );

            edtIngQuantity.requestFocus();

            return;
        }



        // IngUnit CHECK.
        if (unit.isEmpty()) {

            edtIngUnit.setError(
                    "Please enter ingredient unit."
            );

            edtIngUnit.requestFocus();

            return;
        }

        String finalExpiryDate =
                expiryDate.isEmpty()
                        ? null
                        : expiryDate;



        // TheDatabase CREATE.
        if (pantryId == -1) {

            long result =
                    theDatabase.insertPantryItem(
                            name,
                            quantity,
                            unit,
                            finalExpiryDate
                    );

            if (result != -1) {

                Toast.makeText(
                        this,
                        "Ingredient added!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to add ingredient...",
                        Toast.LENGTH_SHORT
                ).show();
            }

        }



        // TheDatabase UPDATE.
        else {

            int result =
                    theDatabase.updatePantryItem(
                            pantryId,
                            name,
                            quantity,
                            unit,
                            finalExpiryDate
                    );

            if (result > 0) {

                Toast.makeText(
                        this,
                        "Ingredient updated!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to update ingredient...",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}