package com.example.daniel.hcs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Pill;

import java.util.List;

public class SecondActivity extends Activity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.tvFirstPill);

        databaseHelper = DatabaseHelper.getInstance(this);
        List<Pill> pills = databaseHelper.getAllPills();
        Log.e("Meine pills", String.valueOf(pills));
        textView.setText(String.valueOf(pills.get(0).getId()) + String.valueOf(pills.get(0).getName()));
    }
}
