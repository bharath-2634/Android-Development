package com.BkAndroidDev.inotes.DataBase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Notes_Table.class,User_Table.class},version = 1,exportSchema = false)
public abstract class iNotes_DataBase extends RoomDatabase {

    private static iNotes_DataBase iNotes_DB = null;

   public static iNotes_DataBase getInstance(Context context){

        if(iNotes_DB==null){
            iNotes_DB = Room.databaseBuilder(context,iNotes_DataBase.class,"iNotes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return iNotes_DB;
    }

    public abstract Notes_DAO Notes();


}
