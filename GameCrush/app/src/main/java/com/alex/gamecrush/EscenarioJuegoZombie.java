package com.alex.gamecrush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EscenarioJuegoZombie extends AppCompatActivity {
    String uidString;
    String nombreString;

    String cantidadZombiesString;

    TextView contadorZombiesTextView;
    TextView nombreTextView;
    TextView tiempoJugadoTextView;

    ImageView zombieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_juego_zombie);

        contadorZombiesTextView = findViewById(R.id.contadorZombies);
        nombreTextView = findViewById(R.id.nombreJugador);
        tiempoJugadoTextView = findViewById(R.id.tiempoJugado);
        zombieImageView = findViewById(R.id.imageViewZombie);

        Bundle intent = getIntent().getExtras();

        uidString = intent.getString("uid");
        nombreString = intent.getString("nombre");
        cantidadZombiesString = intent.getString("cantidadzombies");
        // ASIGNACION VALORES RECOGIDOS DEL EXTRA
        nombreTextView.setText(nombreString);
        contadorZombiesTextView.setText(cantidadZombiesString);


    }
}