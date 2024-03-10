package com.example.dictyourwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import com.example.dictyourwords.Adapters.MeaningsAdapter;
import com.example.dictyourwords.Adapters.PhoneticsAdapter;
import com.example.dictyourwords.Modals.ApiResponse;
import com.example.dictyourwords.Modals.Meanings;

import java.util.ArrayList;

public class MainDictionary extends AppCompatActivity {

    private RecyclerView Phonetics,Meanings;
    private TextView SearchWord;
    private PhoneticsAdapter phoneticsAdapter;
    private MeaningsAdapter meaningsAdapter;
    private SearchView searchView;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dictionary);
        Phonetics = findViewById(R.id.phonetics);
        Meanings =findViewById(R.id.MeaningsRecycler);
        searchView = findViewById(R.id.searchView);
        SearchWord = findViewById(R.id.SearchWord);
        toolBar = findViewById(R.id.customToolBar);
        setSupportActionBar(toolBar);
        RequestManager manager =new RequestManager(getApplicationContext());
        manager.getMeaning(data,"hello");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager manager =new RequestManager(getApplicationContext());
                manager.getMeaning(data,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    private OnFetchData data = new OnFetchData() {
        @Override
        public void onFetchData(ApiResponse response) {
            setAllData(response);
        }
    };
    private void setAllData(ApiResponse response){
        SearchWord.setText(response.getWord());
        phoneticsAdapter  = new PhoneticsAdapter(response.getPhonetics());
        Phonetics.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Phonetics.setAdapter(phoneticsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(Meanings.VERTICAL);
        Meanings.setLayoutManager(layoutManager);
        meaningsAdapter = new MeaningsAdapter(response.getMeanings(),getApplicationContext());
        Meanings.setAdapter(meaningsAdapter);
    }
}