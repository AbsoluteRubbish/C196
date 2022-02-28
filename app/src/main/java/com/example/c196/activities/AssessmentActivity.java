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
import com.example.c196.adapters.AdapterAssessment;
import com.example.c196.entities.EntityAssessment;
import com.example.c196.utilities.Repository;

import java.util.List;

public class AssessmentActivity extends AppCompatActivity {
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repository = new Repository(getApplication());
        List <EntityAssessment> assessmentList = repository.getAllAssessments();
        final AdapterAssessment adapterAssessment = new AdapterAssessment(this);
        recyclerView.setAdapter(adapterAssessment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterAssessment.setAssessment(assessmentList);
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
                RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
                repository = new Repository(getApplication());
                final AdapterAssessment adapterAssessment = new AdapterAssessment(this);
                recyclerView.setAdapter(adapterAssessment);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<EntityAssessment> assessmentList = repository.getAllAssessments();
                adapterAssessment.setAssessment(assessmentList);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAssessmentList(View view){
        Intent intent = new Intent(AssessmentActivity.this, AddAssessmentActivity.class);
        startActivity(intent);
    }

}