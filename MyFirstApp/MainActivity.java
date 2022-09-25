package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button
     * Note the following characteristics of the method:
     * Public access
     * void return value
     * View as the only param (it is the object you clicked!)
     * */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);     // the class param is the activity to start
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        // putExtra() adds the value of EditText to the intent. An intent can carry data types as key-value pairs called extras
        intent.putExtra(EXTRA_MESSAGE, message);
        // startActivity() starts an instance of the other activity (in this case DisplayMessageActivity
        startActivity(intent);
    }
}
