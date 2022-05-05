package com.alex.zombiecrush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Registro extends AppCompatActivity {
    FirebaseAuth auth;
    EditText nombreUsuario;
    EditText emailUsuario;
    EditText passwordUsuario;
    TextView fechaRegistro;
    Button registrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreUsuario = findViewById(R.id.nombreUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);
        passwordUsuario = findViewById(R.id.passwordUsuario);
        fechaRegistro = findViewById(R.id.fechaRegistro);
        registrarUsuario = findViewById(R.id.registrarUsuario);
        auth = FirebaseAuth.getInstance();
        Date date = new Date();

        SimpleDateFormat sdt = new SimpleDateFormat("MM/dd/yyyy");
        String fechaString = sdt.format(date);
        fechaRegistro.setText(fechaString);
        registrarUsuario.setOnClickListener(v -> {
            String email = emailUsuario.getText().toString();
            String password = passwordUsuario.getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailUsuario.setError("Correo no válido!");
                emailUsuario.setFocusable(true);
            } else if (password.length() < 8) {
                passwordUsuario.setError("La contraseña debe contener más de 8 caracteres");
            } else {
                registrarUsuario(email, password);
            }
        });


    }

    // Registrar usuario
    private void registrarUsuario(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser usuario = auth.getCurrentUser();
                    int cantidadZombiesEliminados = 0;
                    assert usuario != null;
                    String uidString = usuario.getUid();
                    String emailString = emailUsuario.getText().toString();
                    String passwordString = passwordUsuario.getText().toString();
                    String nombreString = nombreUsuario.getText().toString();
                    String fechaString = fechaRegistro.getText().toString();

                    HashMap<Object, Object> datosUsuario = new HashMap<>();
                    datosUsuario.put("Uid", uidString); // uid del usuario
                    datosUsuario.put("Email", emailString); // email del usuario
                    datosUsuario.put("Password", passwordString);   // password del usuario
                    datosUsuario.put("Nombre", nombreString);     // nombre del usuario
                    datosUsuario.put("Zombies", cantidadZombiesEliminados); // cantidad de zombies eliminados por el usuario
                    datosUsuario.put("Fecha", fechaString);      // fecha de registro del usuario

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference( ).child("USUARIOS");
                    reference.child(uidString).setValue(datosUsuario);
                    // Una vez registrado el usuario, se cambia de actividad a la del menu
                    startActivity(new Intent(Registro.this, MenuJuego.class));
                    Toast.makeText(Registro.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    finish();

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
}