package com.example.ocrapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    private ProgressBar bar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.titleTxt);
        bar = findViewById(R.id.main_layout_bar);
        bar.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.Bounce).duration(1000).repeat(1).playOn(textView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainPage.class));
                bar.setVisibility(View.INVISIBLE);
                finish();
            }
        },3000);

    }
}