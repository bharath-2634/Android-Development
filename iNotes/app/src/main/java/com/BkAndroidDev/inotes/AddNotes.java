package com.BkAndroidDev.inotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;
import com.BkAndroidDev.inotes.DataBase.Notes_Table;
import com.BkAndroidDev.inotes.DataBase.iNotes_DataBase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddNotes extends AppCompatActivity {

    private EditText heading,description;
    private ImageView back,pin,titleCopy,DescriptionCopy;
    private Button save;
    private TextView date_txt,iNotes_Title;
    private Toolbar toolBar;
    private ProgressDialog ProgressDialog;
    private iNotes_DataBase database;
    private boolean isPinnedNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_notes);
        heading = findViewById(R.id.Title_edt);
        description = findViewById(R.id.description_edt);
        back = findViewById(R.id.back_btn);
        titleCopy = findViewById(R.id.title_copy);
        DescriptionCopy = findViewById(R.id.description_copy);
        pin = findViewById(R.id.add_Notes_pinBtn);
        save = findViewById(R.id.saveBtn);
        date_txt = findViewById(R.id.Notes_date);
        iNotes_Title = findViewById(R.id.title_toolBar);
        toolBar = findViewById(R.id.Notes_toolBar);
        setSupportActionBar(toolBar);
        database = iNotes_DataBase.getInstance(AddNotes.this);
        ProgressDialog = new ProgressDialog(AddNotes.this);
        Calendar calender = Calendar.getInstance();
        String Date = DateFormat.getDateInstance().format(calender.getTime());
        date_txt.setText(Date);


        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPinnedNotes==false){
                    isPinnedNotes=true;
                    pin.setImageResource(R.drawable.pin);
                    Toast.makeText(getApplicationContext(),"Pinned",Toast.LENGTH_SHORT).show();
                } else{
                    isPinnedNotes=false;
                    pin.setImageResource(R.drawable.not_pin);
                    Toast.makeText(getApplicationContext(),"UnPinned",Toast.LENGTH_SHORT).show();
                }


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String back_Title = heading.getText().toString();
                String back_des = description.getText().toString();

                if(back_Title.isEmpty() && back_des.isEmpty()){
                    onBackPressed();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNotes.this);
                    builder.setTitle("Do You Want To Save  "+back_Title+"  Notes ?");
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onBackPressed();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String Title = heading.getText().toString();
                            String Notes = description.getText().toString();
                            if(Title.isEmpty() && Notes.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Please Fill Your Details",Toast.LENGTH_SHORT).show();
                            }else{
                                saveNotes(Title,Notes);
                            }

                        }
                    });
                    AlertDialog SaveDialog = builder.create();
                    SaveDialog.setCanceledOnTouchOutside(false);
                    SaveDialog.show();
                }

            }
        });
//

        titleCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_txt = heading.getText().toString();
                if(title_txt.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Your Title is Empty",Toast.LENGTH_SHORT).show();
                }else{
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("copied Data",title_txt);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(),"Copied to your Clip Board", Toast.LENGTH_SHORT).show();
                }


            }
        });

        DescriptionCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NotesDescription_1 = description.getText().toString();
                if(NotesDescription_1.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Your Description is Empty",Toast.LENGTH_SHORT).show();
                }else{
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("copied Data",NotesDescription_1);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(),"Copied to your Clip Board", Toast.LENGTH_SHORT).show();
                }


            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = heading.getText().toString();
                String Notes = description.getText().toString();
                if(Title.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Fill Your Details",Toast.LENGTH_SHORT).show();
                }else if(Notes.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Fill Your Details",Toast.LENGTH_SHORT).show();
                }else{
                    saveNotes(Title,Notes);
                }


            }
        });

    }

    private void saveNotes(String Title,String Notes){
        Notes_Table table = new Notes_Table();
        Calendar calender = Calendar.getInstance();
        String Date = DateFormat.getDateInstance().format(calender.getTime());
        table.setNotesDate(Date);
        String NoteDate = new SimpleDateFormat("dd", Locale.ENGLISH).format(new Date());
        String NoteDay = new SimpleDateFormat("MMM").format(new Date());

        table.setDate(NoteDate);
        table.setDay(NoteDay);


        table.setNotes_Title(Title);
        table.setNotes_description(Notes);


        if(isPinnedNotes){
            table.setIs_pinned(true);
        }else{
            table.setIs_pinned(false);
        }
        ProgressDialog.setTitle("Saving Your Notes...");
        ProgressDialog.setMessage("Please Wait");
        ProgressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                database.Notes().insert(table);

                Toast.makeText(getApplicationContext(),"Your Notes Saved",Toast.LENGTH_SHORT).show();
                ProgressDialog.dismiss();
                onBackPressed();
            }
        },2000);


    }
}