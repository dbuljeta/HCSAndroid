package com.example.daniel.hcs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.hcs.interfaces.RequestListener;
import com.example.daniel.hcs.utils.API;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button bLogin, bRegister;
    private EditText etName, etPassword;
    private API apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = findViewById(R.id.bLogin);
        bRegister = findViewById(R.id.bRegister);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

        apiService = API.getInstance(this);
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
