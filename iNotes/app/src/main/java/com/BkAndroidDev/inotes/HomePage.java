package com.BkAndroidDev.inotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.DialogInterface;
import android.os.Bundle;
import java.util.List;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.BkAndroidDev.inotes.DataBase.Notes_Table;
import com.BkAndroidDev.inotes.DataBase.iNotes_DataBase;
import androidx.cardview.widget.CardView;

public class HomePage extends AppCompatActivity implements NotesOnClick,pinClickListener,DeleteOnClickListener{

    private CardView addBtn;
    private RecyclerView notes_view;
    private SearchView search_view;
    private SwipeRefreshLayout swipe_layout;
    private List<Notes_Table> notes = new ArrayList<>();
    private iNotes_DataBase DB;
    private NoteViewAdapter notes_Adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addBtn = findViewById(R.id.add_btn);
        notes_view = findViewById(R.id.home_page_recycler);
        search_view = findViewById(R.id.search_view);
        swipe_layout = findViewById(R.id.swipe_layout);
        DB = iNotes_DataBase.getInstance(getApplicationContext());
        notes = DB.Notes().display();
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notes.clear();
                notes.addAll(DB.Notes().display());
                notes_Adapter.notifyDataSetChanged();
                swipe_layout.setRefreshing(false);
            }
        });

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                 List<Notes_Table> resultNotes = new ArrayList<>();
                for(Notes_Table notes:notes){
                    if(notes.getNotes_Title().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)) || notes.getNotes_description().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                        resultNotes.add(notes);
                    }
                }
                notes_Adapter.onSearchResult(resultNotes);
                return true;
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddNotes.class));
            }
        });
        notes_Adapter = new NoteViewAdapter(notes,this,this,getApplicationContext(),this::onDelete);
        LinearLayoutManager layout_manager = new LinearLayoutManager(getApplicationContext());
        layout_manager.setOrientation(notes_view.VERTICAL);
        notes_view.setLayoutManager(layout_manager);
        notes_view.setAdapter(notes_Adapter);
        

    }

    @Override
    public void OnClick(Notes_Table SingleTable) {
        Intent intent = new Intent(getApplicationContext(),NotesViewPage.class);
        intent.putExtra("NotesDate",SingleTable.getNotesDate());
        intent.putExtra("NotesTitle",SingleTable.getNotes_Title());
        intent.putExtra("NotesDescription",SingleTable.getNotes_description());
        intent.putExtra("Id",SingleTable.getId());
        intent.putExtra("Tag",SingleTable.isIs_pinned());
        startActivity(intent);
    }

    @Override
    public void updatePin(boolean isPinned, Notes_Table table) {
        DB.Notes().is_Pinned(table.getId(),isPinned);
    }

    @Override
    public void onDelete(Notes_Table table) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
        builder.setTitle("Do You Want To Delete?");
        builder.setMessage("Deleting Your  "+table.getNotes_Title()+" ");
        builder.setPositiveButton("Yes ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB.Notes().delete(table);
                Toast.makeText(getApplicationContext(),"  "+table.getNotes_Title()+" is Deleted From Your Notes List", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"\nPlease Refresh Your Page",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}