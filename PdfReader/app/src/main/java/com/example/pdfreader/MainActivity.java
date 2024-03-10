package com.example.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import java.util.ArrayList;
import java.io.File;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import android.content.Intent;
import android.view.ActionMode;

import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity implements onPdfFileSelected{

    private RecyclerView recyclerview;
    private ArrayList<File> PdfList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = findViewById(R.id.pdfRecycler);
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
             displayPdf();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }
    private ArrayList<File> getPdfFiles(File file){
        ArrayList<File> PdfFiles = new ArrayList<>();
        File[] files = file.listFiles();
        for(File singleFile: files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                PdfFiles.addAll(getPdfFiles(singleFile));
            }else{
                if(singleFile.getName().endsWith(".pdf")){
                    PdfFiles.add(singleFile);
                }
            }
        }
        return PdfFiles;
    }
    private void displayPdf(){
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        PdfList = new ArrayList<>();
        PdfList.addAll(getPdfFiles(Environment.getExternalStorageDirectory()));
        Adapter adapter = new Adapter(PdfList,this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            displayPdf();
        }
    }

    @Override
    public void PdfFileSelected(File file) {
      Intent intent = new Intent(getApplicationContext(),DocumentActivity.class);
      intent.putExtra("path",file.getAbsolutePath());
      startActivity(intent);
    }
}