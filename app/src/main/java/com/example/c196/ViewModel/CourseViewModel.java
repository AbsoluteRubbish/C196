package com.example.c196.ViewModel;

import com.example.c196.entities.EntityCourses;

public final class CourseViewModel {
    public final int id;
    public final String title;

    public CourseViewModel(int id, String title){
        this.id = id;
        this.title=title;
    }
    public CourseViewModel(EntityCourses c){
        this.id = c.getCourseID();
        this.title = c.getCourseTitle();
    }
    @Override
    public String toString() {
        return  title;
    }
}
