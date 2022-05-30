package com.alex.gamecrush.juegobird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.alex.gamecrush.R;

import java.util.ArrayList;
import java.util.BitSet;

public class GameView extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bird = new Bird();
        bird.setWidth(100 * MenuPrincipalBird.screenWidth / 1080);
        bird.setHeight(100 * MenuPrincipalBird.screenHeight / 1920);
        bird.setX(100 * MenuPrincipalBird.screenWidth / 1080);
        bird.setY(MenuPrincipalBird.screenHeight / 2 - bird.getHeight() / 2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        bird.setArrBms(arrBms);
        handler = new Handler();
        r = () -> invalidate();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        bird.draw(canvas);
       handler.postDelayed(r, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.setDrop(-15);
        }
        return true;
    }
}
