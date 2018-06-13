package com.example.daniel.hcs;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Intake;
import com.example.daniel.hcs.utils.IntakeEvent;
import com.example.daniel.hcs.utils.Pill;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.List;

public class ListItemActivity extends Activity {

    TextView name, description;
    TextView[] time;
    DatabaseHelper databaseHelper;
    List<IntakeEvent> intakeEvents;
    List<Intake> intakeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        name = findViewById(R.id.tvPillName);
        description = findViewById(R.id.tvPillDescription);
        time = new TextView[]{
                findViewById(R.id.tvTimesOfIntake1),
                findViewById(R.id.tvTimesOfIntake2),
                findViewById(R.id.tvTimesOfIntake3),
                findViewById(R.id.tvTimesOfIntake4),
                findViewById(R.id.tvTimesOfIntake5),
                findViewById(R.id.tvTimesOfIntake6),
                findViewById(R.id.tvTimesOfIntake7)
        };

        databaseHelper = DatabaseHelper.getInstance(this);

        Long serverID = getIntent().getLongExtra("serverID", 0);
        Pill pill = databaseHelper.getPill(serverID);
        intakeList = databaseHelper.getIntakesFromPill(pill);

        intakeEvents = databaseHelper.getIntakeEvent(serverID);

        name.setText(pill.getName());
        description.setText(pill.getDescription());

        for (int i = 0; i < intakeList.size(); i++) {
            setColor(i);
            time[i].setText(intakeList.get(i).getTimeOfIntake());
        }

    }

    private void setColor(int i) {
        if (intakeEvents.size() > 0) {
            for (IntakeEvent intakeEvent : intakeEvents) {
                if (intakeEvent.getIntakeId().equals(intakeList.get(i).getServerId())) {
                    if (intakeEvent.getTaken()) {
                        time[i].setTextColor(getResources().getColor(R.color.greenTEXT));
                        break;
                    }
                }
            }
        }
    }
}
