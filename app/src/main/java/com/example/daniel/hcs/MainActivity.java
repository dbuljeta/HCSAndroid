package com.example.daniel.hcs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.hcs.interfaces.RequestListener;
import com.example.daniel.hcs.utils.API;
import com.example.daniel.hcs.utils.AppConstants;
import com.example.daniel.hcs.utils.DatabaseHelper;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button bLogin, bRegister;
    private EditText etName, etPassword;
    private API apiService;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = findViewById(R.id.bLogin);
        bRegister = findViewById(R.id.bRegister);
        etName = findViewById(R.id.etFullName);
        etPassword = findViewById(R.id.etPassword);

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(
                AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String jwt = sharedPreferences.getString(AppConstants.KEY_JWT,"x");
        if (!jwt.equals("x")) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        } else {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(MainActivity.this);
            databaseHelper.deleteAllPills();
        }

        apiService = API.getInstance(this);
//        databaseHelper = DatabaseHelper.getInstance(this);
//        databaseHelper.deleteAllPills();
    }

    @Override
    public void onClick(View view) {
        String name = String.valueOf(etName.getText());
        String password = String.valueOf(etPassword.getText());

        switch(view.getId()) {
            case R.id.bLogin:
                apiService.login(name, password, new RequestListener() {
                    @Override
                    public void failed(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                        btLogin.setVisibility(View.VISIBLE);
//                        llProgressStatus.setVisibility(View.GONE);
                    }

                    @Override
                    public void finished(String message) {
                        Log.e("FINITO", "LADIDA");
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });
                break;
            case R.id.bRegister:
                apiService.register(name, password, new RequestListener() {
                    @Override
                    public void failed(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                        pbProgressBar.setVisibility(View.GONE);
//                        btRegister.setVisibility(View.VISIBLE);
                        Log.e("FAILED", message);
                    }

                    @Override
                    public void finished(String message) {
                        Log.e("FINISHED", message);
                    }
                });
                break;
        }
    }
}
