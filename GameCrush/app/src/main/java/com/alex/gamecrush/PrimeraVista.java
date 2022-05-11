package com.alex.gamecrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.alex.gamecrush.juegozombie.MenuPrincipalJuegoZombie;

public class PrimeraVista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_vista);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PrimeraVista.this, MenuPrincipalJuegoZombie.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);
        }, 1500);
    }
}