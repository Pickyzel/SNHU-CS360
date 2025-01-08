package com.assignment.tiffanymcdonnell_assignmentweek3;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mSayHelloButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSayHelloButton = findViewById(R.id.buttonSayHello);
    }
}