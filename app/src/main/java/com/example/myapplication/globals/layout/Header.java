package com.example.myapplication.globals.layout; // Update with your actual package name

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout ;
import com.example.myapplication.R;


public class Header extends RelativeLayout  {
    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(
            R.layout.header,
            this,
            true
        );
    }
}
