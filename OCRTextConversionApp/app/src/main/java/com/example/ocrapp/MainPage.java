package com.example.ocrapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainPage extends AppCompatActivity {

    private Button captureBtn;
    private static final int REQUEST_CODE = 100;
    private Bitmap bitmap;
    private Spinner spinner;
    private String item;
    private TextToSpeech textToSpeech;
    private SeekBar pitchBar,speedBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        captureBtn = findViewById(R.id.captureBtn);
        spinner = findViewById(R.id.spinner);
        pitchBar = findViewById(R.id.seek_bar_pitch);
        speedBar = findViewById(R.id.seek_bar_speed);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                item = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("English");
        arrayList.add("Canada");
        arrayList.add("Canada French");
        arrayList.add("China");
        arrayList.add("Chinese");
        arrayList.add("French");
        arrayList.add("France");
        arrayList.add("Germany");
        arrayList.add("GERMANY");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPage.this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        if(ContextCompat.checkSelfPermission(MainPage.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainPage.this, new String[] {
                    Manifest.permission.CAMERA
            },REQUEST_CODE);
        }

        textToSpeech = new TextToSpeech(MainPage.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i!=TextToSpeech.ERROR) {
                    float speed = (float) speedBar.getProgress() / 50;
                    float pitch = (float) pitchBar.getProgress() / 50;

                    textToSpeech.setPitch(pitch);
                    textToSpeech.setSpeechRate(speed);
                }
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MainPage.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);
                    getTextFromImage(bitmap);
                }catch( IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainPage.this, "Sorry There is Some Exception", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(MainPage.this, "Text Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getTextFromImage(Bitmap bitmap) {

        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();

        if(!recognizer.isOperational()) {
            Toast.makeText(MainPage.this, "Did not Work", Toast.LENGTH_SHORT).show();
        }else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0;i<textBlockSparseArray.size();i++) {
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }

            Toast.makeText(MainPage.this,"Selected Language : "+item,Toast.LENGTH_SHORT).show();

            if(item == "English") {

                textToSpeech.setLanguage(Locale.ENGLISH);
            }else if(item == "Canada") {

                textToSpeech.setLanguage(Locale.CANADA);
            }else if(item == "Canada French") {

                textToSpeech.setLanguage(Locale.CANADA_FRENCH);
            }else if(item == "China") {

                textToSpeech.setLanguage(Locale.CHINA);
            }else if(item == "Chinese") {

                textToSpeech.setLanguage(Locale.CHINESE);
            }else if(item == "French") {

                textToSpeech.setLanguage(Locale.FRENCH);
            }else if(item == "France") {

                textToSpeech.setLanguage(Locale.FRANCE);
            }else if(item == "Germany") {

                textToSpeech.setLanguage(Locale.GERMANY);
            }else if(item == "GERMANY") {

                textToSpeech.setLanguage(Locale.GERMAN);
            }

            textToSpeech.speak(stringBuilder.toString(),TextToSpeech.QUEUE_FLUSH,null);

        }
    }
}