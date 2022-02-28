package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196.R;
import com.example.c196.adapters.AdapterCourse;
import com.example.c196.entities.EntityCourses;
import com.example.c196.utilities.Repository;

import java.util.List;

public class CourseActivity extends AppCompatActivity {
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repository = new Repository(getApplication());
        List<EntityCourses> courseList = repository.getAllCourses();
        final AdapterCourse adapterCourse = new AdapterCourse(this);
        recyclerView.setAdapter(adapterCourse);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterCourse.setCourses(courseList);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshList:
                RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
                repository = new Repository(getApplication());
                final AdapterCourse adapterCourse = new AdapterCourse(this);
                recyclerView.setAdapter(adapterCourse);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<EntityCourses> coursesList = repository.getAllCourses();
                adapterCourse.setCourses(coursesList);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToCourseList(View view) {
        Intent intent = new Intent(CourseActivity.this, AddCourseActivity.class);
        startActivity(intent);
    }
}