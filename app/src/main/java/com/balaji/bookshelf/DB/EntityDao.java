package com.balaji.bookshelf.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EntityDao {
    @Query("SELECT * FROM my_category")
    List<MyEntity> getAllData();

    @Insert
    void insertData(MyEntity... entities);

    @Delete
    void delete(MyEntity myEntity);

    @Query("DELETE FROM my_category")
    void delete();
}
