package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {
    Button b1;
    EditText t1;
    String cat1;
    DatabaseRegister sqLite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        b1=(Button)findViewById(R.id.btnCat);
        t1=(EditText)findViewById(R.id.etCat);
        sqLite=new DatabaseRegister(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cat1=t1.getText().toString();
                if(TextUtils.isEmpty(cat1)) {
                    t1.setError("Please fill this Field");
                }
                else {
                    boolean chckcat = sqLite.checkCategory(cat1);
                    Toast.makeText(CategoryActivity.this, "haii", Toast.LENGTH_SHORT).show();
                    if (chckcat == true) {
                        boolean isInserted = sqLite.insertCat(cat1);
                        if (isInserted == true) {
                            Toast.makeText(getApplicationContext(), "Category added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CategoryActivity.this, "Not Added", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CategoryActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}