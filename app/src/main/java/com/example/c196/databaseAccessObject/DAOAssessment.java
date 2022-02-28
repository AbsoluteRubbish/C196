package com.example.c196.databaseAccessObject;

import com.example.c196.entities.EntityAssessment;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOAssessment {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityAssessment entityAssessment);

    @Update
    void update(EntityAssessment entityAssessment);

    @Delete
    void delete(EntityAssessment entityAssessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<EntityAssessment> getAllAssessments();



}
