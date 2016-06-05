package com.example.ext01d1840.bilgiler;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Education extends AppCompatActivity {

    SQLiteDatabase db = null;
    Integer person_id;
    public ArrayList<String> myArrayList = new ArrayList<String>();
    ListView lv;

    String[] names;
    String[] divisions;
    String[] statuss;
    String[] startDates;
    String[] endDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        db = openOrCreateDatabase("yesil", MODE_PRIVATE, null);
        Bundle extras = getIntent().getExtras();
        person_id = extras.getInt("person_id");

        /*
        ListView lstArsiv = (ListView)findViewById(R.id.lstEducation);
        ArrayList<String> dbArray = myArrayListToDatabase();
        ListAdapter myListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dbArray);
        lstArsiv.setAdapter(myListAdapter);
        */
        lv = (ListView) findViewById(R.id.lstEducation);
        myArrayListToDatabase();
        Adaptor adapter =new Adaptor(this,names,divisions,statuss,startDates,endDates);
        lv.setAdapter(adapter);

    }
    public void myArrayListToDatabase()
    {
        db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);
        String selectQuery = "Select * from per_educations where person_id = "+person_id;
        Cursor c = db.rawQuery(selectQuery,null);
        c.moveToFirst();
        String dbString ="";
        int  i =1;

        names =new String[100];
        divisions = new String[100];
        statuss =new String[100];
        startDates = new String[100];
        endDates = new String[100];

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("school_name"))!=null){


                names[i] = c.getString(c.getColumnIndex("school_name"));
                divisions[i] = c.getString(c.getColumnIndex("school_division"));
                statuss[i] = c.getString(c.getColumnIndex("status"));
                startDates[i] = c.getString(c.getColumnIndex("start_date"));
                endDates[i] = c.getString(c.getColumnIndex("end_Date"));

               i +=1;
            }
            c.moveToNext();
        }
        db.close();

    }

    /*public ArrayList<String> myArrayListToDatabase()
    {
        db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);
        String selectQuery = "Select * from per_educations where person_id = "+person_id;
        Cursor c = db.rawQuery(selectQuery,null);
        c.moveToFirst();
        String dbString ="";

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("school_name"))!=null){

                dbString += c.getString(c.getColumnIndex("school_name"));
                dbString += "\n";

                String school_name =c.getString(c.getColumnIndex("school_name"));
                String school_division = c.getString(c.getColumnIndex("school_division"));
                String start_date = c.getString(c.getColumnIndex("start_date"));

                myArrayList.add(school_name+"-"+school_division+"-"+start_date);
            }
            c.moveToNext();
        }
        db.close();
        return  myArrayList;
    }*/



    public void addEducationOnClick(View view){

        Intent addEdu = new Intent(this,AddEducation.class);
        addEdu.putExtra("person_id",person_id);
        startActivity(addEdu);


    }

    public void eduGoOnclick(View view){
        Intent newEducation = new Intent(this,Education.class);
        newEducation.putExtra("person_id",person_id);
        startActivity(newEducation);

    }

    public void homeGoOnClick(View view){
        Intent main = new Intent(this,HomePage.class);
        main.putExtra("person_id",person_id);
        startActivity(main);

    }
}
