package com.balaji.bookshelf.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_interest")
public class FavEntity {
    @PrimaryKey(autoGenerate = true)
    public int SNO;

    @ColumnInfo(name = "FAVOURITES")
    public String favourites;
}
