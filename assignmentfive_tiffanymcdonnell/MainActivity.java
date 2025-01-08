package com.assignment.assignmentfive_tiffanymcdonnell;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    EditText usersName;
    TextView textGreeting;
    Button sayHelloButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the name from the EditText element
        usersName = findViewById(R.id.nameText);
        usersName.addTextChangedListener(this);

        //clear the default text when the edittext takes focus
        usersName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    usersName.getText().clear();
                }
                else{
                    usersName.setText(R.string.please_enter_your_name);
                }
            }
        });

        textGreeting = findViewById(R.id.textGreeting);
        sayHelloButton = findViewById(R.id.buttonSayHello);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        sayHelloButton.setEnabled(charSequence.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable s){}

    public void SayHello(View view){

        //Validation
        if(usersName.getText().toString().matches("") ||
                usersName.getText().toString().matches(" ")){
            textGreeting.setText(R.string.name);
        }
        else{
            textGreeting.setText("Hello, " + usersName.getText());
        }
    }
}