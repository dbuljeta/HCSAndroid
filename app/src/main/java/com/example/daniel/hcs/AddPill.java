package com.example.daniel.hcs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daniel.hcs.utils.DatabaseHelper;
import com.example.daniel.hcs.utils.Pill;

public class AddPill extends Activity implements View.OnClickListener {
    EditText etName, etDescription, etTimeOfConsumption, etNumberOfConsuption;
    Button bAdd;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etTimeOfConsumption = findViewById(R.id.etTimeOfConsumption);
        etNumberOfConsuption = findViewById(R.id.etNumberOfConsuption);
        bAdd = findViewById(R.id.bAdd);
        bAdd.setOnClickListener(this);
        databaseHelper = DatabaseHelper.getInstance(this);
    }

    @Override
    public void onClick(View view) {
        String name, description;
        Long number;
        int time;
        name = String.valueOf(etName.getText());
        description = String.valueOf(etDescription.getText());
        time = Integer.parseInt(String.valueOf(etTimeOfConsumption.getText()));
        number = Long.parseLong(String.valueOf(etNumberOfConsuption.getText()));
        if (!name.isEmpty() || !description.isEmpty() || time!=0 || number != 0)
        {
            Pill pill = new Pill(name, description, number);
            databaseHelper.addPill(pill);
            this.finish();
        }
    }
}
