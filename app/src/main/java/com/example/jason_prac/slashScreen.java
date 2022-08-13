package com.example.jason_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class slashScreen extends AppCompatActivity {
    ImageView cloud1,cloud2,cloud3;
    ImageView lighning_01,lighning_02,lighning_03;
    ImageView rain,norain;

    Handler handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        cloud1 = findViewById(R.id.cloud_01);
        cloud2 = findViewById(R.id.cloud_02);
        cloud3 = findViewById(R.id.cloud_03);
        lighning_01 = findViewById(R.id.lightning_01);
        lighning_02 = findViewById(R.id.lightning_02);
        lighning_03 = findViewById(R.id.lightning_03);
        rain = findViewById(R.id.rainUmbrella);
        norain = findViewById(R.id.openUmbrella);

        lighning_01.animate().alpha(1).setDuration(5500);
        cloud2.animate().alpha(0).setDuration(4000);
        lighning_02.animate().alpha(1).setDuration(5500);
        cloud3.animate().alpha(0).setDuration(4000);
        lighning_03.animate().alpha(1).setDuration(5500);
        cloud1.animate().alpha(0).setDuration(4000);
        rain.animate().alpha(1).setDuration(5500);
        norain.animate().alpha(0).setDuration(4000);

        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(slashScreen.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        },5000);
    }
}