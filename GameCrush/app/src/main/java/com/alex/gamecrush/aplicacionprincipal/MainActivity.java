package com.alex.gamecrush.aplicacionprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;

public class  MainActivity extends AppCompatActivity {
    Button loginButton;
    Button registroButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.btnLogin);
        registroButton = findViewById(R.id.btnRegistro);


        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Login.class));
            overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);

        });

        registroButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Registro.class));
            overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);
        });
    }
}