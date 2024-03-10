package com.BkAndroidDev.inotes.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NotesTable")
public class Notes_Table {

    @PrimaryKey(autoGenerate = true)
    private int Id =0;

    @ColumnInfo(name = "Title")
    private String Notes_Title = "";

    @ColumnInfo(name = "notes")
    private String Notes_description = "";

    @ColumnInfo(name = "day")
    private String day = "";

    @ColumnInfo(name = "date")
    private String date = "";

    @ColumnInfo(name ="is_pinned")
    private boolean is_pinned = false;

    @ColumnInfo(name = "NotesDate")
    private String NotesDate = "";

    public String getNotesDate() {
        return NotesDate;
    }

    public void setNotesDate(String notesDate) {
        NotesDate = notesDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNotes_Title() {
        return Notes_Title;
    }

    public void setNotes_Title(String notes_Title) {
        Notes_Title = notes_Title;
    }

    public String getNotes_description() {
        return Notes_description;
    }

    public void setNotes_description(String notes_description) {
        Notes_description = notes_description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isIs_pinned() {
        return is_pinned;
    }

    public void setIs_pinned(boolean is_pinned) {
        this.is_pinned = is_pinned;
    }
}
