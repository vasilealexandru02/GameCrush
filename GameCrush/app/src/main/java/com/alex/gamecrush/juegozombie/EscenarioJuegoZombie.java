package com.alex.gamecrush.juegozombie;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;
import com.alex.gamecrush.aplicacionprincipal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class EscenarioJuegoZombie extends AppCompatActivity {
    String uidString;
    String nombreString;

    String cantidadZombiesString;

    TextView contadorZombiesTextView;
    TextView tiempoRestateTextView;
    boolean puntuacionCambiada;
    ImageView zombieImageView;
    ArrayList<Integer> imagenesZombies;
    ArrayList<Integer> imagenesFondo;

    int contador = 0;
    int altoPantalla;
    int anchoPantalla;
    int numeroScore;
    boolean partidaPerdida = false;
    Dialog partidaPerdidaDialog;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference zombieKillerScoreReference;
    int puntuacionMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_juego_zombie);

        contadorZombiesTextView = findViewById(R.id.contadorZombies);

        tiempoRestateTextView = findViewById(R.id.tiempoRestante);
        zombieImageView = findViewById(R.id.imageViewZombie);
        partidaPerdidaDialog = new Dialog(EscenarioJuegoZombie.this, R.style.ShapeAppearanceOverlay_MaterialComponents_MaterialCalendar_Window_Fullscreen);

        Bundle intent = getIntent().getExtras();

        uidString = intent.getString("uid");
        nombreString = intent.getString("nombre");
        cantidadZombiesString = intent.getString("cantidadzombies");
        puntuacionMax = Integer.parseInt(intent.getString("puntuacionMax"));
        // ASIGNACION VALORES RECOGIDOS DEL EXTRA

        contadorZombiesTextView.setText(cantidadZombiesString);
        obtenerDatosPantalla();
        calcularTiempoRestante();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        // referencia tabla base de datos
        reference = database.getReference("BASE DE DATOS");
        // referencia tabla puntuacion zombie killer
        zombieKillerScoreReference = database.getReference(Constants.DB_PUNTUACIONZOMBIE);

        puntuacionCambiada = false;
        // IMAGENES ZOMBIES
        imagenesZombies = new ArrayList<>();
        imagenesZombies.add(R.drawable.zombie1);
        imagenesZombies.add(R.drawable.zombie2);
        imagenesZombies.add(R.drawable.zombie3);

        // IMAGENES FONDO
        imagenesFondo = new ArrayList<>();
        imagenesFondo.add(R.drawable.background1);
        imagenesFondo.add(R.drawable.background3);
        imagenesFondo.add(R.drawable.background4);
        findViewById(R.id.layoutJuegoZombies).setBackgroundResource(imagenesFondo.get(generarNumeroAleatorio(imagenesFondo.size(), 0)));


        // EVENTO MATAR ZOMBIES
        zombieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!partidaPerdida) {
                    // Aumenta el contador de zombies eliminados
                    contador++;
                    // Se cambia el texto del contador con el valor del contador
                    contadorZombiesTextView.setText(String.valueOf(contador) + " zombies eliminados");
                    // Al hacer click sobre la imagen, se cambia por la otra imagen
                    //zombieImageView.setImageResource(R.drawable.zombieaplastado);
                    zombieImageView.setImageResource(imagenesZombies.get(generarNumeroAleatorio(imagenesZombies.size(), 0)));
                    crearZombie();
                    //Vuelve a aparecer la imagen del zombie original
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                        }
                    }, generarNumeroAleatorio());
                }
            }
        });
        numeroScore = 0;
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
        new CountDownTimer(10000, 1000) {
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

        TextView textoJuegoZombiesTextView;
        Button volverAJugarButton;
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
                partidaPerdidaDialog.dismiss();
                finish();
            }
        });


        String zombies = String.valueOf(contador);

        textoJuegoZombiesTextView.setText("Has matado a " + zombies + " zombies.");
        partidaPerdidaDialog.show();


    }

    private void actualizarPuntuacion(String key, int cantidadZombies) {
        if (cantidadZombies > puntuacionMax) {
            HashMap<String, Object> datosJugador = new HashMap<>();
            datosJugador.put(key, cantidadZombies);
            reference.child(user.getUid()).updateChildren(datosJugador).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    puntuacionCambiada = true;
                    Toast.makeText(EscenarioJuegoZombie.this, "La puntuación fue actualizada!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(EscenarioJuegoZombie.this, "Tu puntuación ha sido demasiado baja!", Toast.LENGTH_SHORT).show();
        }
        numeroScore++;
        HashMap<String, Object> puntuacionZombieKiller = new HashMap<>();
        puntuacionZombieKiller.put("Jugador", nombreString);
        puntuacionZombieKiller.put("Zombies", contador);

        zombieKillerScoreReference.child(String.valueOf(UUID.randomUUID())).setValue(puntuacionZombieKiller);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);
    }

}