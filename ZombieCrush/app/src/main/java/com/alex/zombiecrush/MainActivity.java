package com.alex.zombiecrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnLogin.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"Click LOGIN",Toast.LENGTH_SHORT).show();
        });

        btnRegistro.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Registro.class));
        });
    }
}