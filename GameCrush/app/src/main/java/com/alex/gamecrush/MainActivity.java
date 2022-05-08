package com.alex.gamecrush;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        //


        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Login.class));


        });

        btnRegistro.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Registro.class));
        });
    }
}