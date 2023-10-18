package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class TheaterRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText tname;
    Spinner tplace;
    EditText temail;
    EditText tpass;
    EditText tcpass;
    Button tregister;
    Button treset;
    String tnme,tplce,tmail,tpassw,tcpassw,tphone;
    DatabaseRegister sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_register);
        sqlite=new DatabaseRegister(this);
        tname=(EditText)findViewById(R.id.etTheaterName);
        temail=(EditText)findViewById(R.id.etTEmail);
        tpass=(EditText)findViewById(R.id.etTPassword);
        tcpass=(EditText)findViewById(R.id.etTConfirmpass);
        tregister=(Button) findViewById(R.id.btntRegister);
        treset=(Button)findViewById(R.id.btntReset);
        sqlite=new DatabaseRegister(this);
        tplace=(Spinner)findViewById(R.id.spPlace);
        tplace.setOnItemSelectedListener(this);
        prepareData();

        tregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tnme = tname.getText().toString();
                tplce = tplace.getSelectedItem().toString();
                tmail = temail.getText().toString();
                tpassw = tpass.getText().toString();
                tcpassw = tcpass.getText().toString();
                tphone = "";

                String regExpn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                final String PASSWORD_PATTERN = "^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$";

                if (TextUtils.isEmpty(tnme))
                    tname.setError("Please fill this Field");
                    //if (TextUtils.isEmpty(tplce))
                    // tplace.setError("Please fill this Field");
                else if (TextUtils.isEmpty(tmail))
                    temail.setError("Please fill this Field");
                else  if (TextUtils.isEmpty(tpassw))
                    tpass.setError("Please fill this Field");
                else  if (TextUtils.isEmpty(tcpassw))
                    tcpass.setError("Please fill this Field");

                else if (!temail.getText().toString().trim().matches(regExpn)) {
                    temail.setError("Invalid email");
                    temail.requestFocus();
                }
                else  if (!tpassw.matches(PASSWORD_PATTERN)) {
                    tpass.setError("* must contain one digit from 0-9, one lowercase letter, one uppercase letter, " +
                            "& length should be minimum of 4  and maximum of 10");
                    tpass.requestFocus();
                    return;
                }
                else if (!tpassw.equals(tcpassw)) {
                    tcpass.setError("Passwords Does Not Match!!!");
                    tcpass.requestFocus();
                }
                else
                {
                    Boolean checkemail1 = sqlite.checkemail1(tmail);
                    if (checkemail1 == true) {
                        boolean isInserted = sqlite.insertData1(tnme, tmail, tpassw, tphone,tplce);
                        if (isInserted == true) {
                            Toast.makeText(getApplicationContext(), "Successfully created  account", Toast.LENGTH_LONG).show();
                            // User user = new User(tmail);

                            //storing the user in shared preferences
                            // SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            //finish();
                            // startActivity(new Intent(getApplicationContext(),TheaterActivity.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "This Email id already exists", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        treset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tname.setText("");
                temail.setText("");
                tpass.setText("");
                tcpass.setText("");
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }
    public void prepareData()
    {
        List<String> labels=sqlite.getPlace();
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tplace.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent,View view, int position,long id)
    {
        String label=parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO Auto-generated method stub
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
}