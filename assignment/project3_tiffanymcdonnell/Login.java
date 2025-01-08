package com.assignment.project3_tiffanymcdonnell;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private Database db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.textEditUser);
        Password = findViewById(R.id.textEditpassword);

        // Initialize the buttons
        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnCreateAccount);
        db = new Database(this);

        // Set OnClickListener for login button
        loginButton.setOnClickListener(v -> {

            String username = Username.getText().toString().trim();
            String password = Password.getText().toString().trim();
            // Intent to start Login Activity
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter username and password", Toast.LENGTH_LONG).show();
                return;
            }

            if (db.validateUser(username, password)) {
                Intent intent = new Intent(Login.this, Inventory.class);
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            }


        });

        // Set OnClickListener for register button
        registerButton.setOnClickListener(v -> {
            // Intent to start Register Activity
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }


}