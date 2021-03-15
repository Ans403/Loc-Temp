package com.example.signuplocation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.signuplocation.SignupActivity.Database_Name;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDatabase;
    public static String AppId = "b91ccacf69428484615c568096da17f3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase = openOrCreateDatabase(Database_Name, MODE_PRIVATE, null);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(R.drawable.image_w);
        String Loc = getIntent().getStringExtra("Location");
        final TextView temps = (TextView) findViewById(R.id.Temp_text);
        TextView location_ = (TextView) findViewById(R.id.locationText);
        location_.setText(Loc);
        ItemInterface itemInterface = APIClient.getRetrofitInstance().create(ItemInterface.class);
        Call<Temp> call = itemInterface.getTemp(Loc,AppId);
        call.enqueue(new Callback<Temp>() {

            @Override
            public void onResponse(Call<Temp> call, Response<Temp> response) {

                if (response.isSuccessful()) {
                    Temp data = response.body();
                    Main main = data.getMain();
                    Double temp = main.getTemp();
                    int Temper = (int) (temp-273.15);
                    temps.setText((String.valueOf(Temper)+"C"));


                }
                else
                {
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Downloading Failed");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(t.getMessage());
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });



    }
}