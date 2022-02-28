package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.c196.R;
import com.example.c196.ViewModel.AssessmentType;
import com.example.c196.ViewModel.StatusOfCourse;
import com.example.c196.entities.EntityAssessment;
import com.example.c196.utilities.Repository;
import com.example.c196.entities.EntityCourses;
import com.example.c196.entities.EntityNote;
import com.example.c196.entities.EntityTerm;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*new inserts*/
        Repository repository= new Repository(getApplication());
        repository.insert(new EntityTerm(1,"Spring Term 2022", "3/1/22", "9/1/22"));
        repository.insert(new EntityTerm(2,"Fall Term 2022", "9/1/22", "12/31/22"));
        repository.insert(new EntityTerm(3, "Spring Term 2023", "1/1/23", "6/1/23"));

        repository.insert(new EntityCourses(1,1, "Data Management", "3/1/22", "5/1/22", StatusOfCourse.IN_PROGRESS , "Bill", "555-666-7777", "bill@wgu.edu"));
        repository.insert(new EntityCourses(2, 2, "Web Dev", "9/1/22", "10/31/22", StatusOfCourse.COURSES_TO_TAKE , "John", "444-666-1234", "john@wgu.edu"));

        repository.insert(new EntityAssessment(1, 1, "Data Management Test", AssessmentType.OA, "4/30/22", "5/1/22"));
        repository.insert(new EntityAssessment(2, 2, "Web Dev", AssessmentType.PA, "9/1/22", "10/31/22"));

        repository.insert(new EntityNote(1, 1, "Data Note", "Note Content of Note1, Data Management"));
        repository.insert(new EntityNote(2, 2, "Web Dev Note", "Note Content of Note2, Web Dev"));


        Button termBtn = findViewById(R.id.termButton);
        Button courseBtn = findViewById(R.id.courseButton);
        Button assessmentBtn = findViewById(R.id.assessmentButton);
        Button noteBtn = findViewById(R.id.noteButton);

        termBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TermActivity.class);
            startActivity(intent);
        });

        courseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseActivity.class);
            startActivity(intent);
        });

        assessmentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AssessmentActivity.class);
            startActivity(intent);
        });
        noteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(intent);
        });
    }
}