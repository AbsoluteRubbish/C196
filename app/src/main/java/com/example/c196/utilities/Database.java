package com.example.c196.utilities;


import com.example.c196.databaseAccessObject.DAOAssessment;
import com.example.c196.databaseAccessObject.DAOCourse;
import com.example.c196.databaseAccessObject.DAONote;
import com.example.c196.databaseAccessObject.DAOTerm;
import com.example.c196.entities.EntityAssessment;
import com.example.c196.entities.EntityCourses;
import com.example.c196.entities.EntityNote;
import com.example.c196.entities.EntityTerm;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(exportSchema = false, version = 1,
        entities ={EntityTerm.class, EntityCourses.class, EntityAssessment.class, EntityNote.class})

public abstract class Database extends RoomDatabase{


    public static volatile Database dbInstance;

    public abstract DAOTerm daoTerm();
    public abstract DAOCourse daoCourse();
    public abstract DAOAssessment daoAssessment();
    public abstract DAONote daoNote();

    public static synchronized Database getdbInstance(Context context) {
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "wgu196.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }


}
