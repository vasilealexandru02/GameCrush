package com.alex.gamecrush.zombiekiller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;


public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        Bundle bundle = getIntent().getExtras();
        TextView acercaDeTextView = findViewById(R.id.nombreUsuarioAcercaDe);
        ImageView flechaVolverAtrasImageView = findViewById(R.id.flechaAcercaDe);
        acercaDeTextView.setText(bundle.getString("nombre"));
        ImageView libroImageView = findViewById(R.id.imagenLibroAcercaDe);
        // CLICK LISTENER FLECHA VOLVER ATRÁS.
        flechaVolverAtrasImageView.setOnClickListener(v -> {
            startActivity(new Intent(AcercaDe.this, MenuZombieKiller.class));
            finish();
        });
        // CLICK LISTENER LIBRO.
        libroImageView.setOnClickListener(v -> Toast.makeText(AcercaDe.this, "Ríndete, no hay más información.", Toast.LENGTH_SHORT).show());

    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);
    }
}