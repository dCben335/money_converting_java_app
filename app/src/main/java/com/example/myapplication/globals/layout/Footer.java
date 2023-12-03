package com.example.myapplication.globals.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.myapplication.R;
import com.example.myapplication.activities.Converter;
import com.example.myapplication.activities.Home;
import com.example.myapplication.activities.Map;

public class Footer extends LinearLayout {
    public Footer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(
                R.layout.footer,
                this,
                true
        );
        setButtonClickNavigation(R.id.btnMap, Map.class);
        setButtonClickNavigation(R.id.btnHome, Home.class);
        setButtonClickNavigation(R.id.btnConverter, Converter.class);
    }

    private void setButtonClickNavigation(int buttonId, final Class<?> destinationClass) {
        findViewById(buttonId).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNavigate(destinationClass);
            }
        });
    }

    private void onClickNavigate(Class<?> destinationClass) {
        Context context = getContext();
        if (context instanceof Activity) {
            Activity currentActivity = (Activity) context;

            if (!currentActivity.getClass().equals(destinationClass)) {
                Intent intent = new Intent(context, destinationClass);
                currentActivity.startActivity(intent);
            }
        }
    }
}
