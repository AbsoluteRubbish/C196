package com.example.c196.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.entities.EntityTerm;
import com.example.c196.utilities.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTermActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    int termID;

    boolean isValid;

    Repository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
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
                new DatePickerDialog(AddTermActivity.this, startDate, myCalendar
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
                new DatePickerDialog(AddTermActivity.this, endDate, myCalendar
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
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }



    public void saveTermButton(View view){
        EntityTerm entityTerm;
        if (isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false || editTitle.getText().toString().trim().isEmpty()
                || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty() ){
            Toast.makeText(AddTermActivity.this, "Make sure all fields are filled and start date is before end date. Term not saved.", Toast.LENGTH_LONG).show();
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



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.saveItem:
                EntityTerm entityTerm;
                if (isDateCorrect(editStart.getText().toString(), editEnd.getText().toString()) == false || editTitle.getText().toString().trim().isEmpty()
                        || editStart.getText().toString().trim().isEmpty() || editEnd.getText().toString().trim().isEmpty() ){
                    Toast.makeText(AddTermActivity.this, "Make sure all fields are filled and start Date is before End Date. Term not saved.", Toast.LENGTH_LONG).show();
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
        }
        return super.onOptionsItemSelected(item);
    }
}