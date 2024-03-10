package com.BkAndroidDev.funage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.view.View;

import android.widget.TextView;
import android.widget.ImageView;
import android.os.Bundle;
import java.util.Locale;

public class MainPage extends AppCompatActivity {

    private TextView SearchName,Age,invisibleName;
    private ImageView invisibleImage;
    private Toolbar customToolBar;
    private CardView detailsCard;
    private SearchView searchView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        SearchName = findViewById(R.id.searchName);
        Age = findViewById(R.id.AgeTxt);
        detailsCard = findViewById(R.id.DetailsCard);

        invisibleName = findViewById(R.id.invisibleTxt);
        invisibleImage = findViewById(R.id.invisibleImage);
        invisibleName.setVisibility(View.VISIBLE);
        invisibleImage.setVisibility(View.VISIBLE);
        detailsCard.setVisibility(View.GONE);
        customToolBar = findViewById(R.id.mainToolBar);
        searchView = findViewById(R.id.searchView);
        setSupportActionBar(customToolBar);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please Wait for a movement");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setIndeterminate(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager requestManager = new RequestManager(getApplicationContext());
                requestManager.getAgeListener(listener,query.trim());
                dialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    private onDataFetchListener listener = new onDataFetchListener() {
        @Override
        public void onDataFetch(ApiObjectModal modal) {
            setData(modal);
        }
    };

    private void setData(ApiObjectModal modal) {
        invisibleImage.setVisibility(View.GONE);
        invisibleName.setVisibility(View.GONE);
        dialog.dismiss();
        detailsCard.setVisibility(View.VISIBLE);
        SearchName.setText(modal.getName());
        Age.setText("We Think That Your Age Will Be \n\n\t"+modal.getAge());

    }
}