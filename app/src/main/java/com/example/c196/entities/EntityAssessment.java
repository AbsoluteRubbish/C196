package com.example.c196.entities;

import com.example.c196.ViewModel.AssessmentType;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessments",
        foreignKeys = @ForeignKey(entity = EntityCourses.class,
        parentColumns = "courseID", childColumns = "courseID", onDelete = CASCADE))

public class EntityAssessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;

    private int courseID;
    private String assessmentTitle;
    private AssessmentType assessmentType;
    private String assessmentStartDate;
    private String assessmentEndDate;

    @Override
    public String toString() {
        return "EntityAssessment{" +
                "assessmentID=" + assessmentID +
                ", courseID=" + courseID +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentType=" + assessmentType +
                ", assessmentStartDate='" + assessmentStartDate + '\'' +
                ", assessmentEndDate='" + assessmentEndDate + '\'' +
                '}';
    }
    public EntityAssessment(int assessmentID, int courseID, String assessmentTitle, AssessmentType assessmentType, String assessmentStartDate, String assessmentEndDate){
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
    }
    //setters & getters
    public int getAssessmentID(){return assessmentID;}
    public void setAssessmentID(int assessmentID){this.assessmentID = assessmentID;}

    public int getCourseID(){return courseID;}
    public void setCourseID(int courseID) {this.courseID = courseID;}

    public String getAssessmentTitle(){return assessmentTitle;}
    public void setAssessmentTitle(String assessmentTitle){this.assessmentTitle = assessmentTitle;}

    public AssessmentType getAssessmentType(){return assessmentType;}
    public void setAssessmentType(AssessmentType assessmentType){this.assessmentType = assessmentType;}

    public String getAssessmentStartDate(){return assessmentStartDate;}
    public void setAssessmentStartDate(String assessmentStartDate){this.assessmentStartDate = assessmentStartDate;}

    public String getAssessmentEndDate(){return assessmentEndDate;}
    public void setAssessmentEndDate(String assessmentEndDate){this.assessmentEndDate = assessmentEndDate;}


}
