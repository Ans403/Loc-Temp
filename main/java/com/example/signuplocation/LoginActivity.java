package com.example.signuplocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.signuplocation.SignupActivity.Database_Name;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDatabase = openOrCreateDatabase(Database_Name, MODE_PRIVATE, null);

        email = (EditText) findViewById(R.id.LoginEmail);
        Button loginBtn = (Button) findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailLocation();
            }
        });
    }

    private void EmailLocation() {

        String Email = email.getText().toString();
        String LocationDB;

        if(Email.isEmpty()){
            email.setError("Please enter your Email");
            email.requestFocus();
            return;
        }
        String sql_query = "select Location from info WHERE Email = ? ";
        Cursor cursor =  myDatabase.rawQuery(sql_query, new String[]{Email});
        if(cursor.moveToFirst())
        {
            LocationDB = cursor.getString(0);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            intent.putExtra("Location", LocationDB);
            startActivity(intent);


        }
        else
        {
            Toast.makeText(this,"No account Registered on this email",Toast.LENGTH_SHORT).show();

        }



    }
}