package com.example.c196.utilities;

import android.app.Application;

import com.example.c196.databaseAccessObject.DAOAssessment;
import com.example.c196.databaseAccessObject.DAOCourse;
import com.example.c196.databaseAccessObject.DAONote;
import com.example.c196.databaseAccessObject.DAOTerm;
import com.example.c196.entities.EntityAssessment;
import com.example.c196.entities.EntityCourses;
import com.example.c196.entities.EntityNote;
import com.example.c196.entities.EntityTerm;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private DAOTerm daoTerm;
    private DAOCourse daoCourse;
    private DAOAssessment daoAssessment;
    private DAONote daoNote;
    private List<EntityTerm> allTerms;
    private List<EntityCourses> allCourses;
    private List<EntityAssessment> allAssessments;
    private List<EntityNote> allNotes;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        Database db = Database.getdbInstance(application);
        daoTerm=db.daoTerm();
        daoCourse=db.daoCourse();
        daoAssessment= db.daoAssessment();
        daoNote= db.daoNote();
    }
    //lists
    public List<EntityTerm> getAllTerms(){
        dbExecutor.execute(()->{
            allTerms=daoTerm.getAllTerms();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allTerms;
    }


    public List<EntityCourses> getAllCourses(){
        dbExecutor.execute(()->{
            allCourses=daoCourse.getAllCourses();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allCourses;
    }

    public List<EntityAssessment> getAllAssessments() {
      dbExecutor.execute(()->{
            allAssessments=daoAssessment.getAllAssessments();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allAssessments;
    }

    public List<EntityNote> getAllNotes() {
        dbExecutor.execute(()->{
            allNotes=daoNote.getAllNotes();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return allNotes;
    }


    // Terms insert/update/delete

    public void insert(EntityTerm entityTerm){
        dbExecutor.execute(()->{
            daoTerm.insert(entityTerm);
        });
       try{
           Thread.sleep(1000);
       }catch (InterruptedException e){
           e.printStackTrace();
       }
    }

    public void update(EntityTerm entityTerm){
        dbExecutor.execute(()->{
            daoTerm.update(entityTerm);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityTerm entityTerm){
           dbExecutor.execute(()->{
               daoTerm.delete(entityTerm);
           });
           try{
               Thread.sleep(1000);
           }catch (InterruptedException e){
               e.printStackTrace();
           }
        }
//course insert/update/delete
    public void insert(EntityCourses entityCourses){
        dbExecutor.execute(()->{
            daoCourse.insert(entityCourses);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityCourses entityCourses){
        dbExecutor.execute(()->{
            daoCourse.update(entityCourses);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityCourses entityCourses){
        dbExecutor.execute(()->{
            daoCourse.delete(entityCourses);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //ASSESSMENT update/delete/insert
    public void insert(EntityAssessment entityAssessment){
        dbExecutor.execute(()->{
            daoAssessment.insert(entityAssessment);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityAssessment entityAssessment){
        dbExecutor.execute(()->{
            daoAssessment.update(entityAssessment);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityAssessment entityAssessment){
        dbExecutor.execute(()->{
            daoAssessment.delete(entityAssessment);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //notes update/delete/insert
    public void insert(EntityNote entityNote){
        dbExecutor.execute(()->{
            daoNote.insert(entityNote);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(EntityNote entityNote){
        dbExecutor.execute(()->{
            daoNote.update(entityNote);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(EntityNote entityNote){
        dbExecutor.execute(()->{
            daoNote.delete(entityNote);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
