package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.ViewModel.StatusOfCourse;
import com.example.c196.entities.EntityCourses;
import com.example.c196.entities.EntityTerm;
import com.example.c196.utilities.Repository;
import com.example.c196.ViewModel.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddCourseActivity extends AppCompatActivity {

    Repository repository;

    int courseID;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;

    Spinner editStatus;
    private ArrayAdapter<StatusOfCourse> courseStatusAdapter;

    Spinner editTermID;
    ArrayAdapter<TermViewModel> termAdapter;

    private int id;

    boolean isValid;

    DatePickerDialog.OnDateSetListener startCourseDate;
    DatePickerDialog.OnDateSetListener endCourseDate;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        repository = new Repository(getApplication());

        editTitle = findViewById(R.id.edit_detailed_course_title);
        editStart = findViewById(R.id.edit_detailed_course_start_date);
        editEnd = findViewById(R.id.edit_detailed_course_end_date);
        editInstructorName = findViewById(R.id.edit_detailed_course_instructor_name);
        editInstructorPhone = findViewById(R.id.edit_detailed_course_instructor_phone);
        editInstructorEmail = findViewById(R.id.edit_detailed_course_instructor_email);

        Bundle b = getIntent().getExtras();

        //courseStatusSpinner
        PopulateStatusSpinner();
        //termIDSpinner
        populateTermSpinner();

        courseID = getIntent().getIntExtra("courseID", -1);
        editTitle.setText(getIntent().getStringExtra("courseTitle"));
        editStart.setText(getIntent().getStringExtra("courseStartDate"));
        editEnd.setText(getIntent().getStringExtra("courseEndDate"));
        editInstructorName.setText(getIntent().getStringExtra("courseInstructorName"));
        editInstructorPhone.setText(getIntent().getStringExtra("courseInstructorPhone"));
        editInstructorEmail.setText(getIntent().getStringExtra("courseInstructorEmail"));
        if (getIntent().getStringExtra("courseStatus") != null){
            setStatus(StatusOfCourse.valueOf(getIntent().getStringExtra("courseStatus")));
        }
        id = getIntent().getIntExtra("termID", -1);
        editTermID.setSelection(indexInTermSpinner(id));
        if(id != -1) {
            b.getInt("termID");
        }

        if(b!= null && b.getString("editStart") != null){
           editStart.setText(b.getString("editStart"));
        }

        if(b!=null && b.getString("editEnd") != null){
            editEnd.setText(b.getString("editEnd"));
        }

        //start date picker
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startInfo = editStart.getText().toString();
                try{
                    myCalendar.setTime(sdf.parse(startInfo));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourseActivity.this, startCourseDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startCourseDate = new DatePickerDialog.OnDateSetListener() {
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
                String endInfo = editEnd.getText().toString();
                try{
                    myCalendar.setTime(sdf.parse(endInfo));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourseActivity.this, endCourseDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endCourseDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

    }
    public static boolean isDateCorrect(String startDate, String endDate){
        try
        {
            String myFormat= "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            Date endD = sdf.parse(endDate);
            Date startD = sdf.parse(startDate);
            if(endD.after(startD))
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;

        }
    }
    private void updateLabelStart() {
        editStart.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabelEnd() {
        editEnd.setText(sdf.format(myCalendar.getTime()));
    }

    // Course Status Spinner
    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private void setStatus(StatusOfCourse value){
        editStatus.setSelection(getIndexInSpinner(editStatus, value.toString()));
    }

    private void PopulateStatusSpinner(){
        editStatus = findViewById(R.id.courseSpinner);
        courseStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, StatusOfCourse.values());
        editStatus.setAdapter(courseStatusAdapter);
    }

    private StatusOfCourse getSpinnerValue() {
        return (StatusOfCourse) editStatus.getSelectedItem();
    }

    //TermID spinner
    private void populateTermSpinner(){
        editTermID = findViewById(R.id.termIDSpinner);
        List<TermViewModel> spinnerTermTitle =  getTermViewModel();
        termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerTermTitle);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTermID.setAdapter(termAdapter);
    }

    private List<TermViewModel> getTermViewModel(){
        List<EntityTerm> termTitle = repository.getAllTerms();
        List<TermViewModel> o = new ArrayList<>();
        for(int i=0; i< termTitle.size(); i++){
            o.add(new TermViewModel(termTitle.get(i)));
        }
        return o;
    }
    private int indexInTermSpinner(int termID){
        for(int i=0; i<editTermID.getCount(); i++){
            TermViewModel item = (TermViewModel) editTermID.getItemAtPosition(i);
            if(item.id == termID){
                return i;
            }
        }
        return -1;
    }

    private int SpinnerTermIDValue(){
        TermViewModel item = (TermViewModel) editTermID.getSelectedItem();
        return item.id;
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }


    public void saveCourseButton(View view){
        EntityCourses entityCourses;
        if(isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false|| editTitle.getText().toString().trim().isEmpty()
                || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty()
                || editInstructorName.getText().toString().trim().isEmpty() || editInstructorPhone.getText().toString().trim().isEmpty()
                || editInstructorEmail.getText().toString().trim().isEmpty() ){
            Toast.makeText(AddCourseActivity.this, "Make sure all fields are filled and start date must be before end date. Course not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(courseID == -1){
            int newID = repository.getAllCourses().get(repository.getAllCourses().size()-1).getCourseID()+1;
            entityCourses = new EntityCourses(newID,
                    SpinnerTermIDValue(),
                    editTitle.getText().toString(),
                    editStart.getText().toString(),
                    editEnd.getText().toString(),
                    getSpinnerValue(),
                    editInstructorName.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editInstructorEmail.getText().toString());
            repository.insert(entityCourses);
            isValid = true;
        }else{
            entityCourses = new EntityCourses(courseID,
                    SpinnerTermIDValue(),
                    editTitle.getText().toString(),
                    editStart.getText().toString(),
                    editEnd.getText().toString(),
                    getSpinnerValue(),
                    editInstructorName.getText().toString(),
                    editInstructorPhone.getText().toString(),
                    editInstructorEmail.getText().toString());
            repository.update(entityCourses);
            isValid = true;
        }
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveItem:
                EntityCourses entityCourses;
                if(isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false|| editTitle.getText().toString().trim().isEmpty()
                        || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty()
                        || editInstructorName.getText().toString().trim().isEmpty() || editInstructorPhone.getText().toString().trim().isEmpty()
                        || editInstructorEmail.getText().toString().trim().isEmpty() ){
                    Toast.makeText(AddCourseActivity.this, "Make sure all fields are filled and start date must be before end date. Course not saved.", Toast.LENGTH_LONG).show();
                    return false;
                }
                if(courseID == -1){
                    int newID = repository.getAllCourses().get(repository.getAllCourses().size()-1).getCourseID()+1;
                    entityCourses = new EntityCourses(newID,
                            SpinnerTermIDValue(),
                            editTitle.getText().toString(),
                            editStart.getText().toString(),
                            editEnd.getText().toString(),
                            getSpinnerValue(),
                            editInstructorName.getText().toString(),
                            editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString());
                    repository.insert(entityCourses);
                }else{
                    entityCourses = new EntityCourses(courseID,
                            SpinnerTermIDValue(),
                            editTitle.getText().toString(),
                            editStart.getText().toString(),
                            editEnd.getText().toString(),
                            getSpinnerValue(),
                            editInstructorName.getText().toString(),
                            editInstructorPhone.getText().toString(),
                            editInstructorEmail.getText().toString());
                    repository.update(entityCourses);
                }
                finish();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }

}