package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner category;
    DatabaseRegister sqLite;
    EditText mname;
    Spinner lang;
    EditText descrip;
    EditText cast;
    Button mregister;
    Button mreset;
    ImageView pic;
    String mnme,lng,des,cst,catgry;
    Bitmap bp;
    byte[] photo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        sqLite=new DatabaseRegister(this);
        mname=(EditText)findViewById(R.id.etMvName);
        lang=(Spinner)findViewById(R.id.spLang);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lang_Array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang.setAdapter(adapter);
        descrip=(EditText)findViewById(R.id.etDescription);
        cast=(EditText)findViewById(R.id.etCast);
        mregister=(Button)findViewById(R.id.btnmRegister);
        mreset=(Button)findViewById(R.id.btnmReset);
        pic=(ImageView)findViewById(R.id.imPoster);
        category=(Spinner)findViewById(R.id.spCat);
        category.setOnItemSelectedListener(this);
        prepareData();
        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnme=mname.getText().toString();
                lng=lang.getSelectedItem().toString();
                catgry=category.getSelectedItem().toString();
                cst=cast.getText().toString();
                des=descrip.getText().toString();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.PNG,0,bos);
                photo=bos.toByteArray();
                if (TextUtils.isEmpty(mnme))
                    mname.setError("Please fill this Field");
                if (TextUtils.isEmpty(cst))
                    cast.setError("Please fill this Field");
                if (TextUtils.isEmpty(des))
                    descrip.setError("Please fill this Field");
              /* if(mname.getText().toString()=="" || cast.getText().toString()=="" || descrip.getText().toString()=="")
               {
                   Toast.makeText(MovieActivity.this, "Fields can be empty", Toast.LENGTH_SHORT).show();
               }
               else {*/
                boolean chckmv = sqLite.checkMovie(mnme);
                if (chckmv == true) {
                    boolean insertmv = sqLite.insertMovie(catgry, mnme, lng, des, cst, photo);
                    if (insertmv == true) {
                        Toast.makeText(MovieActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MovieActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MovieActivity.this, "Movie already exists", Toast.LENGTH_SHORT).show();
                }
                //}
            }
        });
        mreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mname.setText("");
                descrip.setText("");
                cast.setText("");
                pic.setImageResource(R.drawable.ic_image_black_24dp);
            }
        });
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!=getPackageManager().PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA},5);
            }
        }
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,2);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 2:
                if(resultCode==RESULT_OK)
                {
                    Uri choosenImage=data.getData();
                    if(choosenImage!=null)
                    {
                        bp=decodeUri(choosenImage,400);
                        pic.setImageBitmap(bp);
                    }

                }
        }
    }

    protected Bitmap decodeUri(Uri choosenImage, int i)
    {
        try {
            BitmapFactory.Options o=new BitmapFactory.Options();
            o.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(choosenImage),null,o);
            int width_tmp=o.outWidth,height_tmp=o.outHeight ;
            int scale=1;
            while (true)
            {
                if(width_tmp/2<i || height_tmp/2<i)
                {
                    break;
                }
                width_tmp/=2;
                height_tmp/=2;
                scale *=2;
            }
            BitmapFactory.Options o2=new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return  BitmapFactory.decodeStream(getContentResolver().openInputStream(choosenImage),null,o2);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }
    public void prepareData()
    {
        List<String> labels=sqLite.getCategory();
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
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