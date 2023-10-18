package com.example.ticketbooking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminTheaterProfile extends AppCompatActivity {
    EditText t1,t2,t3,t4,t5,t6;
    Button delte;
    DatabaseRegister sqLite;
    String s1,s2,s3,s4,s5,mail,s6,s7,s8,s9,s10,s11;
    String tname;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_theater_profile);
        sqLite=new DatabaseRegister(this);
        t1=(EditText)findViewById(R.id.etTheaterNameta);
        t2=(EditText)findViewById(R.id.etPlceta);
        t3=(EditText)findViewById(R.id.etTEmailta);
        t5=(EditText)findViewById(R.id.etPhoneta);
        delte=(Button)findViewById(R.id.btnDeleteTa);
        Intent intent=getIntent();
        tname=intent.getStringExtra("message");
        //  Toast.makeText(this, tname, Toast.LENGTH_SHORT).show();
        cursor=sqLite.getTheatreDetails2(tname);
        while (cursor.moveToNext()) {
            s1=cursor.getString(cursor.getColumnIndex("TNAME"));
            s2=cursor.getString(cursor.getColumnIndex("PLACE"));
            s3=cursor.getString(cursor.getColumnIndex("TEMAIL"));
            s4=cursor.getString(cursor.getColumnIndex("TPASSWORD"));
            s5=cursor.getString(cursor.getColumnIndex("TPHONE"));
        }
        t1.setText(s1);
        t2.setText(s2);
        t3.setText(s3);
        t5.setText(s5);
        delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s6=t3.getText().toString();
                boolean del=sqLite.deleteTheatre(s6);
                if(del==true)
                {
                    Toast.makeText(AdminTheaterProfile.this, "Theatre has been removed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AdminTheaterProfile.this, "Unsuccessfull", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public boolean deleteTheatre(String tmaill)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        int tid=0;
        Cursor cursor=database.rawQuery("select TID from tbltheater where TEMAIL=?",new String[]{tmaill});
        while (cursor.moveToNext())
        {
            tid=cursor.getInt(cursor.getColumnIndex("TID"));
        }
        int result=database.delete(TABLE_NAME3,"TEMAIL=?",new String[]{tmaill});
        int result1=database.delete(TABLE_NAME5,"TID=?",new String[]{String.valueOf(tid)});
        if(result>0 && result1>0)
            return true;
        else
            return false;
    }
}