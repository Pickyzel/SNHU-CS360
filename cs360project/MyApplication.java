package com.example.cs360project;

import android.app.Application;

import java.util.Vector;

public class MyApplication extends Application {

    //cotains global variables to be passed between views
    public Item globalItem;
    public final ItemDatabase itemDatabase = new ItemDatabase(this);

    public UserDatabase userDatabase = new UserDatabase(this);

    public Vector<Item> itemList = new Vector<Item>();

    public boolean notifications, firstRun = true;

    public String currentUserName;
}
