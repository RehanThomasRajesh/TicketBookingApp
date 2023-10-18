package com.example.ticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdminTheatreShow extends AppCompatActivity {
    ListView theatre;
    DatabaseRegister sqlite;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_theatre_show);
        sqlite=new DatabaseRegister(this);
        theatre=(ListView) findViewById(R.id.theatre_list);
        list=sqlite.getTheatreList().toArray(new String[0]);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        theatre.setAdapter(adapter);
        theatre.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String dpt = list[position];
                Intent intent = new Intent(getApplicationContext(), AdminTheaterProfile.class);
                intent.putExtra("message", dpt);
                startActivity(intent);

            }
        });

    }
}