package com.example.c196.entities;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class EntityTerm{

    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termTitle;
    private String termStartDate;
    private String termEndDate;

    @Override
    public String toString() {
        return "EntityTerm{" +
                "termID=" + termID +
                ", termTitle='" + termTitle + '\'' +
                ", termStartDate='" + termStartDate + '\'' +
                ", termEndDate='" + termEndDate + '\'' +
                '}';
    }

    public EntityTerm(int termID, String termTitle, String termStartDate, String termEndDate){
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    //setters & getter

    public int getTermID(){
        return termID;
    }
    public void setTermID(int termID){
        this.termID = termID;
    }

    public String getTermTitle(){
        return termTitle;
    }
    public void setTermTitle(String title){
        this.termTitle = termTitle;
    }

    public String getTermStartDate(){
        return termStartDate;
    }
    public void setTermStartDate(String termStartDate){
        this.termStartDate = termStartDate;
    }

    public String getTermEndDate(){
        return termEndDate;
    }
    public void setTermEndDate(String termEndDate){
        this.termEndDate = termEndDate;
    }

}
