package com.alex.gamecrush.aplicacionprincipal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alex.gamecrush.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Slider extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    private ViewPager viewPager;
    // consulta datos
    private SliderAdapter sliderAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference baseDeDatos;
    //
    ImageView flechaAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        viewPager = findViewById(R.id.slider);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        flechaAtras = findViewById(R.id.flechaSlider);

        flechaAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Slider.this, "Texto", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Slider.this, MainActivity.class));
                //finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
    }
}