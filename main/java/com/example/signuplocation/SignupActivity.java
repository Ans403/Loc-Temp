package com.example.signuplocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    final static String Database_Name = "locationTempDB";
    SQLiteDatabase myDatabase;
    EditText name, email, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        myDatabase = openOrCreateDatabase(Database_Name, MODE_PRIVATE, null);
        createDBTable();
         name = (EditText) findViewById(R.id.signName);
         email = (EditText) findViewById(R.id.signEmail);
         location = (EditText) findViewById(R.id.location);
        Button signUp = (Button) findViewById(R.id.signUpbtn);
        TextView registered = (TextView) findViewById(R.id.registeredText);



        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

    }

    private void insertData() {

        final String NameStr = name.getText().toString().trim();
        final String EmailStr = email.getText().toString().trim();
        final String LocationStr = location.getText().toString().trim();

        if(NameStr.isEmpty()){
            name.setError("Please enter your name");
            name.requestFocus();
            return;
        }

        if(EmailStr.isEmpty()){
            email.setError("Please enter Email address");
            email.requestFocus();
            return;
        }
        if(LocationStr.isEmpty()){
            location.setError("Please enter location");
            location.requestFocus();
            return;
        }

        String emailCheck;
        String sql_q  = "select Email from info ";
        Cursor cursor =  myDatabase.rawQuery(sql_q,null);
        if(cursor.moveToFirst())
        {
          emailCheck = cursor.getString(0);

          if(emailCheck.equals(EmailStr))
          {
              Toast.makeText(this,"This Email has already Registered",Toast.LENGTH_SHORT).show();
              email.setError("Please enter other email address");
              email.requestFocus();
              return;

          }


        }while(cursor.moveToNext());


        String sql_query = "INSERT INTO info(Name, Email, Location) VALUES (?,?,?) ";

        myDatabase.execSQL(sql_query,new String[]{NameStr,EmailStr,LocationStr});

        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
        name.setText("");
        email.setText("");
        location.setText("");

        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);


    }

    private void createDBTable() {

        String sql_query = "CREATE TABLE IF NOT EXISTS info (\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    Name varchar(50) NOT NULL,\n" +
                "    Email varchar(50) NOT NULL,\n" +
                "    Location varchar(50) Not null \n" +
                "); ";

        myDatabase.execSQL(sql_query);
    }
}