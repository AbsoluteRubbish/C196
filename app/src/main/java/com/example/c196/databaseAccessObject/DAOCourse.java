package com.example.c196.databaseAccessObject;

import com.example.c196.entities.EntityCourses;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOCourse {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityCourses entityCourses);

    @Update
    void update(EntityCourses entityCourses);

    @Delete
    void delete(EntityCourses entityCourses);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<EntityCourses> getAllCourses();
}
