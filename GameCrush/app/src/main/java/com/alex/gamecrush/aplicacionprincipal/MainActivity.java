package com.alex.gamecrush.aplicacionprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;

public class  MainActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);


        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Login.class));
            overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);

        });

        btnRegistro.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Registro.class));
            overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);
        });
    }
}