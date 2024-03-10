package com.BkAndroidDev.inotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;
import com.BkAndroidDev.inotes.DataBase.iNotes_DataBase;

public class NotesViewPage extends AppCompatActivity {

    private ImageView backBtn,shareBtn,tagBtn;
    private TextView dateTxt,NotesCard;
    private EditText NotesTitle,Notes;
    private Button saveBtn;
    private iNotes_DataBase DB;
    boolean isPinned;
    private int Id;
    private String title,Description,Date;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_page);
        backBtn = findViewById(R.id.viewPageBackBtn);
        shareBtn = findViewById(R.id.viewPageShareBtn);
        tagBtn = findViewById(R.id.viewPageTagBtn);
        dateTxt = findViewById(R.id.viewPageDateTxt);
        NotesTitle = findViewById(R.id.viewPageNotesTitle);
        Notes = findViewById(R.id.viewPageNotes);
        saveBtn = findViewById(R.id.viewPageSaveBtn);
        NotesCard = findViewById(R.id.viewPageTitleCard);
        DB = iNotes_DataBase.getInstance(NotesViewPage.this);
        dialog = new ProgressDialog(NotesViewPage.this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");

                i.putExtra(Intent.EXTRA_TEXT,"Title: "+title+"\nDescription:  "+Description);
                startActivity(Intent.createChooser(i,"share to"));

            }
        });

        Intent intent = getIntent();
        title = intent.getStringExtra("NotesTitle");
        Description  = intent.getStringExtra("NotesDescription");
        Date = intent.getStringExtra("NotesDate");
        Id = intent.getIntExtra("Id",0);
        isPinned = intent.getBooleanExtra("Tag",false);
        NotesCard.setText(title);

        if(isPinned){
            tagBtn.setImageResource(R.drawable.pin);
        }else if(!isPinned){
            tagBtn.setImageResource(R.drawable.not_pin);
        }else{
            tagBtn.setImageResource(R.drawable.not_pin);
        }

        dateTxt.setText(Date);
        NotesTitle.setText(title);
        Notes.setText(Description);

        tagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPinned==true){
                    isPinned = false;
                    tagBtn.setImageResource(R.drawable.not_pin);
                }else if(isPinned==false){
                    isPinned = true;
                    tagBtn.setImageResource(R.drawable.pin);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                onBackPressed();
            }
        });

    }
    private void save(){
        String newTitle = NotesTitle.getText().toString();
        String newDescription = Notes.getText().toString();
        boolean Pin = isPinned;
        dialog.setTitle("Please Wait");
        dialog.setMessage("Saving Notes");
        dialog.show();
        if(newTitle.isEmpty() &&  newDescription.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Fill Your Note Pad", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(),"Notes Updated",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DB.Notes().is_Pinned(Id,Pin);
                    DB.Notes().update(newTitle,newDescription,Id);
                    dialog.dismiss();
                }
            },2000);


        }

    }
}