package com.alex.gamecrush.juegozombie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.gamecrush.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class EscenarioJuegoZombie extends AppCompatActivity {
    String uidString;
    String nombreString;

    String cantidadZombiesString;

    TextView contadorZombiesTextView;
    TextView nombreTextView;
    TextView tiempoRestateTextView;

    ImageView zombieImageView;

    int contador = 0;
    int altoPantalla;
    int anchoPantalla;

    boolean partidaPerdida = false;
    Dialog partidaPerdidaDialog;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_juego_zombie);

        contadorZombiesTextView = findViewById(R.id.contadorZombies);
        nombreTextView = findViewById(R.id.nombreJugador);
        tiempoRestateTextView = findViewById(R.id.tiempoRestante);
        zombieImageView = findViewById(R.id.imageViewZombie);

        partidaPerdidaDialog = new Dialog(EscenarioJuegoZombie.this);

        Bundle intent = getIntent().getExtras();

        uidString = intent.getString("uid");
        nombreString = intent.getString("nombre");
        cantidadZombiesString = intent.getString("cantidadzombies");
        // ASIGNACION VALORES RECOGIDOS DEL EXTRA
        nombreTextView.setText(nombreString);
        contadorZombiesTextView.setText(cantidadZombiesString);
        obtenerDatosPantalla();
        calcularTiempoRestante();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("BASE DE DATOS");


        // EVENTO MATAR ZOMBIES
        zombieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!partidaPerdida) {
                    // Aumenta el contador de zombies eliminados
                    contador++;
                    // Se cambia el texto del contador con el valor del contador
                    contadorZombiesTextView.setText(String.valueOf(contador));
                    // Al hacer click sobre la imagen, se cambia por la otra imagen
                    zombieImageView.setImageResource(R.drawable.zombieaplastado);

                    //Vuelve a aparecer la imagen del zombie original
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zombieImageView.setImageResource(R.drawable.zombie);
                            crearZombie();

                        }
                    }, generarNumeroAleatorio());
                }
            }
        });
    }

    /**
     * Devuelve un numero aleatorio
     *
     * @return
     */
    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(500 - 200) + 500;
    }

    private int generarNumeroAleatorio(int max, int min) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * Recoge la resolucion de la pantalla en la que se ejecuta el juego
     */
    private void obtenerDatosPantalla() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        anchoPantalla = point.x;
        altoPantalla = point.y;
    }

    /**
     * Cuando se elimina un zombie, se genera un nuevo zombie en una posición
     * aleatoria
     */
    private void crearZombie() {
        int valorMinimo = 0;
        int maximoX = anchoPantalla - zombieImageView.getWidth();
        int maximoY = altoPantalla - zombieImageView.getHeight();

        int randomX = generarNumeroAleatorio(maximoX, valorMinimo);
        int randomY = generarNumeroAleatorio(maximoY, valorMinimo);

        zombieImageView.setX(randomX);
        zombieImageView.setY(randomY);

    }

    /**
     * Calcula el tiempo restante que le falta al jugador y lo muestra en pantalla
     */
    private void calcularTiempoRestante() {
        new CountDownTimer(5000, 1000) {
            // TIEMPO ESTA CORRIENDO, SE EJECUTA UNA VEZ POR SEGUNDO
            public void onTick(long millisUntilFinished) {
                long tiempoRestante = millisUntilFinished / 1000;
                tiempoRestateTextView.setText(tiempoRestante + "S");
            }

            // TIEMPO SE ACABA
            public void onFinish() {
                tiempoRestateTextView.setText("0S");
                partidaPerdida = true;
                mostrarMensajePartidaPerdida();
                actualizarPuntuacion("Zombies", contador);
            }
        }.start();
    }

    /**
     * Muestra mediante el dialog el layout de partida_perdida_zombies
     */
    private void mostrarMensajePartidaPerdida() {
        TextView textoPartidaPerdidaZombiesTextView;
        TextView textoJuegoZombiesTextView;
        TextView cantidadZombiesEliminadosTextView;

        Button volverAJugarButton;
        Button verPuntuacionesButton;
        Button volverMenuPrincipalButton;
        partidaPerdidaDialog.setContentView(R.layout.partida_perdida_zombies);

        //textoPartidaPerdidaZombiesTextView = partidaPerdidaDialog.findViewById(R.id.textoPartidaPerdidaZombies);
        textoJuegoZombiesTextView = partidaPerdidaDialog.findViewById(R.id.textoJuegoZombies);

        volverAJugarButton = partidaPerdidaDialog.findViewById(R.id.volverAJugar);
        volverMenuPrincipalButton = partidaPerdidaDialog.findViewById(R.id.volverMenuPrincipal);
        //verPuntuacionesButton = partidaPerdidaDialog.findViewById(R.id.verPuntuaciones);

        // CLICK LISTENER BOTONES

        volverAJugarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = 0;
                partidaPerdidaDialog.dismiss();
                textoJuegoZombiesTextView.setText("");
                partidaPerdida = false;
                calcularTiempoRestante();
                crearZombie();
            }
        });
        volverMenuPrincipalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EscenarioJuegoZombie.this, MenuPrincipalJuegoZombie.class));
                finish();
            }
        });


        String zombies = String.valueOf(contador);

        textoJuegoZombiesTextView.setText("Has matado a" + zombies + " zombies.");
        partidaPerdidaDialog.show();


    }

    private void actualizarPuntuacion(String key, int cantidadZombies) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(key, cantidadZombies);
        reference.child(user.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EscenarioJuegoZombie.this, "La puntuacion fue actualizada!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}