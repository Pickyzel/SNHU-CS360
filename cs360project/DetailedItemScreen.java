package com.example.cs360project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedItemScreen extends AppCompatActivity {

    EditText itemName;
    EditText onHand;
    EditText price;
    EditText location;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item);
        itemName = (EditText) findViewById(R.id.itemName);
        onHand = (EditText) findViewById(R.id.onHand);
        price = (EditText) findViewById(R.id.price);
        location = (EditText) findViewById(R.id.location);
        MyApplication mApp = ((MyApplication)getApplicationContext());
        itemName.setText(mApp.globalItem.getName());
        price.setText(mApp.globalItem.getPrice());
        location.setText(mApp.globalItem.getLocation());
        onHand.setText(mApp.globalItem.getOnHand().toString());
    }

    public void Delete(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        try{
            mApp.itemDatabase.DeleteItem(mApp.globalItem.getName());
        } catch(Exception e){
            //deleting an item that doesn't exist
        }
        Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
        startActivity(myIntent);
        finish();
    }

    public void Cancel(View view){
        Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
        startActivity(myIntent);
        finish();
    }

    public void Save(View view){
        MyApplication mApp = ((MyApplication)getApplicationContext());
        String newName = itemName.getText().toString();
        String newOnHand = onHand.getText().toString();
        String newPrice = price.getText().toString();
        String newLocation = location.getText().toString();
        Item checkItem;
        for (int i = 0; i < mApp.itemList.size(); i++){
            checkItem = mApp.itemList.get(i);
            if(checkItem.getName().equals(mApp.globalItem.getName())){
                mApp.itemDatabase.UpdateItem(mApp.globalItem.getName(), newName, newOnHand, newPrice, newLocation);
                mApp.globalItem = new Item(newName, newOnHand, newPrice, newLocation);
                Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
                startActivity(myIntent);
                finish();
                return;
            }
        }
        mApp.itemDatabase.AddItem(newName, newOnHand, newPrice, newLocation);
        mApp.globalItem = new Item(newName, newOnHand, newPrice, newLocation);
        mApp.itemList.add(mApp.globalItem);
        Intent myIntent = new Intent(view.getContext(), DatabaseScreen.class);
        startActivity(myIntent);
        finish();
    }

}
