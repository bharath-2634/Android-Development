package com.BkAndroidDev.wallpaperplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

import android.view.WindowManager;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.widget.ProgressBar;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainPage extends AppCompatActivity implements cardOnClickListener{

    private Toolbar bar;
    private RecyclerView imageList;
    private Adapter adapter;
    private RequestManager manager;
    private ProgressBar progressBar;
    private FloatingActionButton next,pre;
    int PageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bar = findViewById(R.id.customToolBar);
        setSupportActionBar(bar);
//
        imageList = findViewById(R.id.mainRecycler);
        manager = new RequestManager(getApplicationContext());
        progressBar = findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        next = findViewById(R.id.nextBtn);
        pre = findViewById(R.id.previousBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String page = String.valueOf(PageNumber+1);
                manager.getResponse(listener,page);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PageNumber>1){
                String page = String.valueOf(PageNumber-1);
                manager.getResponse(listener,page);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        manager.getResponse(listener,"1");
        progressBar.setVisibility(View.INVISIBLE);
    }
    private onDataFetchListener listener = new onDataFetchListener() {
        @Override
        public void onDataFetchListener(CurratedResponse response) {
            setData(response.getPhotos());
            PageNumber = response.getPage();
        }
    };
    private void setData(ArrayList<Photo> photo){
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        imageList.setLayoutManager(layoutManager);
        adapter = new Adapter(photo,this,getApplicationContext());
        imageList.setAdapter(adapter);
    }

    @Override
    public void onClick(Photo photo) {
        Toast.makeText(getApplicationContext(),""+photo.getPhotographer(),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),WallPaperActivity.class)
        .putExtra("image",photo)
        .putExtra("Url",photo.getSrc().getOriginal()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);
        SearchView view = (SearchView) menuItem.getActionView();
        view.setQueryHint("search for Wallpaper");
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                manager.searchImage(Searchlistener,"1",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private searchCall Searchlistener = new searchCall() {
        @Override
        public void searchImage(searchResponse response) {
            setData(response.getPhotos());
        }
    };
}