package com.alex.gamecrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegistro;
    FirebaseAuth auth;
    EditText nombreUsuario;
    EditText emailUsuario;
    EditText passwordUsuario;
    TextView fechaRegistro;
    Button registrarUsuario;
    Button darDeAlta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        //
        nombreUsuario = findViewById(R.id.nombreUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);
        passwordUsuario = findViewById(R.id.passwordUsuario);
        fechaRegistro = findViewById(R.id.fechaRegistro);
        registrarUsuario = findViewById(R.id.registrarUsuario);
        auth = FirebaseAuth.getInstance();
        Date date = new Date();

        btnLogin.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Click LOGIN", Toast.LENGTH_SHORT).show();
            FirebaseUser usuario = auth.getCurrentUser();
            int cantidadZombiesEliminados = 0;
            assert usuario != null;
            String uidString = usuario.getUid();
            String emailString = "prueba";
            String passwordString = "prueba";
            String nombreString = "prueba";
            String fechaString = "fecha rpeuba";

            HashMap<Object, Object> datosUsuario = new HashMap<>();
            datosUsuario.put("Uid", uidString); // uid del usuario
            datosUsuario.put("Email", emailString); // email del usuario
            datosUsuario.put("Password", passwordString);   // password del usuario
            datosUsuario.put("Nombre", nombreString);     // nombre del usuario
            datosUsuario.put("Zombies", cantidadZombiesEliminados); // cantidad de zombies eliminados por el usuario
            datosUsuario.put("Fecha", fechaString);      // fecha de registro del usuario

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BASE DE DATOS");
            reference.push().setValue(datosUsuario);

        });

        btnRegistro.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Registro.class));
        });
    }
}