package com.BkAndroidDev.inotes.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface Notes_DAO {

    @Insert(onConflict = REPLACE)
     void insert(Notes_Table table);

    @Query("UPDATE NotesTable SET Title=:Title,notes=:description WHERE Id=:Id")
    void update(String Title,String description,int Id);


    @Query("UPDATE NotesTable SET is_pinned=:state WHERE Id=:Id")
    void is_Pinned(int Id,boolean state);


    @Query("SELECT * FROM NotesTable ORDER BY Id DESC")
    List<Notes_Table> display();

    @Delete()
    void delete(Notes_Table table);

    @Insert(onConflict = REPLACE)
    void isUser(User_Table table);

    @Query("SELECT * FROM UserTable")
    boolean userDisplay();


}
