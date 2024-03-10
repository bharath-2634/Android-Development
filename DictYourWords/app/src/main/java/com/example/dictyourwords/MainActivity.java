package com.example.dictyourwords;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = findViewById(R.id.mainLayoutProgress);
        bar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),MainDictionary.class));

            }
        },3000);
    }
}