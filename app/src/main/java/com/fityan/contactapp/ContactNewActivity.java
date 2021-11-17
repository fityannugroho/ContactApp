package com.fityan.contactapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ContactNewActivity extends AppCompatActivity {
    /**
     * Helper to interact with database.
     */
    private final DBHelper dbHelper = new DBHelper(this);

    /* View elements */
    private Button btnBack, btnAdd;
    private TextInputEditText inputName, inputPhone, inputEmail, inputAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new);

        /* Initialize view elements */
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        inputAddress = findViewById(R.id.inputAddress);
        inputEmail = findViewById(R.id.inputEmail);
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);

        /* When Add Button is clicked, */
        btnAdd.setOnClickListener(view -> {
            /* Execute insert query */
            try {
                String name, phone, email, address;

                /* Retreive data from input. */
                name = getTextFromInput(inputName, true);
                phone = getTextFromInput(inputPhone, true);
                email = getTextFromInput(inputEmail, false);
                address = getTextFromInput(inputAddress, false);

                /* Execute query, then give a feedback. */
                if (dbHelper.insertContact(name, phone, email, address)) {
                    Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();

                    /* Finish the activity and back to previous activity automatically. */
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

        /* When Back Button is clicked, back to previous activity. */
        btnBack.setOnClickListener(view -> onBackPressed());
    }


    /**
     * Retreive data from input, and validate the requirement.
     * @param input The input element.
     * @param required Is value required?
     * @return The input value.
     * @throws NullPointerException If validation failed.
     */
    private String getTextFromInput(TextInputEditText input, boolean required) {
        String value = Objects.requireNonNull(input.getText()).toString();

        if (value.isEmpty() && required) {
            input.setError("This input is required");
            throw new NullPointerException("Field " + input.getHint() + " is required.");
        }

        return value;
    }
}