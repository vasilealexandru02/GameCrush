package com.alex.gamecrush.juegozombie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.aplicacionprincipal.MainActivity;
import com.alex.gamecrush.R;
import com.alex.gamecrush.aplicacionprincipal.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipalJuegoZombie extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;

    Button btnJugar;
    Button btnPuntuaciones;
    Button btnAcercaDe;

    TextView textViewcantidadZombies;
    TextView textViewNombreUsuarioMenu;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference baseDeDatos;

    ImageView flechaMenuJuegoZombie;

    String puntuacionMax;
    String uidString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juego);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        baseDeDatos = firebaseDatabase.getReference("BASE DE DATOS");

        btnJugar = findViewById(R.id.btnJugar);
        btnPuntuaciones = findViewById(R.id.btnPuntuaciones);
        btnAcercaDe = findViewById(R.id.btnAcercaDe);


        textViewcantidadZombies = findViewById(R.id.cantidadZombies);
        textViewNombreUsuarioMenu = findViewById(R.id.nombreUsuarioMenu);

        flechaMenuJuegoZombie = findViewById(R.id.flechaMenuJuegoZombie);

        // BOTON DE JUGAR
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalJuegoZombie.this, EscenarioJuegoZombie.class);
                String nombreString = textViewNombreUsuarioMenu.getText().toString();
                String cantidadZombies = textViewcantidadZombies.getText().toString();

                intent.putExtra("uid", uidString);
                intent.putExtra("nombre", nombreString);
                intent.putExtra("cantidadzombies", cantidadZombies);
                intent.putExtra("puntuacionMax", puntuacionMax);

                startActivity(intent);
                finish();
            }
        });
        // BOTON DE VER PUNTUACIONES
        btnPuntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // BOTON DE ACERCA DE
        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        flechaMenuJuegoZombie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipalJuegoZombie.this, Slider.class));
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
            startActivity(new Intent(MenuPrincipalJuegoZombie.this, MainActivity.class));
            finish();
        }
    }

    /**
     * Cierra la sesion del usuario
     */
    private void cerrarSesion() {
        auth.signOut();
        Toast.makeText(this, "¡Has cerrado sesión!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MenuPrincipalJuegoZombie.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);

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
                    String cantidadZombiesString = "" + ds.child("Zombies").getValue();
                    puntuacionMax = "" + ds.child("Zombies").getValue();
                    uidString = "" + ds.child("Uid").getValue();
                    String nombreString = "" + ds.child("Nombre").getValue();

                    textViewcantidadZombies.setText("Record: " + cantidadZombiesString);
                    textViewNombreUsuarioMenu.setText("Bienvenido " + nombreString + " !");

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