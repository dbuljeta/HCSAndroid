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
    private TextView tvDescription, tvName;

    private List<Pill> pillList;
    private List<Intake> intakeList;
    private Long pillId;
    private Long intakeId;
    private String pillName;
    private String pillDescription;


    private DatabaseHelper databaseHelper;
    private API api;

    public static final String BUNDLE_PILL_ID = "pill_id";
    public static final String BUNDLE_INTAKE_ID = "intake_id";
    public static final String BUNDLE_PILL_NAME = "pill_name";
    public static final String BUNDLE_PILL_DESCRIPTION = "pill_description";


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
        tvName = findViewById(R.id.tvName);
        pillList = databaseHelper.getAllPills();
        pillId = getIntent().getLongExtra(BUNDLE_PILL_ID, 1);
        intakeId = getIntent().getLongExtra(BUNDLE_INTAKE_ID, 1);
        pillName = getIntent().getStringExtra(BUNDLE_PILL_NAME);
        pillDescription = getIntent().getStringExtra(BUNDLE_PILL_DESCRIPTION);

        Log.e("pillID",String.valueOf(pillId));
        Log.e("intakeID",String.valueOf(intakeId));
        Log.e("pillName",pillName);
        tvName.setText(pillName);
        tvDescription.setText(pillDescription);
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
