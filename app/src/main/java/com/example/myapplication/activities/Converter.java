package com.example.myapplication.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.globals.ApiCall;
import com.example.myapplication.R;
import com.example.myapplication.globals.layout.Main;

import org.json.JSONException;
import org.json.JSONObject;


public class Converter extends Main {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent(this, R.layout.converter);;

        handleAPIData();
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
                // Handle API call error
            }
        }, Converter.this);
    }
}
