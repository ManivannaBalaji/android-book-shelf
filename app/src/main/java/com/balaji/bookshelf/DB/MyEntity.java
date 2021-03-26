package com.balaji.bookshelf.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_category")
public class MyEntity {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "NAME")
    public String name;
}
