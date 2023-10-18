package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login,viewall;
    AlertDialog.Builder alertbox;
    EditText username;
    EditText password;
    DatabaseRegister sqLite;
    String getusernme,getpasswrd;
    TextView click,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertbox=new AlertDialog.Builder(this);
        sqLite=new DatabaseRegister(this);
        login=(Button)findViewById(R.id.btnLogin);
        username=(EditText)findViewById(R.id.etUsername);
        password=(EditText)findViewById(R.id.etPassword);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            User user = SharedPrefManager.getInstance(this).getUser();
            Boolean c1=sqLite.checkemail(user.getemail());
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

        signup=(TextView)findViewById(R.id.tvnewuser);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Signup",Toast.LENGTH_LONG).show();
               Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getusernme = username.getText().toString();
                getpasswrd = password.getText().toString();
                if (TextUtils.isEmpty(getusernme)) {
                    username.setError("Enter your email");
                } else if (TextUtils.isEmpty(getpasswrd)) {

                    password.setError("Enter your password");
                }
                else {
                    Boolean check1 = sqLite.checkemail(getusernme);
                   if (check1.equals(false)) {
                        Boolean checkemail = sqLite.emailpassword(getusernme,getpasswrd);
                        if (checkemail == true) {
                            User user=new User(getusernme);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            if(user.getemail().equals("admin@gmail.com")) {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),AdminActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                                // in.putExtra("email",getusernme);
                                startActivity(in);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                        }
                    }
                   else {
                        Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        click=(TextView)findViewById(R.id.tvforgot);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Toast.makeText(getApplicationContext(),"ForgotEmail",Toast.LENGTH_LONG).show();
        /*        Intent intent=new Intent(getApplicationContext(),ForgotEmail.class);
               startActivity(intent);*/
            }
        });

        viewall=(Button)findViewById(R.id.viewall);
        alertbox=new AlertDialog.Builder(this);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = sqLite.getAllData();
                if(res.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append("ID: "+res.getString(0)+"\n");
                        buffer.append("FirstName: "+res.getString(1)+"\n");
                        buffer.append("LastnameName: "+res.getString(2)+"\n");
                        buffer.append("Email: "+res.getString(3)+"\n");
                        buffer.append("Contact: "+res.getString(4)+"\n");
                        buffer.append("Password: "+res.getString(5)+"\n");
                        buffer.append("Type: "+res.getString(6)+"\n\n");
                    }
                    alertbox.setCancelable(true);
                    alertbox.setTitle("Customers");
                    alertbox.setMessage(buffer.toString());
                    alertbox.show();
                }
            }
        });
    }

}