package com.alex.gamecrush.zombiekiller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;
import com.alex.gamecrush.aplicacionprincipal.MainActivity;
import com.alex.gamecrush.aplicacionprincipal.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MenuZombieKiller extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;

    Button jugarButton;
    Button puntuacionesButton;
    Button acercaDeButton;

    TextView cantidadZombiesTextView;
    TextView nombreUsuarioMenuTextView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference baseDeDatos;

    ImageView flechaMenuJuegoZombie;

    String puntuacionMax;
    String uidString;
    String nombreJugador;
    String cantidadZombiesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_zombie_killer);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        baseDeDatos = firebaseDatabase.getReference("BASE DE DATOS");

        jugarButton = findViewById(R.id.btnJugar);
        puntuacionesButton = findViewById(R.id.btnPuntuaciones);
        acercaDeButton = findViewById(R.id.btnAcercaDe);


        cantidadZombiesTextView = findViewById(R.id.cantidadZombies);
        nombreUsuarioMenuTextView = findViewById(R.id.nombreUsuarioMenu);

        flechaMenuJuegoZombie = findViewById(R.id.flechaMenuJuegoZombie);

        // BOTON DE JUGAR
        jugarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuZombieKiller.this, EscenarioJuegoZombieKiller.class);
                //String nombreString = textViewNombreUsuarioMenu.getText().toString();
                String cantidadZombies = cantidadZombiesTextView.getText().toString();

                intent.putExtra("uid", uidString);
                intent.putExtra("nombre", nombreJugador);
                intent.putExtra("cantidadzombies", cantidadZombiesString);
                intent.putExtra("puntuacionMax", puntuacionMax);

                startActivity(intent);
                finish();
            }
        });
        // BOTON DE VER PUNTUACIONES
        puntuacionesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuZombieKiller.this, PuntuacionesZombieKiller.class);
                intent.putExtra("nombre", nombreJugador);
                startActivity(intent);
                finish();
            }
        });

        // BOTON DE ACERCA DE
        acercaDeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // FLECHA VOLVER ATRAS
        flechaMenuJuegoZombie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuZombieKiller.this, Slider.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        usuarioLogueado();
        super.onStart();
    }

    private void usuarioLogueado() {
        if (user != null) {
            //Toast.makeText(this, "Jugador en linea", Toast.LENGTH_SHORT).show();
            consultarDatosJugador();
        } else {
            startActivity(new Intent(MenuZombieKiller.this, MainActivity.class));
            finish();
        }
    }

    /**
     * Hace una query con todos los datos del usuario filtrado por el correo del usuario y asigna los valores a los text view
     */
    private void consultarDatosJugador() {
        Query query = baseDeDatos.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot ds : datasnapshot.getChildren()) {
                    cantidadZombiesString = "" + ds.child("Zombies").getValue();
                    puntuacionMax = "" + ds.child("Zombies").getValue();
                    uidString = "" + ds.child("Uid").getValue();
                    String nombreString = "" + ds.child("Nombre").getValue();
                    nombreJugador = "" + ds.child("Nombre").getValue();

                    cantidadZombiesTextView.setText("Record: " + cantidadZombiesString + " zombies eliminados");
                    nombreUsuarioMenuTextView.setText("Jugador: " + nombreString);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);
    }
}