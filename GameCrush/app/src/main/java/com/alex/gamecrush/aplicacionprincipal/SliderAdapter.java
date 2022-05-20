package com.alex.gamecrush.aplicacionprincipal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.alex.gamecrush.R;
import com.alex.gamecrush.juegozombie.MenuPrincipalJuegoZombie;

public class SliderAdapter extends PagerAdapter {


    Context context;
    LayoutInflater layourInflater;
    Slider slider = new Slider();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] imagenes_slider = {
            R.drawable.zombie_logo,
            R.drawable.comingsoon_logo
    };
    public String[] titulos_slider = {
            "ZOMBIE KILLER",
            "PROXIMAMENTE..."
    };
    public String[] descripciones_slider = {
            "Primer juego diseñado en la aplicación de GameCrush, ¡machaca a esos zombies antes de que se acabe el tiempo!",
            "¡Próximamente más juegos disponibles en el lobby de GameCrush!"
    };

    @Override
    public int getCount() {
        return titulos_slider.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layourInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layourInflater.inflate(R.layout.slider_layout, container, false);
        ImageView imagenSlide = view.findViewById(R.id.imagenSlide);


        TextView tituloSlide = view.findViewById(R.id.tituloSlide);
        TextView descripcionSlide = view.findViewById(R.id.descripcionSlide);

        //
        imagenSlide.setImageResource(imagenes_slider[position]);
        tituloSlide.setText(titulos_slider[position]);
        descripcionSlide.setText(descripciones_slider[position]);
        container.addView(view);
        imagenSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(context, MenuPrincipalJuegoZombie.class);
                    context.startActivity(intent);
                    slider.finish();

                }
                if (position == 1) {
                    Toast.makeText(context, "Próximamente mas juegos... :)", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
