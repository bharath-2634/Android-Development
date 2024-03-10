package com.BkAndroidDev.funage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.mainProgressBar);
        bar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bar.setVisibility(View.INVISIBLE);
                   startActivity(new Intent(getApplicationContext(),MainPage.class));
            }
        },3000);
    }
}