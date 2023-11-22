package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickHike(View view) {
        Intent intent = new Intent(this, HikeActivity.class);
        startActivity(intent);
    }

    public void onClickDiscover(View view) {
        Intent intent = new Intent(this, DiscoverActivity.class);
        startActivity(intent);
    }
}