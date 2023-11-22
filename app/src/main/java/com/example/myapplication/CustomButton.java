package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        init(null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomButton);
            CharSequence customText = a.getString(R.styleable.CustomButton_customText);
            if (customText != null) {
                setText(customText);
            }
            a.recycle();
        }
    }
}