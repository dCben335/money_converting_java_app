package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class DiscoverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        handleAPIData();
    }

    public void handleAPIData() {

        ApiCall.fetchData("https://jsonplaceholder.typicode.com/todos/1", new ApiCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    TextView TextTag = findViewById( R.id.title );
                    int test = response.getInt("userId");
                    TextTag.setText(String.valueOf(test));

                }  catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle API call error
            }
        }, DiscoverActivity.this);
    }
}