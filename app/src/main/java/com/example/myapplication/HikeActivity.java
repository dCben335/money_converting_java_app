package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike);

        fillTextView("mamadou", R.id.nom_rando);
        fillTextView("mamadou", R.id.duree_rando);
        fillTextView("mamadou", R.id.description_rand);
    }

    public void onClickDiscover(View view) {
        Intent intent = new Intent(this, DiscoverActivity.class);
        startActivity(intent);
    }

    public void onClickBack(View view) {
        this.finish();
    }

    private void fillTextView(String textContent, int field_id) {
        TextView field = (TextView) findViewById( field_id );
        field.setText(textContent);
    }
}