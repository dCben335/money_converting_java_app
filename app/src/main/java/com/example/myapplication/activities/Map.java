package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.myapplication.R;
import com.example.myapplication.globals.layout.Main;

public class Map extends Main {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent(this, R.layout.map);
    }
}