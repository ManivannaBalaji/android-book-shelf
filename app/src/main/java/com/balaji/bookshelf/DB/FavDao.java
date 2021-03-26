package com.balaji.bookshelf.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavDao {
    @Query("SELECT * FROM MY_INTEREST")
    List<FavEntity> getAllFav();

    @Insert
    void insertFav(FavEntity... favEntities);

    @Delete
    void deleteFav(FavEntity favEntity);

}
