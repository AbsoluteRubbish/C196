package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.c196.R;
import com.example.c196.adapters.AdapterNote;
import com.example.c196.entities.EntityNote;
import com.example.c196.utilities.MyReceiver;
import com.example.c196.utilities.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar();
        getSupportActionBar();
        RecyclerView recyclerView = findViewById(R.id.noteRecyclerView);
        repository = new Repository(getApplication());
        List<EntityNote> noteList = repository.getAllNotes();
        final AdapterNote adapterNote = new AdapterNote(this);
        recyclerView.setAdapter(adapterNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterNote.setNotes(noteList);
    }
     public boolean onCreateOptionsMenu(Menu menu){
        //adds item to action bar
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshList:
                RecyclerView recyclerView = findViewById(R.id.noteRecyclerView);
                repository = new Repository(getApplication());
                final AdapterNote adapterNote = new AdapterNote(this);
                recyclerView.setAdapter(adapterNote);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<EntityNote> noteList = repository.getAllNotes();
                adapterNote.setNotes(noteList);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToNoteList(View view){
        Intent intent = new Intent(NoteActivity.this, AddNoteActivity.class);
        startActivity(intent);
    }
}