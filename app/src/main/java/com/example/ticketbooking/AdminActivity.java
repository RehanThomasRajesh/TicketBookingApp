package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AdminActivity extends AppCompatActivity {
    GridLayout admingrid;
    ImageButton imageButton,alogout;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        admingrid = (GridLayout) findViewById(R.id.adminGrid);
        imageButton=(ImageButton)findViewById(R.id.adminlogout);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
        setSingleEvent(admingrid);


    }

    public void setSingleEvent(GridLayout singleEvent)
    {
        int i;
        for (i = 0; i < admingrid.getChildCount(); i++)
        {
            LinearLayout cont =(LinearLayout) admingrid.getChildAt(i);
            final int fin=i;
            if(fin==0) {
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       startActivity(new Intent(getApplicationContext(),PlaceActivity.class));
                    }

                });
            }
            else if (fin==1)
            {
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
                    }

                });

            }
            else if (fin==2)
            {
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),TheaterRegister.class));
                    }

                });
            }
            else if (fin==3)
            {
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),MovieActivity.class));
                    }

                });
            }
            else if (fin==4)
            {
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),AdminTheatreShow.class));
                    }

                });
            }

            else
            {
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(getApplicationContext(),DeleteMovie.class));
                    }

                });
            }
        }
    }
}