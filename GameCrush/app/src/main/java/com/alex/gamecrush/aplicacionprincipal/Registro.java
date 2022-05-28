package com.alex.gamecrush.aplicacionprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.gamecrush.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Registro extends AppCompatActivity {
    FirebaseAuth auth;


    EditText nombreUsuarioEditText;
    EditText emailUsuarioEditText;
    EditText passwordUsuarioEditText;

    TextView fechaRegistroTextView;

    Button registrarUsuarioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreUsuarioEditText = findViewById(R.id.nombreUsuario);
        emailUsuarioEditText = findViewById(R.id.emailUsuario);
        passwordUsuarioEditText = findViewById(R.id.passwordUsuario);
        fechaRegistroTextView = findViewById(R.id.fechaRegistro);
        registrarUsuarioButton = findViewById(R.id.registrarUsuario);
        auth = FirebaseAuth.getInstance();

        Date date = new Date();

        SimpleDateFormat sdt = new SimpleDateFormat("MM/dd/yyyy");
        String fechaString = sdt.format(date);
        fechaRegistroTextView.setText(fechaString);
        registrarUsuarioButton.setOnClickListener(v -> {
            String email = emailUsuarioEditText.getText().toString();
            String password = passwordUsuarioEditText.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailUsuarioEditText.setError("Correo no válido!");
                emailUsuarioEditText.setFocusable(true);
            } else if (password.length() < 8) {
                passwordUsuarioEditText.setError("La contraseña debe contener más de 8 caracteres");
            } else {
                registrarUsuario(email, password);
            }
        });
    }

    /**
     * Metodo que registra un usuario
     *
     * @param email
     * @param password
     */
    private void registrarUsuario(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Una vez registrado el usuario, se cambia de actividad a la del menu
                    //startActivity(new Intent(Registro.this, MenuJuego.class));
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(Registro.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            insertarDatosJugador();
                            startActivity(new Intent(Registro.this, Slider.class));
                            finish();
                        }
                    });


                } else {
                    Toast.makeText(Registro.this, "No se ha podido registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
                Toast.makeText(Registro.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * Metodo que inserta los datos del jugador
     */
    private void insertarDatosJugador() {
        int cantidadZombiesEliminados = 0;
        String uidString = auth.getCurrentUser().getUid();
        String emailString = emailUsuarioEditText.getText().toString();
        String passwordString = passwordUsuarioEditText.getText().toString();
        String nombreUsuarioString = nombreUsuarioEditText.getText().toString();
        String fechaString = fechaRegistroTextView.getText().toString();
        HashMap<Object, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("Uid", uidString); // uid del usuario
        datosUsuario.put("Email", emailString); // email del usuario
        datosUsuario.put("Password", passwordString);   // password del usuario
        datosUsuario.put("Nombre", nombreUsuarioString);     // nombre del usuario
        datosUsuario.put("Zombies", cantidadZombiesEliminados); // cantidad de zombies eliminados por el usuario
        datosUsuario.put("Fecha", fechaString);      // fecha de registro del usuario

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("BASE DE DATOS");
        reference.child(uidString).setValue(datosUsuario);
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BASE DE DATOS");
        //reference.push().setValue(datosUsuario);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);
    }
}