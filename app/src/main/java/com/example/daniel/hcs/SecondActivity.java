package com.example.daniel.hcs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daniel.hcs.interfaces.RequestListener;
import com.example.daniel.hcs.utils.API;
import com.example.daniel.hcs.utils.CustomAdapter;
import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Intake;
import com.example.daniel.hcs.utils.Pill;
import com.example.daniel.hcs.utils.WakeupReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SecondActivity extends Activity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private Button bAddPill;
    private List<Pill> pillList;
    private CustomAdapter pillAdapter;
    private API api;

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
        api = API.getInstance(this);

        //List<Pill> pills = databaseHelper.getAllPills();
        //Log.e("Mein pills", String.valueOf(pills));
        //textView.setText(String.valueOf(pills.get(0).getId()) + String.valueOf(pills.get(0).getName()));
        bAddPill = findViewById(R.id.bAddPill);
        bAddPill.setOnClickListener(this);
        listView.setOnItemLongClickListener(this);
        checkTime();
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
    public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
        api.deletePill(pillList.remove(i), new RequestListener() {
            @Override
            public void failed(String message) {
                Log.e("Failed", "FAIL");
            }

            @Override
            public void finished(String message) {
                pillAdapter.notifyDataSetChanged();
            }
        });

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
        int i, j, alarmNumber = 0;
        String time;
//        String[] currentTime;
        String[] intakeTime;

//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        String test = sdf.format(cal.getTime());
//        Log.e("TEST", test);
//        currentTime = test.split(":");
//        Log.e("timeHH", currentTime[0]);
//        Log.e("timeMM", currentTime[1]);

        List<Pill> pills = databaseHelper.getAllPills();
        List<Intake> intakes;
//        Log.e("time","PILL SIZE " + String.valueOf(pills.size()));

        for (i = 0; i < pills.size(); i++)
        {
            Log.e("time", "i " + String.valueOf(i));
            intakes = databaseHelper.getIntakesFromPill(pills.get(i));
//            Log.e("PIll ID", "pill ID " + pills.get(i).getServerId());
//            Log.e("time", "INTAKE SIZE " + String.valueOf(intakes.size()));
            for (j = 0; j < intakes.size(); j++) {
                time = intakes.get(j).getTimeOfIntake();
                intakeTime = time.split(":");
//                Log.e("timeHH", intakeTime[0]);
//                Log.e("timeMM", intakeTime[1]);
//                if (currentTime[0].equals(intakeTime[0]) && currentTime[1].equals(intakeTime[1]))
//                {
//                    Log.e("time", "Uzmi tableticu u: Vrijeme " + j + " " + time);
//
//                }
                AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                Intent receiverIntent = new Intent(this, WakeupReceiver.class);
                //The second parameter is unique to this PendingIntent,
                //if you want to make more alarms,
                //make sure to change the 0 to another integer
                PendingIntent alarmIntent = PendingIntent.getBroadcast(this, alarmNumber, receiverIntent, 0);

                int hour = Integer.parseInt(intakeTime[0]);
                int minute = Integer.parseInt(intakeTime[1]);

                Calendar alarmCalendarTime = Calendar.getInstance(); //Convert to a Calendar instance to be able to get the time in milliseconds to trigger the alarm
                alarmCalendarTime.set(Calendar.HOUR_OF_DAY, hour);
                alarmCalendarTime.set(Calendar.MINUTE, minute);
                alarmCalendarTime.set(Calendar.SECOND, 0); //Must be set to 0 to start the alarm right when the minute hits 30
                alarmCalendarTime.set(Calendar.MILLISECOND, 0); //Must be set to 0 to start the alarm right when the minute hits 30

                //Add a day if alarm is set for before current time, so the alarm is triggered the next day
                if (alarmCalendarTime.before(Calendar.getInstance())) {
                    alarmCalendarTime.add(Calendar.DAY_OF_MONTH, 1);
                }

                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendarTime.getTimeInMillis(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS), alarmIntent);
                alarmNumber++;
            }
        }


    }
}
