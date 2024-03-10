package com.BkAndroidDev.inotes.DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserTable")
public class User_Table {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "is_User")
    private boolean isUser = false;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
