package com.alex.gamecrush.aplicacionprincipal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alex.gamecrush.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Slider extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    private ViewPager viewPager;
    // consulta datos
    private SliderAdapter sliderAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference baseDeDatos;
    TextView textoBienvenidaSliderTextView;
    //
    ImageView flechaAtrasImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        // Base de datos
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        baseDeDatos = firebaseDatabase.getReference("BASE DE DATOS");
        //
        viewPager = findViewById(R.id.slider);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        flechaAtrasImageView = findViewById(R.id.flechaSlider);
        textoBienvenidaSliderTextView = findViewById(R.id.textoBienvenidaSlider);
        usuarioLogueado();

        AlertDialog.Builder messageAlertDialog = new AlertDialog.Builder(this);
        messageAlertDialog.setMessage("¿Deseas cerrar sesion?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Slider.this, "Has cerrado sesión.", Toast.LENGTH_SHORT).show();
                        cerrarSesion();
                        startActivity(new Intent(Slider.this, MainActivity.class));
                        finish();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        flechaAtrasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Slider.this, "Texto", Toast.LENGTH_SHORT).show();
                messageAlertDialog.show();
            }
        });
    }

    private void consultarNombreJugador() {
        Query query = baseDeDatos.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot ds : datasnapshot.getChildren()) {
                    String nombreString = "" + ds.child("Nombre").getValue();
                    textoBienvenidaSliderTextView.setText(new StringBuilder().append("Bienvenido ").append(nombreString).append(" !").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cerrarSesion() {
        auth.signOut();
        Toast.makeText(this, "¡Has cerrado sesión!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Slider.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);

    }

    private void usuarioLogueado() {
        if (user != null) {
            //Toast.makeText(this, "Jugador en linea", Toast.LENGTH_SHORT).show();
            consultarNombreJugador();

        } else {
            startActivity(new Intent(Slider.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }


}