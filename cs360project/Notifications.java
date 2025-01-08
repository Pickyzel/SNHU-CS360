package com.example.cs360project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Notifications extends AppCompatActivity {
    Button no;
    Button yes;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_permission);
        no = (Button) findViewById(R.id.denyPermission);
        yes = (Button) findViewById(R.id.grantPermission);
    }

    public void DenyPermission(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        mApp.notifications = false;
        mApp.userDatabase.AddNotificationPreference(mApp.notifications, mApp.currentUserName);
        Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
        startActivity(myIntent);
        finish();
    }

    public void GrantPermission(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        mApp.notifications = true;
        mApp.userDatabase.AddNotificationPreference(mApp.notifications, mApp.currentUserName);
        Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
        startActivity(myIntent);
        finish();
    }
}
