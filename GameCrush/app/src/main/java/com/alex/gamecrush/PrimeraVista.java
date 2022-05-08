package com.alex.gamecrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PrimeraVista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_vista);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PrimeraVista.this, MenuPrincipal.class);
            startActivity(intent);
        }, 1500);
    }
}