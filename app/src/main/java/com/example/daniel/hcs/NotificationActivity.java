package com.example.daniel.hcs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.daniel.hcs.interfaces.RequestListener;
import com.example.daniel.hcs.utils.API;
import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Intake;
import com.example.daniel.hcs.utils.IntakeEvent;
import com.example.daniel.hcs.utils.Pill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NotificationActivity extends Activity implements View.OnClickListener {

    private Button bYes, bNo;
    private TextView tvDescription;

    private List<Pill> pillList;
    private List<Intake> intakeList;
    private Long pillId;
    private Long intakeId;

    private DatabaseHelper databaseHelper;
    private API api;

    public static final String BUNDLE_PILL_ID = "pill_id";
    public static final String BUNDLE_INTAKE_ID = "intake_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Calendar cal = Calendar.getInstance();
        bYes = findViewById(R.id.bYes);
        bNo = findViewById(R.id.bNo);
        databaseHelper = DatabaseHelper.getInstance(this);
        api = API.getInstance(this);

        tvDescription = findViewById(R.id.tvDesc);
        pillList = databaseHelper.getAllPills();
//        String[] currentTime;
//        String[] pillTime;
//        String intakeTime;
//        int h, m, hPill, mPill;
//        for (int i = 0; i < pillList.size(); i++) {
//            intakeList = databaseHelper.getIntakesFromPill(pillList.get(i));
//
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//            String test = sdf.format(cal.getTime());
//            Log.e("TEST", test);
//            currentTime = test.split(":");
//            Log.e("timeHH", currentTime[0]);
//            Log.e("timeMM", currentTime[1]);
//            h = Integer.parseInt(currentTime[0]);
//            m = Integer.parseInt(currentTime[1]);
//            for (int j = 0; j < intakeList.size(); j++) {
//                intakeTime = intakeList.get(j).getTimeOfIntake();
//                pillTime = intakeTime.split(":");
//                hPill = Integer.parseInt(pillTime[0]);
//                mPill = Integer.parseInt(pillTime[1]);
//                if (h == hPill && m == mPill) {
//                    tvDescription.setText(pillList.get(i).getDescription());
//                }
//            }
//        }
        pillId = getIntent().getLongExtra(BUNDLE_PILL_ID, 1);
        intakeId = getIntent().getLongExtra(BUNDLE_INTAKE_ID, 1);
        Log.e("pillID",String.valueOf(pillId));
        Log.e("intakeID",String.valueOf(intakeId));
        bYes.setOnClickListener(this);
        bNo.setOnClickListener(this);
    }


    //TODO buttons functionality, add to database...
    @Override
    public void onClick(View view) {
        Boolean isTaken = Boolean.FALSE;
        switch (view.getId()){
            case R.id.bYes:
                Log.e("Šta sam napravio", "onClick: Popio");
                isTaken = Boolean.TRUE;
                break;
            case R.id.bNo:
                Log.e("Šta sam napravio", "onClick: Nisam popio");
                isTaken = Boolean.FALSE;
                break;
        }

        //TODO send pill id intake id and isTaken
        api.createEventIntake(new IntakeEvent(pillId, intakeId, isTaken), new RequestListener() {
            @Override
            public void failed(String message) {

            }

            @Override
            public void finished(String message) {
                NotificationActivity.this.finish();
            }
        });
    }
}
