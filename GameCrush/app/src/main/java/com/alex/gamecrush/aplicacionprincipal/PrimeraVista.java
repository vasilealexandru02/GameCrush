package com.alex.gamecrush.aplicacionprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;

public class PrimeraVista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_vista);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PrimeraVista.this, Slider.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);
        }, 1500);
    }
}