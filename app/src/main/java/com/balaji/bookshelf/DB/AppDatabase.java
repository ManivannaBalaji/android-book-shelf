package com.balaji.bookshelf.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MyEntity.class, FavEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntityDao entityDao();
    public abstract FavDao favDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
