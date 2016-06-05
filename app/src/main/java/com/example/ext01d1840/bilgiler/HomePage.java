package com.example.ext01d1840.bilgiler;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    SQLiteDatabase db = null;
    Integer person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = openOrCreateDatabase("yesil", MODE_PRIVATE, null);
        setContentView(R.layout.activity_home_page);

        Bundle extras = getIntent().getExtras();
        person_id = extras.getInt("person_id");

        String selectQuery = "Select first_name,last_name from fnd_user where _id = "+person_id;
        Cursor cp = db.rawQuery(selectQuery,null);
        cp.move(1);
        String first_name = cp.getString(cp.getColumnIndex("first_name")).toUpperCase();
        String last_name = cp.getString(cp.getColumnIndex("last_name")).toUpperCase();


        TextView tvHFirstName = (TextView) findViewById(R.id.tvHfistName) ;
        TextView tvHLastName = (TextView) findViewById(R.id.tvHlastName) ;

        tvHFirstName.setText(first_name);
        tvHLastName.setText(last_name);

    }

    public void educationOnClick(View view){

        Intent newEducation = new Intent(this,Education.class);
        newEducation.putExtra("person_id",person_id);
        startActivity(newEducation);

    }

    public void powerOnClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
