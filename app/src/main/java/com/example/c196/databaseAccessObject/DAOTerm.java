package com.example.c196.databaseAccessObject;

import com.example.c196.entities.EntityTerm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOTerm {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityTerm entityTerm);

    @Update
    void update(EntityTerm entityTerm);

    @Delete
    void delete(EntityTerm entityTerm);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<EntityTerm> getAllTerms();


}
