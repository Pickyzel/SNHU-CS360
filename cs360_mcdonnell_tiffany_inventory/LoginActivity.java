package com.assignment.cs360_mcdonnell_tiffany_inventory;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView invalidCredentials;
    private EditText username;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        invalidCredentials = (TextView) findViewById(R.id.invalidLogin);
    }

    //Calls database method to validate credentials
    public void ValidateLogin(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        mApp.currentUserName = username.getText().toString();
        Integer checkUserReturn = mApp.userDatabase.checkUser(usernameText, passwordText);
        if (checkUserReturn.equals(1)){
            //Log in successful, notifications on, move to database screen
            mApp.notifications = true;
            Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
            startActivity(myIntent);
            finish();
        }
        else if (checkUserReturn.equals(2)){
            //Log in successful, notifications off, move to database screen
            mApp.notifications = false;
            Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
            startActivity(myIntent);
            finish();
        }
        else if (checkUserReturn.equals(3)){
            //Log in successful, no notification setting, move to notification screen
            Intent myIntent = new Intent(view.getContext(), Notifications.class);
            startActivity(myIntent);
            finish();
        }

        else{
            //Log in failed
            invalidCredentials.setText("Invalid Username or Password");
            password.getText().clear();
        }

    }

    //adds user using database method
    public void AddUser(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        mApp.userDatabase.addUser(usernameText, passwordText);
        invalidCredentials.setText("User Added!");
    }
}