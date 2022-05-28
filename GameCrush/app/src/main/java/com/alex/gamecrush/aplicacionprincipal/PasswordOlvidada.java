package com.alex.gamecrush.aplicacionprincipal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.gamecrush.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordOlvidada extends AppCompatActivity {

    private EditText emailRecuperacion;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_olvidada);
        emailRecuperacion = findViewById(R.id.emailRecuperacion);
        resetPasswordButton = findViewById(R.id.botonRecuperacion);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();


        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailRecuperacion.getText().toString().trim();
        if (email.isEmpty()) {
            emailRecuperacion.setError("Correo es requerido!");
            emailRecuperacion.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRecuperacion.setError("Por favor introduce un correo válido!");
            emailRecuperacion.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        progressBar.incrementProgressBy(100);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PasswordOlvidada.this, "Mira tu correo electrónico para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PasswordOlvidada.this, "Hubo un error recuperando la contraseña :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}