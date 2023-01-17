package com.example.graphexample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    Resources resources;
    Bitmap image;
    Paint paint;
    float imageX, imageY;
    float touchX, touchY;
    float dx, dy;
    SurfaceThread thread;
    Sprite sprite;
    ArrayList<Sprite> sprites = new ArrayList<>();
    public MySurface(Context context) {
        super(context);
        resources = getResources();
        image = BitmapFactory.decodeResource(resources, R.drawable.sprites);
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        imageX = 200;
        imageY = 300;
        getHolder().addCallback(this); //активация интерфейса
        //sprite = new Sprite(image, this, imageX, imageY);
        sprites.add(new Sprite(image, this, imageX, imageY));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //imageX += dx;
        //imageY += dy;
        //canvas.drawBitmap(image, imageX, imageY, paint);
        for(Sprite sprite: sprites)
            sprite.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            sprites.add(new Sprite(image, this, touchX, touchY));
            for(Sprite sprite: sprites)
                sprite.calcSteps(touchX, touchY);
        }
        return true;
    }

    private void calcSteps(){
        dx = (touchX - imageX) / 500;
        dy = (touchY - imageY) / 500;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread = new SurfaceThread(this, getHolder());
        thread.setRun(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRun(false);
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
