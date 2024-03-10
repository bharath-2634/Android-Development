package com.BkAndroidDev.inotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.view.View;
import com.BkAndroidDev.inotes.DataBase.iNotes_DataBase;

public class MainActivity extends AppCompatActivity {

    private CardView nextBtn;
    private iNotes_DataBase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextBtn = findViewById(R.id.onBoardingCard);

        DB = iNotes_DataBase.getInstance(MainActivity.this);
        if(DB.Notes().userDisplay()){
           startActivity(new Intent(getApplicationContext(),MainPage.class));
           finish();
        }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MainPage.class));
                finish();
            }
        });

    }
}