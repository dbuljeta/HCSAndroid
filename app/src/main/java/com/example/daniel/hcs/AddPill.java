package com.example.daniel.hcs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.daniel.hcs.interfaces.RequestListener;
import com.example.daniel.hcs.utils.API;
import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Intake;
import com.example.daniel.hcs.utils.Pill;

import java.util.ArrayList;
import java.util.List;

public class AddPill extends Activity implements View.OnClickListener, DialogInterface.OnClickListener {
    EditText etName, etDescription, etNumberOfConsuption;
    //    TimePicker tpTimeOfIntake;
    Button bAdd;
    DatabaseHelper databaseHelper;
    Integer numberOfDialogs = 0;
    Long number, pillId;
    Pill pill;
    List<Intake> intakeList = new ArrayList<Intake>();
    private API apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
//        tpTimeOfIntake = findViewById(R.id.tpTimeOfIntake);
        etNumberOfConsuption = findViewById(R.id.etNumberOfConsuption);
        bAdd = findViewById(R.id.bAdd);
        bAdd.setOnClickListener(this);
        apiService = API.getInstance(this);
        databaseHelper = DatabaseHelper.getInstance(this);
    }

    @Override
    public void onClick(View view) {
        String name, description;
        name = String.valueOf(etName.getText());
        description = String.valueOf(etDescription.getText());
//        serverId = Long.parseLong(String.valueOf(etTimeOfConsumption.getText()));
        number = Long.parseLong(String.valueOf(etNumberOfConsuption.getText()));
        if (!name.isEmpty() || !description.isEmpty() || number != 0) {
            //TODO make server request
            pill = new Pill(name, description, number);
            Log.e("Pill", String.valueOf(pill.getServerId()));
            Log.e("Pill", String.valueOf(pill.getName()));
            Log.e("Pill", String.valueOf(pill.getDescription()));
            Log.e("Pill", String.valueOf(pill.getNumberOfIntakes()));

//            pillId = databaseHelper.addPill(pill);

            final TimePicker timePicker = new TimePicker(this);
            timePicker.setIs24HourView(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add intake " + (numberOfDialogs + 1))
                    .setMessage("Set time of intake!")
                    .setView(timePicker)
                    .setPositiveButton("Create", this)
                    .show();

        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        numberOfDialogs++;
        final TimePicker timePicker = new TimePicker(this);
        timePicker.setIs24HourView(true);
        Integer hour = timePicker.getHour();
        Integer min = timePicker.getMinute();
        Log.e("HOURS", String.valueOf(hour));
        Log.e("min", String.valueOf(min));
        intakeList.add(new Intake(
                hour + ":" + min + ":00"
        ));
//        databaseHelper.addIntake(new Intake(
//                1L,
//                pillId,
//                hour + ":" + min + ":00"
//        ));

        if(numberOfDialogs == number - 1) {
            Log.e("ADD", "SEND");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add intake " + (numberOfDialogs + 1))
                    .setMessage("Set time of intake!")
                    .setView(timePicker)
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Integer hour = timePicker.getHour();
                            Integer min = timePicker.getMinute();
                            Log.e("HOURS", String.valueOf(hour));
                            Log.e("min", String.valueOf(min));
//                            databaseHelper.addIntake(new Intake(
//                                    1L,
//                                    pillId,
//                                    hour + ":" + min + ":00"
//                            ));
                            if (min > 10)
                            {
                                intakeList.add(new Intake(
                                        String.valueOf(hour) + ":" + String.valueOf(min) + ":00"
                                ));
                            }
                            else {
                                intakeList.add(new Intake(
                                        String.valueOf(hour) + ":0" + String.valueOf(min) + ":00"
                                ));
                            }


                            apiService.createPill(pill, intakeList, new RequestListener() {
                                @Override
                                public void failed(String message) {
                                    Log.e("ADD", "FAIL");
                                }

                                @Override
                                public void finished(String message) {
                                    Log.e("ADD", "SUCC");
                                    AddPill.this.finish();
                                }
                            });
                        }
                    })
                    .show();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add intake " + (numberOfDialogs + 1))
                    .setMessage("Set time of intake!")
                    .setView(timePicker)
                    .setPositiveButton("Create", this)
                    .show();
        }
    }

    private void createTimePickerDialog() {
        final TimePicker timePicker = new TimePicker(this);
        timePicker.setIs24HourView(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add intake " + (numberOfDialogs + 1))
                .setMessage("Set time of intake!")
                .setView(timePicker)
                .setPositiveButton("Create", this)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Integer hour = timePicker.getHour();
                        Integer min = timePicker.getMinute();
                        Log.e("HOURS", String.valueOf(hour));
                        Log.e("min", String.valueOf(min));
                        intakeList.add(new Intake(
                                hour + ":" + min + ":00"
                        ));
//                        databaseHelper.addIntake(new Intake(
//                                1L,
//                                pillId,
//                                hour + ":" + min + ":00"
//                        ));
                    }


                }).show();
    }
}
