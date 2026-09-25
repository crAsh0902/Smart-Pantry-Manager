package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;



public class AddEditActivity extends AppCompatActivity {

    EditText edtIngName;
    EditText edtIngQuantity;
    EditText edtIngUnit;
    EditText edtIngExpiryDate;

    Button btnSaveIng;
    TheDatabase theDatabase;

    long pantryId = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit
        );

        edtIngName = findViewById(
                R.id.edtIName
        );

        edtIngQuantity = findViewById(
                R.id.edtIQuantity
        );

        edtIngUnit = findViewById(
                R.id.edtIUnit
        );

        edtIngExpiryDate = findViewById(
                R.id.edtIExpiryDate
        );

        btnSaveIng = findViewById(
                R.id.btnSaveIng
        );

        theDatabase = new TheDatabase(this);



        pantryId = getIntent().getLongExtra(
                "PANTRY_ID",
                -1
        );

        if (pantryId != -1) {

            loadIngForEditing();

        }

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
                        R.id.txtAddIngTitle
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



        if (name.isEmpty()) {

            edtIngName.setError(
                    "Please enter an ingredient name"
            );

            edtIngName.requestFocus();

            return;
        }



        if (quantityText.isEmpty()) {

            edtIngQuantity.setError(
                    "Please enter a quantity"
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
                    "Please enter a valid number"
            );

            edtIngQuantity.requestFocus();

            return;
        }

        if (quantity <= 0) {

            edtIngQuantity.setError(
                    "Quantity must be greater than zero"
            );

            edtIngQuantity.requestFocus();

            return;
        }



        if (unit.isEmpty()) {

            edtIngUnit.setError(
                    "Please enter a unit"
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
                        "Ingredient added",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to add ingredient",
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
                        "Ingredient updated",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}