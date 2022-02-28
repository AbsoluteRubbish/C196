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
import com.example.c196.ViewModel.AssessmentType;
import com.example.c196.entities.EntityAssessment;
import com.example.c196.entities.EntityCourses;
import com.example.c196.ViewModel.CourseViewModel;
import com.example.c196.utilities.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAssessmentActivity extends AppCompatActivity {

    Repository repository;

    int assessmentID;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    Spinner editType;
    private ArrayAdapter<AssessmentType> assessmentTypeAdapter;

    Spinner editCourseID;
    ArrayAdapter<CourseViewModel> courseAdapter;

    private int id;
    boolean isValid;

    DatePickerDialog.OnDateSetListener startAssessmentDate;
    DatePickerDialog.OnDateSetListener endAssessmentDate;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        repository = new Repository(getApplication());



        editTitle = findViewById(R.id.edit_detailed_assessment_title);
        editStart = findViewById(R.id.edit_detailed_assessment_start_date);
        editEnd = findViewById(R.id.edit_detailed_assessment_end_date);

        Bundle b = getIntent().getExtras();

        //Assessment Type Spinner
        PopulateTypeSpinner();
        //courseIDSpinner
        populateCourseSpinner();

        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        editTitle.setText(getIntent().getStringExtra("assessmentTitle"));
        editStart.setText(getIntent().getStringExtra("assessmentStartDate"));
        editEnd.setText(getIntent().getStringExtra("assessmentEndDate"));
        if(getIntent().getStringExtra("assessmentType") != null) {
            setType(AssessmentType.valueOf(getIntent().getStringExtra("assessmentType")));
        }

        id = getIntent().getIntExtra("courseID", -1);
        editCourseID.setSelection(indexInCourseSpinner(id));

        if(id != -1){b.getInt("courseID");}

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
                String info = editStart.getText().toString();
                try{
                    myCalendar.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessmentActivity.this, startAssessmentDate,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();;
            }
        });
        startAssessmentDate = new DatePickerDialog.OnDateSetListener(){
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
                String info = editEnd.getText().toString();
                try{
                    myCalendar.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessmentActivity.this, endAssessmentDate,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();;
            }
        });
        endAssessmentDate = new DatePickerDialog.OnDateSetListener(){
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
    //calendar
    private void updateLabelStart(){
        editStart.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabelEnd(){
        editEnd.setText(sdf.format(myCalendar.getTime()));
    }


    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    //Assessment Type Spinner
    private void setType(AssessmentType value){
        editType.setSelection((getIndexInSpinner(editType, value.toString())));
    }

    private void PopulateTypeSpinner(){
        editType = findViewById(R.id.typeSpinner);
        assessmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AssessmentType.values());
        editType.setAdapter(assessmentTypeAdapter);
    }

    private AssessmentType getSpinnerValue(){
        return (AssessmentType) editType.getSelectedItem();
    }

    //CourseID Spinner
    private void populateCourseSpinner(){
        editCourseID = findViewById(R.id.courseIDSpinner);
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
        CourseViewModel item = ((CourseViewModel) editCourseID.getSelectedItem());
        return item.id;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void saveAssessmentButton(View view){
        EntityAssessment entityAssessment;
        if (isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false || editTitle.getText().toString().trim().isEmpty()
                || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty() ){
            Toast.makeText(AddAssessmentActivity.this, "Make sure all fields are filled and start date is before end date. Assessment not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(assessmentID == -1){
            int newID = repository.getAllAssessments().get(repository.getAllAssessments().size()-1).getAssessmentID()+1;
            entityAssessment = new EntityAssessment(newID,SpinnerCourseIDValue(), editTitle.getText().toString(), getSpinnerValue(), editStart.getText().toString(), editEnd.getText().toString());
            repository.insert(entityAssessment);
            isValid = true;
        }else{
            entityAssessment = new EntityAssessment(assessmentID, SpinnerCourseIDValue(), editTitle.getText().toString(), getSpinnerValue(), editStart.getText().toString(), editEnd.getText().toString());
            repository.update(entityAssessment);
            isValid = true;
        }
        finish();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveItem:
                EntityAssessment entityAssessment;
                if (isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false || editTitle.getText().toString().trim().isEmpty()
                        || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty() ){
                    Toast.makeText(AddAssessmentActivity.this, "Make sure all fields are filled and start date is before end date. Assessment not saved.", Toast.LENGTH_LONG).show();
                    return false;
                }
                if(assessmentID == -1){
                    int newID = repository.getAllAssessments().get(repository.getAllAssessments().size()-1).getAssessmentID()+1;
                    entityAssessment = new EntityAssessment(newID,SpinnerCourseIDValue(), editTitle.getText().toString(), getSpinnerValue(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(entityAssessment);
                }else{
                    entityAssessment = new EntityAssessment(assessmentID, SpinnerCourseIDValue(), editTitle.getText().toString(), getSpinnerValue(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(entityAssessment);
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}