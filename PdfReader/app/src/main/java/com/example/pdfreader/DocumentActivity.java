package com.example.pdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import java.io.File;
import android.content.Intent;
import com.github.barteksc.pdfviewer.PDFView;

public class DocumentActivity extends AppCompatActivity {

    private PDFView pdfView;
    private String path="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        pdfView = findViewById(R.id.pdfView);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        File file = new File(path);
        Uri uriPath = Uri.fromFile(file);
        pdfView.fromUri(uriPath).load();

    }
}