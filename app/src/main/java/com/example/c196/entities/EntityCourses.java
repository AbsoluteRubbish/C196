package com.example.c196.entities;

import com.example.c196.ViewModel.StatusOfCourse;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courses",
    foreignKeys = @ForeignKey(entity = EntityTerm.class,
        parentColumns = "termID", childColumns = "termID", onDelete = CASCADE))


public class EntityCourses {

    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private int termID;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;

    private StatusOfCourse courseStatus;

    private String courseInstructorName;
    private String courseInstructorPhone;
    private String courseInstructorEmail;

    @Override
    public String toString() {
        return "EntityCourses{" +
                "courseID=" + courseID +
                ", termID=" + termID +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", courseEndDate='" + courseEndDate + '\'' +
                ", courseStatus=" + courseStatus +
                ", courseInstructorName='" + courseInstructorName + '\'' +
                ", courseInstructorPhone='" + courseInstructorPhone + '\'' +
                ", courseInstructorEmail='" + courseInstructorEmail + '\'' +
                '}';
    }

    public EntityCourses(int courseID, int termID,
                         String courseTitle, String courseStartDate, String courseEndDate,
                        StatusOfCourse courseStatus,
                         String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail){
        this.courseID = courseID;
        this.termID = termID;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
    }

    //setters & getters
    public int getTermID(){return termID;}
    public void setTermID(int termID){this.termID = termID;}

    public int getCourseID(){return courseID;}
    public void setCourseID(int courseID){this.courseID = courseID;}

    public String getCourseTitle(){return courseTitle;}
    public void setCourseTitle(String courseTitle){this.courseTitle = courseTitle;}

    public String getCourseStartDate(){return courseStartDate;}
    public void setCourseStartDate(String courseStartDate){this.courseStartDate = courseStartDate;}

    public String getCourseEndDate(){return courseEndDate;}
    public void setCourseEndDate(String courseEndDate){this.courseEndDate = courseEndDate;}

    public StatusOfCourse getCourseStatus(){return courseStatus;}
    public void setCourseStatus(StatusOfCourse courseStatus){this.courseStatus = courseStatus;}

    public String getCourseInstructorName(){return courseInstructorName;}
    public void setCourseInstructorName(String courseInstructorName){this.courseInstructorName = courseInstructorName;}

    public String getCourseInstructorPhone(){return courseInstructorPhone;}
    public void setCourseInstructorPhone(String courseInstructorPhone){this.courseInstructorPhone = courseInstructorPhone;}

    public String getCourseInstructorEmail(){return  courseInstructorEmail;}
    public void setCourseInstructorEmail(String courseInstructorEmail){this.courseInstructorEmail = courseInstructorEmail;}


}
