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

    private EditText emailRecuperacionEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_olvidada);
        emailRecuperacionEditText = findViewById(R.id.emailRecuperacion);
        resetPasswordButton = findViewById(R.id.botonRecuperacion);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();


        resetPasswordButton.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email = emailRecuperacionEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailRecuperacionEditText.setError("Correo es requerido!");
            emailRecuperacionEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailRecuperacionEditText.setError("Por favor introduce un correo válido!");
            emailRecuperacionEditText.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);


        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PasswordOlvidada.this, "Mira tu correo electrónico para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(PasswordOlvidada.this, "Hubo un error recuperando la contraseña :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}