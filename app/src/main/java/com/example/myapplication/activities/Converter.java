package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.globals.ApiCall;
import com.example.myapplication.R;
import com.example.myapplication.globals.layout.Main;

import org.json.JSONException;
import org.json.JSONObject;


public class Converter extends Main {
    private Spinner firstSpinner;
    private Spinner secondSpinner;
    private String[] selectOptions = {"1", "2", "3", "4"};
    private int currentOptionOne = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent(this, R.layout.converter);


        firstSpinner = findViewById(R.id.first_options);
        secondSpinner = findViewById(R.id.second_options);
        setSpinner(firstSpinner, selectOptions);
        setSpinner(firstSpinner, selectOptions);

        Button submitBtn = findViewById(R.id.submit_btn);
        retrieveSpinnerValue(firstSpinner);
    }

    public void handleConvert() {

    }

    protected void setSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
     this,
            android.R.layout.simple_spinner_item,
            options
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    public void retrieveSpinnerValue(Spinner spinner) {
        String selectedValue = spinner.getSelectedItem().toString();
    }

    public void handleAPIData() {
        ApiCall.makeApiCall("https://jsonplaceholder.typicode.com/todos/1", new ApiCall.ApiCallback() {
            public void onSuccess(JSONObject response) {
                try {
                    TextView TextTag = findViewById( R.id.title );
                    int test = response.getInt("userId");
                    TextTag.setText(String.valueOf(test));

                }  catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void onError(String errorMessage) {
                Toast.makeText(Converter.this, "Error on Fetching, Retry Later", Toast.LENGTH_SHORT).show();
            }
        }, Converter.this);
    }
}
