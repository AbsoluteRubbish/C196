package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.adapters.AdapterCourse;
import com.example.c196.entities.EntityCourses;
import com.example.c196.utilities.Repository;
import com.example.c196.entities.EntityTerm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedTermActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    int termID;
    int numCourses;
    EntityTerm currentTerm;

    Repository repository;

    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);
        repository = new Repository(getApplication());


        editTitle = findViewById(R.id.edit_detailed_term_title);
        editStart = findViewById(R.id.edit_detailed_term_start_date);
        editEnd = findViewById(R.id.edit_detailed_term_end_date);
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        termID = getIntent().getIntExtra("termID", -1);

        editTitle.setText(getIntent().getStringExtra("termTitle"));
        editStart.setText(getIntent().getStringExtra("termStartDate"));
        editEnd.setText(getIntent().getStringExtra("termEndDate"));

//recycler view for courses under term
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.courseUnderTermRecyclerView);
        final AdapterCourse adapterCourse = new AdapterCourse(this);
        recyclerView.setAdapter(adapterCourse);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<EntityCourses> filteredCourses = new ArrayList<>();
        for (EntityCourses c : repository.getAllCourses()){
            if(c.getTermID() == termID) filteredCourses.add(c);
        }
        adapterCourse.setCourses(filteredCourses);


//start date picker
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStart.getText().toString();
                if (info.equals(""))info="2/10/22";
                try{
                    myCalendar.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedTermActivity.this, startDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        //end date picker
        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEnd.getText().toString();
                if (info.equals(""))info="2/10/22";
                try{
                    myCalendar.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedTermActivity.this, endDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }
    public static boolean isDateCorrect(String startDate, String endDate) {
        try {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            Date endD = sdf.parse(endDate);
            Date startD = sdf.parse(startDate);
            if (endD.after(startD))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;

        }
    }
    private void updateLabelStart() {
        editStart.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabelEnd() {
            editEnd.setText(sdf.format(myCalendar.getTime()));
        }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    public void createCourseButton(View view){
        Intent intent = new Intent(DetailedTermActivity.this, AddCourseActivity.class);
        Bundle b = new Bundle();
        b.putInt("termID", termID);
        b.putString("editStart", editStart.getText().toString());
        b.putString("editEnd", editEnd.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
    }

    public void saveTermButton(View view){
        EntityTerm entityTerm;
        if (isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false || editTitle.getText().toString().trim().isEmpty()
                || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty() ){
            Toast.makeText(DetailedTermActivity.this, "Make sure all fields are filled and start Date is before End Date. Term not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(termID == -1){
            int newID = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermID()+1;
            entityTerm = new EntityTerm(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.insert(entityTerm);
            isValid = true;
        }else{
            entityTerm = new EntityTerm(termID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.update(entityTerm);
            isValid = true;
        }
       finish();
    }

    public void deleteTermButton(View view){
        for(EntityTerm term : repository.getAllTerms()){
            if(term.getTermID() == termID) currentTerm = term;
        }
        numCourses = 0;
        for (EntityCourses entityCourses: repository.getAllCourses()){
            if(entityCourses.getTermID() == termID) ++numCourses;
        }
        if(numCourses == 0){
            repository.delete(currentTerm);
            Toast.makeText(DetailedTermActivity.this, currentTerm.getTermTitle() + " was deleted. Please refresh page.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DetailedTermActivity.this, "Can Not Delete Term with Courses", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveItem:
                EntityTerm entityTerm;
                if (isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false || editTitle.getText().toString().trim().isEmpty()
                        || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty() ){
                    Toast.makeText(DetailedTermActivity.this, "Make sure all fields are filled and start Date is before End Date. Term not saved.", Toast.LENGTH_LONG).show();
                    return false;
                }
                if(termID == -1){
                    int newID = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermID()+1;
                    entityTerm = new EntityTerm(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(entityTerm);
                }else{
                    entityTerm = new EntityTerm(termID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(entityTerm);
                }
                finish();
                return true;

            case R.id.deleteItem:
                for(EntityTerm term : repository.getAllTerms()){
                    if(term.getTermID() == termID) currentTerm = term;
                }
                numCourses = 0;
                for (EntityCourses entityCourses: repository.getAllCourses()){
                    if(entityCourses.getTermID() == termID) ++numCourses;
                }
                if(numCourses == 0){
                    repository.delete(currentTerm);
                    Toast.makeText(DetailedTermActivity.this, currentTerm.getTermTitle() + " was deleted. Please refresh page.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DetailedTermActivity.this, "Can Not Delete Term with Courses", Toast.LENGTH_LONG).show();
                }
                finish();
                return true;
            case R.id.refreshList:
                RecyclerView recyclerView=findViewById(R.id.courseUnderTermRecyclerView);
                final AdapterCourse adapterCourse = new AdapterCourse(this);
                recyclerView.setAdapter(adapterCourse);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<EntityCourses> filteredCourses = new ArrayList<>();
                for (EntityCourses c : repository.getAllCourses()){
                    if(c.getTermID() == termID) filteredCourses.add(c);
                }
                adapterCourse.setCourses(filteredCourses);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

