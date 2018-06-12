package com.example.daniel.hcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.hcs.utils.CustomAdapter;
import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Intake;
import com.example.daniel.hcs.utils.Pill;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends Activity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private Button bAddPill;
    private List<Pill> pillList;
    private CustomAdapter pillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.tvFirstPill);
        listView = findViewById(R.id.lvAllPills);
        databaseHelper = DatabaseHelper.getInstance(this);

        pillList = databaseHelper.getAllPills();
        pillAdapter = new CustomAdapter(pillList);
        this.listView.setAdapter(pillAdapter);

        //List<Pill> pills = databaseHelper.getAllPills();
        //Log.e("Mein pills", String.valueOf(pills));
        //textView.setText(String.valueOf(pills.get(0).getId()) + String.valueOf(pills.get(0).getName()));
        bAddPill = findViewById(R.id.bAddPill);
        bAddPill.setOnClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent explicitIntent = new Intent();
        explicitIntent.setClass(getApplicationContext(), AddPill.class);
        this.startActivity(explicitIntent);
    }

    @Override
    protected void onResume() {
//        CustomAdapter adapter = (CustomAdapter) listView.getAdapter();
//        List<Pill> pillList;
//        pillList = databaseHelper.getAllPills();
//        for (int i = 0; i < pillList.size(); i++){
//            Log.e("Pill", String.valueOf(pillList.get(i)));
//        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        pillList = databaseHelper.getAllPills();
//        Log.e("Pill", String.valueOf(pillList.get(i).getServerId()));
//        Log.e("Pill", String.valueOf(pillList.get(i).getName()));
//        Log.e("Pill", String.valueOf(pillList.get(i).getDescription()));
//        Log.e("Pill", String.valueOf(pillList.get(i).getNumberOfIntakes()));
//        Log.e("Pill", "\n");
        pillAdapter.insert(pillList);
        checkTime();
        super.onRestart();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        databaseHelper.deletePill(pillList.remove(i));
        pillAdapter.insert(pillList);
        return false;
    }

//    private void managerTasks(Location mlocation) {
//        float distance;
//        for (int i = 0; i < locations.size(); i++) {
//            Location locationTaskMarker = new Location("");
//            locationTaskMarker.setLatitude(locations.get(i).latitude);
//            locationTaskMarker.setLongitude(locations.get(i).longitude);
//            distance = locationTaskMarker.distanceTo(mlocation);
//            Log.e("distance", Float.toString(distance));
//            if (distance < 50) {
//                if (!fCalled) {
////                    fCalled = true;
////                    taskBuilder(i);
//                }
//            }
//        }
//    }

    private void checkTime(){
        int i, j;
        String time;
        List<Pill> pills = databaseHelper.getAllPills();
        List<Intake> intakes = null;
        Log.e("time",String.valueOf(pills.size()));
        for (i = 0; i < pills.size(); i++)
        {
            Log.e("time",String.valueOf(i));
            intakes = databaseHelper.getIntakesFromPill(pills.get(i));
            for (j = 0; j < intakes.size(); j++)
            {
                time = intakes.get(j).getTimeOfIntake();
                Log.e("time", "Vrijeme " + j + " " + time);
            }
        }


    }
}
