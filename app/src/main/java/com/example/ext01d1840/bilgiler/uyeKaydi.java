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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class uyeKaydi extends AppCompatActivity {

    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_kaydi);

        /*db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);*/

    }


    public void insertOnclick(View view){

        db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);

        EditText etFName = (EditText) findViewById(R.id.etAd);
        EditText etLName = (EditText) findViewById(R.id.etSoyad);
        EditText etMail = (EditText) findViewById(R.id.etMail);
        EditText etUserName = (EditText) findViewById(R.id.etKadi);
        EditText etSifre1 = (EditText) findViewById(R.id.etSifre1);
        EditText etSifre2 = (EditText) findViewById(R.id.etSifre2);

        String fName = etFName.getText().toString();
        String lName = etLName.getText().toString();
        String email = etMail.getText().toString();
        String username = etUserName.getText().toString();
        String sifre1 = etSifre1.getText().toString();
        String sifre2 = etSifre2.getText().toString();

        Integer xFname = etFName.length();
        Integer xLname = etLName.length();
        Integer xemail = etMail.length();
        Integer xusername = etUserName.length();
        Integer xsifre1 = etSifre1.length();
        Integer xsifre2 = etSifre2.length();



        if (xFname == 0 || xLname == 0 ||xemail ==0 || xusername == 0 || xsifre1 == 0 || xsifre2 == 0 )
        {
            Toast.makeText(this,"Butun alanlarin doldurulmasi zorunludur.",Toast.LENGTH_SHORT).show();
        }
        /*else if (sifre1 != sifre2){


            Toast.makeText(this,"Girilen sifreler ayni olmali",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Sifre1 : "+sifre1+ " sifre2 : "+sifre2,Toast.LENGTH_SHORT).show();

        }*/
        else {
            long count = 0;
            try {

                try {
                    db = openOrCreateDatabase("yesil", MODE_PRIVATE, null);
                    String createQuery = "Create table if not exists fnd_user (_id integer primary key autoincrement, first_name  text not null,last_name text not null,";
                    createQuery += "email text not null,username text not null,password text not null )";
                    db.execSQL(createQuery);
                    String selectQuery = "Select * from fnd_user where username = '" + username + "'";
                    SQLiteStatement s = db.compileStatement(selectQuery);
                    count = s.simpleQueryForLong();
                    }catch (Exception e){

                    count =0;
                }


                if (count == 0){

                    String durum = addUser(fName,lName,email,username,sifre1);

                    startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    /*
                    Intent main_activity = new Intent(this,MainActivity.class);
                    startActivity(main_activity);*/

                }
                else{
                    Toast.makeText(this,"Girmis oldugunuz kullanici adi kullanilmaktadir",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){

                e.printStackTrace();
                Toast.makeText(this,"ilk where de hata "+e.getMessage(),Toast.LENGTH_SHORT).show();

            }


        }

    }

    public String addUser(String fname,String lname,String email,String username,String sifre){


        String durum = "Kayit basari ile eklendi";


        try {
            db = openOrCreateDatabase("yesil",MODE_PRIVATE,null);
            String insertQuery = "insert into fnd_user(first_name,last_name,email,username,password) values ('"+fname+"','"+lname+"','"+email+"','"+username+"','"+sifre+"')";
            db.execSQL(insertQuery);

            durum = "Kayit basari ile eklendi";
        }catch (Exception e){
            durum =  "Kayit ekleme basarisiz";
            e.printStackTrace();
            Toast.makeText(this,"hata "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        return durum;
    }
}
