package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
DatabaseRegister sqlite;
EditText fname,lname,email,mobile,pass,cpass;
Button register;
TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sqlite=new DatabaseRegister(this);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            User user = SharedPrefManager.getInstance(this).getUser();
            Boolean c1=sqlite.checkemail(user.getemail());
            //  startActivity(new Intent(this, HomeActivity.class));
            if(c1.equals(false))
            {

                if (user.getemail().equals("admin@gmail.com")) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                } else {
                    startActivity(new Intent(this, HomeActivity.class));
                }
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            return;
        }
        fname=(EditText)findViewById(R.id.input_fname);
        lname=(EditText)findViewById(R.id.input_lname);
        email=(EditText)findViewById(R.id.input_email);
        mobile=(EditText)findViewById(R.id.input_mobile);
        pass=(EditText)findViewById(R.id.input_password);
        cpass=(EditText)findViewById(R.id.input_reEnterPassword);
        register=(Button) findViewById(R.id.btn_signup);
        sqlite=new DatabaseRegister(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fnme=fname.getText().toString();
                String lnme=lname.getText().toString();
                String mail=email.getText().toString();
                String phone=mobile.getText().toString();
                String passw=pass.getText().toString();
                String cpassw=cpass.getText().toString();
                String type="user";
                String regExpn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                final String PASSWORD_PATTERN ="^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$";
                final String CHARAC_PATTERN="^[a-zA-Z ]+$";
                final String PHONE_PATTERN="[1-9][0-9]{9}";

                if(TextUtils.isEmpty(fnme))
                    fname.setError("Please fill this Field");
                else if(TextUtils.isEmpty(lnme))
                    lname.setError("Please fill this Field");
                else if(TextUtils.isEmpty(mail))
                    email.setError("Please fill this Field");
                else if(TextUtils.isEmpty(phone))
                    email.setError("Please fill this Field");
                else if(TextUtils.isEmpty(passw))
                    pass.setError("Please fill this Field");
                else if(TextUtils.isEmpty(cpassw))
                    cpass.setError("Please fill this Field");
                else if(!fname.getText().toString().trim().matches(CHARAC_PATTERN))
                {
                    fname.setError("Invalid");
                    fname.requestFocus();
                }
                else if(!lname.getText().toString().trim().matches(CHARAC_PATTERN))
                {
                    lname.setError("Invalid");
                    lname.requestFocus();
                }
                else if(!mobile.getText().toString().trim().matches(PHONE_PATTERN))
                {
                    mobile.setError("Invalid");
                    mobile.requestFocus();
                }
                else  if(!email.getText().toString().trim().matches(regExpn))
                {
                    email.setError("Invalid email");
                    email.requestFocus();
                }
                else if (!passw.matches(PASSWORD_PATTERN))
                {
                    pass.setError("* must contain one digit from 0-9, one lowercase letter, one uppercase letter, " +
                            "& length should be minimum of 4  and maximum of 10");
                    pass.requestFocus();
                }
                else if (!passw.equals(cpassw))
                {
                    cpass.setError("Passwords Does Not Match!!!");
                    cpass.requestFocus();
                }
                else
                {
                    boolean checkemail = sqlite.checkemail(mail);
                    if (checkemail == true) {
                           boolean isInserted = sqlite.insertData(fnme,lnme,mail,phone,passw,type);
                           if (isInserted == true)
                        {
                            User user = new User(mail);
                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            Toast.makeText(getApplicationContext(), "Successfully created your account", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(in);
                                fname.setText("");
                                lname.setText("");
                                email.setText("");
                                pass.setText("");
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "This Email id already exists", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        login=(TextView)findViewById(R.id.link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}