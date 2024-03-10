package com.BkAndroidDev.wallpaperplus;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class WallPaperActivity extends AppCompatActivity {

    private FloatingActionButton download,setWallPaper;
    private ImageView wallImage;
    private Photo photo;
    private ImageView goBack;
    private String Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper);
        wallImage = findViewById(R.id.wallPaperImage);
        download = findViewById(R.id.downloadImageBtn);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setWallPaper = findViewById(R.id.setWallPaperImageBtn);
        photo = (Photo)getIntent().getSerializableExtra("image");
        Url = getIntent().getStringExtra("Url");
        Picasso.get().load(photo.getSrc().getLarge()).into(wallImage);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    DownloadManager download = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(Url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI)
                            .setAllowedOverRoaming(true)
                            .setTitle("wallPaper_"+photo.getPhotographer())
                            .setMimeType("image/jpeg")
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,"wallPaper_"+photo.getPhotographer()+".jpg");
                    download.enqueue(request);
                    Toast.makeText(getApplicationContext(),"Download Completed",Toast.LENGTH_SHORT).show();
                }catch(Exception msg){
                    Toast.makeText(getApplicationContext(),"Sorry Failed to Download ",Toast.LENGTH_SHORT).show();
                }

            }
        });
        setWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                Bitmap map = ((BitmapDrawable) wallImage.getDrawable()).getBitmap();
                try{
                    manager.setBitmap(map);
                    Toast.makeText(getApplicationContext(),"Wall Paper applied",Toast.LENGTH_SHORT).show();
                }catch(Exception msg){

                }
            }
        });
    }
}