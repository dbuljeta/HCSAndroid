package com.example.daniel.hcs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Intake;
import com.example.daniel.hcs.utils.Pill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NotificationActivity extends Activity implements View.OnClickListener {

    private Button bYes, bNo;
    private TextView tvDescription;

    private List<Pill> pillList;
    private List<Intake> intakeList;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Calendar cal = Calendar.getInstance();
        bYes = findViewById(R.id.bYes);
        bNo = findViewById(R.id.bNo);
        databaseHelper = DatabaseHelper.getInstance(this);

        tvDescription = findViewById(R.id.tvDesc);
        pillList = databaseHelper.getAllPills();
        String[] currentTime;
        String[] pillTime;
        String intakeTime;
        int h, m, hPill, mPill;
        for (int i = 0; i < pillList.size(); i++) {
            intakeList = databaseHelper.getIntakesFromPill(pillList.get(i));

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String test = sdf.format(cal.getTime());
            Log.e("TEST", test);
            currentTime = test.split(":");
            Log.e("timeHH", currentTime[0]);
            Log.e("timeMM", currentTime[1]);
            h = Integer.parseInt(currentTime[0]);
            m = Integer.parseInt(currentTime[1]);
            for (int j = 0; j < intakeList.size(); j++) {
                intakeTime = intakeList.get(j).getTimeOfIntake();
                pillTime = intakeTime.split(":");
                hPill = Integer.parseInt(pillTime[0]);
                mPill = Integer.parseInt(pillTime[1]);
                if (h == hPill && m == mPill) {
                    tvDescription.setText(pillList.get(i).getDescription());
                }
            }
        }

        bYes.setOnClickListener(this);
        bNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bYes:
                Log.e("Šta sam napravio", "onClick: Popio");
                this.finish();
                break;
            case R.id.bNo:
                Log.e("Šta sam napravio", "onClick: Nisam popio");
                this.finish();
                break;
        }
    }
}
