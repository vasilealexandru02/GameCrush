package com.alex.gamecrush.zombiekiller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.gamecrush.R;
import com.alex.gamecrush.aplicacionprincipal.Constants;
import com.alex.gamecrush.aplicacionprincipal.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuntuacionesZombieKiller extends AppCompatActivity {

    List<ListElement> elementos;

    private TextView puntuacionesTextView;

    private ImageView flechaVolverAtrasImageView;

    private DatabaseReference baseDeDatos;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones_zombie_killer);
        Bundle bundle = getIntent().getExtras();
        puntuacionesTextView = findViewById(R.id.nombreUsuarioPuntuaciones);
        flechaVolverAtrasImageView = findViewById(R.id.flechaMenuPuntuaciones);
        puntuacionesTextView.setText(bundle.getString("nombre"));


        // FLECHA VOLVER ATRAS
        flechaVolverAtrasImageView.setOnClickListener(v -> {
            startActivity(new Intent(PuntuacionesZombieKiller.this, MenuZombieKiller.class));
            finish();
        });
        init();

    }

    public void init() {
        //consultarScoreJugadores();
        elementos = new ArrayList<>();
        //elementos.add(new ListElement("#000000", "Pepito", "20 zombies eliminados"));

        ListAdapter listAdapter = new ListAdapter(elementos, this);
        RecyclerView recyclerView = findViewById(R.id.datosJugadorRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        //
        firebaseDatabase = FirebaseDatabase.getInstance();
        baseDeDatos = firebaseDatabase.getReference(Constants.DB_PUNTUACIONZOMBIE);
        Query query = baseDeDatos.orderByChild("Zombies").limitToLast(5);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.i("SNAPSHOT", snapshot.getChildren().toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //Log.i("SNAPSHOT", dataSnapshot.child("Jugador").getValue().toString());
                    String nombre = "" + dataSnapshot.child("Jugador").getValue();
                    String score = "" + dataSnapshot.child("Zombies").getValue();
                    ListElement listElement = new ListElement(nombre, score);
                    elementos.add(listElement);
                    Log.i("ELEMENTOS", elementos.toString());
                    // ordena los elementos de la query de forma descendente
                    Collections.sort(elementos, (listElement1, listElement2) -> {
                        if (Integer.parseInt(listElement1.getScore()) < Integer.parseInt(listElement2.getScore()))
                            return 1;
                        if (Integer.parseInt(listElement1.getScore()) > Integer.parseInt(listElement2.getScore()))
                            return -1;
                        return 0;
                    });


                }
                // añadir texto al score del jugador

               /* for (ListElement listElement : elementos) {
                    listElement.setScore(listElement.getScore() + " zombies eliminados");
                }*/

                listAdapter.notifyDataSetChanged();
                if (elementos.isEmpty()) {
                    findViewById(R.id.noHayRegistrosPuntuaciones).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Log.i("CONTENIDO ARRAY", elementos.toString());
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);
        elementos.clear();
    }
}