package com.example.myapplication.globals.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;


public class Main extends AppCompatActivity {
    protected void loadContent( Context context, int activityLayout) {
        setContentView(R.layout.main);
        FrameLayout container = findViewById(R.id.main_container);
        View activityContent = LayoutInflater.from(context).inflate(
            activityLayout,
            container,
            false
        );
        container.addView(activityContent);
    }

    protected void fillTextView(String textContent, int field_id) {
        TextView field = (TextView) findViewById( field_id );
        field.setText(textContent);
    }
}
