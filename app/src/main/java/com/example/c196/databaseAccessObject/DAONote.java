package com.example.c196.databaseAccessObject;

import com.example.c196.entities.EntityNote;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAONote {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityNote entityNote);

    @Update
    void update(EntityNote entityNote);

    @Delete
    void delete(EntityNote entityNote);

    @Query("SELECT * FROM notes ORDER BY noteID ASC")
    List<EntityNote> getAllNotes();

}
