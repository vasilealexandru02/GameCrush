package com.alex.gamecrush.aplicacionprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

public class Login extends AppCompatActivity {

    private EditText emailLoginEditText;
    private EditText passwordLoginEditText;

    private TextView passwordOlvidadaTextView;

    private Button botonLogin;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLoginEditText = findViewById(R.id.emailLogin);
        passwordLoginEditText = findViewById(R.id.passwordLogin);
        botonLogin = findViewById(R.id.botonLogin);
        auth = FirebaseAuth.getInstance();

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLoginEditText.getText().toString();
                String password = passwordLoginEditText.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailLoginEditText.setError("Correo no existente!");
                    emailLoginEditText.setFocusable(true);
                } else {
                    loginJugador(email, password);
                }
            }
        });

        passwordOlvidadaTextView = findViewById(R.id.passwordOlvidada);
        passwordOlvidadaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, PasswordOlvidada.class));
                finish();
            }
        });


    }

    /**
     * Realiza el login del jugador
     *
     * @param email
     * @param password
     */
    private void loginJugador(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    startActivity(new Intent(Login.this, Slider.class));
                    // overridePendingTransition(R.anim.slide_left, R.anim.slide_outright);
                    assert user != null;
                    Toast.makeText(Login.this, "Bienvenido a GameCrush!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right, R.anim.slide_outleft);
    }

}