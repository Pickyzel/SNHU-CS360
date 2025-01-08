package com.example.cs360project;

import android.app.NotificationChannel;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.Vector;


public class DatabaseScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    MyRecyclerViewAdapter adapter;
    private static final String NOTIFICATION_ID_STRING = "0";


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_screen);
        MyApplication mApp = ((MyApplication)getApplicationContext());
        mApp.itemList = mApp.itemDatabase.populateList();
        RecyclerView recyclerView = findViewById(R.id.itemGrid);
        int columns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        adapter = new MyRecyclerViewAdapter(this, mApp.itemList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //checks for SMS permissions
        if (mApp.firstRun && mApp.notifications){
            mApp.firstRun = false;
            SendSMS();
        }
    }



    //Override method for the click listener for the recycler view
    public void onItemClick(View view, int position){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        mApp.globalItem = mApp.itemList.get(position);
        Intent myIntent = new Intent(view.getContext(), DetailedItemScreen.class);
        startActivity(myIntent);
    }

    public void AddItem(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        mApp.globalItem = new Item();
        Intent myIntent = new Intent(view.getContext(), DetailedItemScreen.class);
        startActivity(myIntent);
    }

    public void SendSMS(){
        SmsManager sm = SmsManager.getDefault();
        String number = "5555215554";
        String message = "testing";
        sm.sendTextMessage(number, null, message, null, null);
    }
}
