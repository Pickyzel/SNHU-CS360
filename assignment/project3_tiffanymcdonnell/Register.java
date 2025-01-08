package com.assignment.project3_tiffanymcdonnell;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText editUser;
    private EditText editPassword;
    private EditText PhoneNumber;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUser = findViewById(R.id.register);
        editPassword = findViewById(R.id.Password);
        PhoneNumber = findViewById(R.id.Phonenumber);
        Button createUser = findViewById(R.id.btnSubmit);
        db = new Database(this);

        createUser.setOnClickListener(v -> {
            String username = editUser.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String phone = PhoneNumber.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }

            if (db.checkUsernameExists(username)) {
                Toast.makeText(Register.this, "Username already exists", Toast.LENGTH_LONG).show();
                return;
            }

            if (db.checkPhoneExists(phone)) {
                Toast.makeText(Register.this, "Phone number already registered", Toast.LENGTH_LONG).show();
                return;
            }

            db.addUser(username, password, phone);
            Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });
    }
}