package com.example.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // declare the views
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int position;
    Button addCityButton;
    Button deleteCityButton;
    EditText editName, addName;
    Button confirmButton;
    Editable input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign views
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city);
        deleteCityButton = findViewById(R.id.delete_city);
        editName = findViewById(R.id.edit_name);
        addName = findViewById(R.id.add_name);
        confirmButton = findViewById(R.id.confirm_button);

        // set initial names
        String[] cities = {"Edmonton", "Vancouver", "Moscow"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editName.setText(cityList.getItemAtPosition(i).toString());
                addName.setVisibility(View.GONE);
                editName.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);

                position = i;

                deleteCityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // delete a city in the data and make edit text and confirm button gone
                        dataList.remove(i);
                        editName.setVisibility(View.GONE);
                        confirmButton.setVisibility(View.GONE);
                        cityList.invalidateViews();
                    }
                });
            }
        });

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make edit text and confirm button appear
                editName.setVisibility(View.GONE);
                addName.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make the edit texts and confirm button gone
                if(addName.getVisibility() == View.VISIBLE) {
                    input = addName.getText();
                    dataList.add(input.toString());
                    addName.setVisibility(View.GONE);
                }
                else {
                    input = editName.getText();
                    dataList.set(position, input.toString());
                    editName.setVisibility(View.GONE);
                }

                confirmButton.setVisibility(View.GONE);
                cityList.invalidateViews();
            }
        });
    }
}