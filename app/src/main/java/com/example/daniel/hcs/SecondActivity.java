package com.example.daniel.hcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.hcs.utils.CustomAdapter;
import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Pill;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends Activity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    ListView listView;
    Button bAddPill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.tvFirstPill);
        listView = findViewById(R.id.lvAllPills);

        databaseHelper = DatabaseHelper.getInstance(this);
        //List<Pill> pills = databaseHelper.getAllPills();
        //Log.e("Mein pills", String.valueOf(pills));
        //textView.setText(String.valueOf(pills.get(0).getId()) + String.valueOf(pills.get(0).getName()));
        bAddPill = findViewById(R.id.bAddPill);
        bAddPill.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent explicitIntent = new Intent();
        explicitIntent.setClass(getApplicationContext(), AddPill.class);
        this.startActivity(explicitIntent);
    }

//    @Override
//    protected void onResume() {
//        CustomAdapter adapter = (CustomAdapter) listView.getAdapter();
//        List<Pill> pillList;
//        pillList = databaseHelper.getAllPills();
//        for (int i = 0; i < pillList.size(); i++){
//            Log.e("Pill", String.valueOf(pillList.get(i)));
//        }
//
//        super.onResume();
//    }

    @Override
    protected void onRestart() {
        CustomAdapter adapter = (CustomAdapter) listView.getAdapter();
        List<Pill> pillList;
        pillList = databaseHelper.getAllPills();
        for (int i = 0; i < pillList.size(); i++){
            Log.e("Pill", String.valueOf(pillList.get(i)));
        }
        super.onRestart();
    }
}
