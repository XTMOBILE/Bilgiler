package com.example.ext01d1840.bilgiler;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);

        String createQuery = "Create table if not exists fnd_user (_id integer primary key autoincrement, first_name  text not null,last_name text not null,";
        createQuery += "email text not null,username text not null,password text not null )";
        db.execSQL(createQuery);

        String createEdu = "create table if not exists per_educations (id integer primary key autoincrement,person_id integer,";
        createEdu += "start_date text,end_Date text,school_name text, school_division text,status text )";
        db.execSQL(createEdu);


    }

    public void loginOnClick(View view) {


        EditText cName = (EditText) findViewById(R.id.etUname);
        EditText cPass = (EditText) findViewById(R.id.etPass);
        String uName = cName.getText().toString();
        String pass = cPass.getText().toString();
        String durum = getLogin(uName,pass);
        Toast.makeText(this,""+durum,Toast.LENGTH_SHORT).show();


    }

    public long getControl(String uName,String password) {

        db = openOrCreateDatabase("yesil", MODE_PRIVATE, null);
        String selectQueryx = "Select * from fnd_user where username = '" + uName + "' and password = '" + password + "'";
        long control = 0;

        try {

            SQLiteStatement ss = db.compileStatement(selectQueryx);
            control = ss.simpleQueryForLong();
            return control;

        }catch (Exception e){
            e.printStackTrace();
            control = 0;
        }
        db.close();

        return control;

    }
    public String getLogin(String uName,String password){

        long sayi = getControl(uName,password);
        String durum = null;
        if(sayi != 0 ){

            durum = "Hosgeldin";
            Intent newHome = new Intent(this,HomePage.class);

            db = openOrCreateDatabase("yesil", MODE_PRIVATE, null);
            String selectQueryx = "Select _id from fnd_user where username = '" + uName + "' and password = '" + password + "'";
            Cursor cp = db.rawQuery(selectQueryx,null);
            cp.move(1);
            int person_id = cp.getInt(cp.getColumnIndex("_id"));
            newHome.putExtra("person_id",person_id);
            startActivity(newHome);


        }else{

            durum = "Girmis oldugunuz kullanici adi / sifre gecersiz";
        }

        return durum;

    }

    public void newAccountOnClick(View view){

        Intent newAccount = new Intent(this,uyeKaydi.class);
        startActivity(newAccount);
    }


}
