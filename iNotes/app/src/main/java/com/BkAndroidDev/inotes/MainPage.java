package com.BkAndroidDev.inotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.os.Bundle;

import com.BkAndroidDev.inotes.DataBase.User_Table;
import com.BkAndroidDev.inotes.DataBase.iNotes_DataBase;

public class MainPage extends AppCompatActivity {

    private ProgressBar bar;
    private iNotes_DataBase data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bar = findViewById(R.id.main_layout_bar);

        data = iNotes_DataBase.getInstance(MainPage.this);
        bar.setVisibility(View.VISIBLE);
        User_Table table = new User_Table();
        table.setId(0);
        table.setUser(true);
        data.Notes().isUser(table);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                bar.setVisibility(View.INVISIBLE);
                finish();
            }
        },3000);
    }
}