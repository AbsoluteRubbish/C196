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
import com.example.c196.adapters.AdapterTerm;
import com.example.c196.utilities.Repository;
import com.example.c196.entities.EntityTerm;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {
Repository repository;
//int termID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        repository = new Repository(getApplication());
        List<EntityTerm> termList = repository.getAllTerms();
        final AdapterTerm adapterTerm = new AdapterTerm(this);
        recyclerView.setAdapter(adapterTerm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterTerm.setTerms(termList);

    }

    //keeps state returning home
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
                RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
                repository = new Repository(getApplication());
                final AdapterTerm adapterTerm = new AdapterTerm(this);
                recyclerView.setAdapter(adapterTerm);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<EntityTerm> termList = repository.getAllTerms();
                adapterTerm.setTerms(termList);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToTermList(View view){
        Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
        startActivity(intent);
    }

    public void addTerm(View view){
        Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
        startActivity(intent);
    }

}