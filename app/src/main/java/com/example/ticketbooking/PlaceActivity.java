package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlaceActivity extends AppCompatActivity {
    Button b1;
    EditText t1;
    String plce1;
    DatabaseRegister sqLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        b1=(Button)findViewById(R.id.btnPlce);
        t1=(EditText)findViewById(R.id.etPlce);
        sqLite=new DatabaseRegister(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plce1=t1.getText().toString();
                if(TextUtils.isEmpty(plce1)) {
                    t1.setError("Please fill this Field");
                }
                else {
                    boolean checkplce = sqLite.checkplace(plce1);
                    if (checkplce == true) {
                        boolean isInserted = sqLite.insertPlace(plce1);
                        if (isInserted == true) {
                            Toast.makeText(getApplicationContext(), "Place added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Not Added", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PlaceActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}