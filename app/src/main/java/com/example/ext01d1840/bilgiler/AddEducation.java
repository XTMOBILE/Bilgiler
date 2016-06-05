package com.example.ext01d1840.bilgiler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class AddEducation extends AppCompatActivity   {

    SQLiteDatabase db = null;
    Integer person_id;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    int year_x,month_x,day_x;
    static final int DILOG_ID =0;
    int control;
    String selectStatus;


    EditText start_date;
    EditText end_date;
    EditText schoolName;
    EditText schoolDivision;
    ImageView btn;
    ImageView btnEnd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_education);

        db = openOrCreateDatabase("yesil", MODE_PRIVATE, null);
        Bundle extras = getIntent().getExtras();
        person_id = extras.getInt("person_id");

        spinner = (Spinner) findViewById(R.id.spnStatus);
        adapter = ArrayAdapter.createFromResource(this,R.array.status,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 selectStatus =  spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        showDialogOnButtonClick();
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
    }
    public void cancelOnClick(View view){

        Intent intent = new Intent(this,Education.class);
        intent.putExtra("person_id",person_id);
        startActivity(intent);
        //startActivity(new Intent(getApplicationContext(),Education.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void kaydetOnClick(View view){


        db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);

        schoolName = (EditText) findViewById(R.id.etSchoolName);
        schoolDivision = (EditText)findViewById(R.id.etDivision);
        end_date = (EditText) findViewById(R.id.etEndDate);
        start_date = (EditText) findViewById(R.id.etStartDate);

        String sName = schoolName.getText().toString();
        String sDivision = schoolDivision.getText().toString();
        String sStatus =  selectStatus;
        String sStartDate = start_date.getText().toString();
        String sEndDate =end_date.getText().toString();

        Integer xName = schoolName.length();
        Integer xDivision =  schoolDivision.length();
        Integer xStartDate = start_date.length();
        Integer xEndDate  = end_date.length();

        if (xName == 0 || xDivision ==0 || xStartDate == 0)
        {
            Toast.makeText(this,"Zorunlu alanlari doldurmaniz gerekmektedir",Toast.LENGTH_SHORT).show();
        }
        else if(sStatus == "Seçiniz" ){

            Toast.makeText(this,"Durum alani secmeniz gerekmektedir",Toast.LENGTH_SHORT).show();
        }
        else {

            String durum = addEducation(person_id,sName,sDivision,sStatus,sStartDate,sEndDate);
            Toast.makeText(this," "+durum,Toast.LENGTH_SHORT).show();
        }

    }

    private String addEducation(Integer person_id, String sName, String sDivision, String sStatus, String sStartDate, String sEndDate) {

        String durum;
        try {

            db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);
            String createEdu = "create table if not exists per_educations (id integer primary key autoincrement,person_id integer,";
            createEdu += "start_date text,end_Date text,school_name text, school_division text,status text )";
            db.execSQL(createEdu);

            String insertQuery = "insert into per_educations(person_id,start_date,end_date,school_name,school_division,status)";
            insertQuery +="values ('"+person_id+"','"+sStartDate+"','"+sEndDate+"','"+sName+"','"+sDivision+"','"+sStatus+"')";
            db.execSQL(insertQuery);

            durum = "Kayit basari ile eklendi";

            Intent eduIntent = new Intent(this,Education.class);
            eduIntent.putExtra("person_id",person_id);
            startActivity(eduIntent);

        }catch (Exception e){

            durum =  "Kayit ekleme basarisiz";
            e.printStackTrace();
            Toast.makeText(this,"hata "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return durum;
    }


    public void showDialogOnButtonClick(){

        btn = (ImageView) findViewById(R.id.cStart);
        btnEnd = (ImageView) findViewById(R.id.cEnd);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                control  = 1;
                showDialog(DILOG_ID);

            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control  = 2;
                showDialog(DILOG_ID);
            }
        });

    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DILOG_ID){
            return new DatePickerDialog(this,dpickerListner,year_x,month_x,day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;
            if (control == 1)
            {
                start_date = (EditText) findViewById(R.id.etStartDate);
                start_date.setText(year_x+"-"+month_x+"-"+day_x);
            }
            else if (control == 2)
            {
                end_date = (EditText) findViewById(R.id.etEndDate);
                end_date.setText(year_x+"-"+month_x+"-"+day_x);
            }

        }
    };



}
