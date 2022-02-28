package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.entities.EntityCourses;
import com.example.c196.entities.EntityNote;
import com.example.c196.ViewModel.CourseViewModel;
import com.example.c196.utilities.Repository;

import java.util.ArrayList;
import java.util.List;

public class DetailedNoteActivity extends AppCompatActivity {

    Repository repository;

    EditText editTitle;
    EditText editContent;

    Spinner editCourseID;
    ArrayAdapter<CourseViewModel> courseAdapter;
    private int id;
    boolean isValid;

    EntityNote currentNote;

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        repository = new Repository(getApplication());


        editTitle = findViewById(R.id.edit_detailed_note_title);
        editContent = findViewById(R.id.edit_detailed_note_content);

        //courseIDSpinner
        populateCourseSpinner();

        noteID = getIntent().getIntExtra("noteID", -1);
        editTitle.setText(getIntent().getStringExtra("noteTitle"));
        editContent.setText(getIntent().getStringExtra("noteText"));

        id = getIntent().getIntExtra("courseID", -1);
        editCourseID.setSelection(indexInCourseSpinner(id));
    }

       //CourseID Spinner

    private void populateCourseSpinner(){
        editCourseID = findViewById(R.id.courseSpinnerNotes);
        List<CourseViewModel> spinnerCourseTitle = getCourseViewModel();
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,  spinnerCourseTitle);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCourseID.setAdapter(courseAdapter);
    }
    private List<CourseViewModel> getCourseViewModel(){
        List<EntityCourses> courseTitle = repository.getAllCourses();
        List<CourseViewModel> o = new ArrayList<>();
        for (int i=0; i < courseTitle.size(); i++){
            o.add(new CourseViewModel(courseTitle.get(i)));
        }
        return o;
    }

    private int indexInCourseSpinner(int courseID){
        for(int i=0; i<editCourseID.getCount(); i++){
            CourseViewModel item = (CourseViewModel) editCourseID.getItemAtPosition(i);
            if(item.id == courseID){
                return i;
            }
        }
        return -1;
    }

    private int SpinnerCourseIDValue(){
       CourseViewModel item = (CourseViewModel) editCourseID.getSelectedItem();
        return item.id;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //adds item to action bar
        getMenuInflater().inflate(R.menu.menu_detail_with_share_note, menu);
        return true;
    }

    public void saveNoteButton(View view) {
        EntityNote entityNote;
        if( editTitle.getText().toString().trim().isEmpty() || editContent.getText().toString().trim().isEmpty()){
            Toast.makeText(DetailedNoteActivity.this, "Make sure all fields are filled. Note not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(noteID == -1){
            int newID = repository.getAllNotes().get(repository.getAllNotes().size()-1).getNoteID()+1;
            entityNote = new EntityNote(newID, SpinnerCourseIDValue(), editTitle.getText().toString(), editContent.getText().toString());
            repository.insert(entityNote);
            isValid = true;
        }else{
            entityNote = new EntityNote(noteID, SpinnerCourseIDValue(), editTitle.getText().toString(), editContent.getText().toString());
            repository.update(entityNote);
            isValid = true;
        }
        finish();
    }

    public void deleteNoteButton(View view){
        for(EntityNote note : repository.getAllNotes()){
            if(note.getNoteID() == noteID) {
                currentNote = note;
                repository.delete(currentNote);
                Toast.makeText(DetailedNoteActivity.this, currentNote.getNoteTitle() + " was deleted", Toast.LENGTH_LONG).show();
                }
            }
        finish();
    }

    public void shareNoteButton(View view){
        String titleFromScreen = editTitle.getText().toString();
        String contextFromScreen = editContent.getText().toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, contextFromScreen);
        sendIntent.putExtra(Intent.EXTRA_TITLE, titleFromScreen);
        sendIntent.setType("text/plain");
        Intent shareIntent= Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveItem:
                EntityNote entityNote;
                if( editTitle.getText().toString().trim().isEmpty() || editContent.getText().toString().trim().isEmpty()){
                    Toast.makeText(DetailedNoteActivity.this, "Make sure all fields are filled. Note not saved.", Toast.LENGTH_LONG).show();
                    return false;
                }
                if(noteID == -1){
                    int newID = repository.getAllNotes().get(repository.getAllNotes().size()-1).getNoteID()+1;
                    entityNote = new EntityNote(newID, SpinnerCourseIDValue(), editTitle.getText().toString(), editContent.getText().toString());
                    repository.insert(entityNote);
                }else{
                    entityNote = new EntityNote(noteID, SpinnerCourseIDValue(), editTitle.getText().toString(), editContent.getText().toString());
                    repository.update(entityNote);
                }
                finish();
                return true;

            case R.id.deleteItem:
                for(EntityNote note : repository.getAllNotes()){
                    if(note.getNoteID() == noteID) {
                        currentNote = note;
                        repository.delete(currentNote);
                        Toast.makeText(DetailedNoteActivity.this, currentNote.getNoteTitle() + " was deleted. Refresh page.", Toast.LENGTH_LONG).show();
                    }
                }
                finish();
                return true;

            case R.id.shareNote:
                String titleFromScreen = editTitle.getText().toString();
                String contextFromScreen = editContent.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, contextFromScreen);
                sendIntent.putExtra(Intent.EXTRA_TITLE, titleFromScreen);
                sendIntent.setType("text/plain");
                Intent shareIntent= Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}