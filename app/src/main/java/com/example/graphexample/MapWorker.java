package com.example.graphexample;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Arrays;
import java.util.Random;

public class MapWorker {
    int wCanvas, hCanvas;
    Bitmap [] textures;
    int map[][];
    final int TEXTURE_SIZE = 32;
    Paint paint;
    Random random;
    int counter = 0;

    public MapWorker(int wCanvas, int hCanvas, Resources resources){
        this.wCanvas = wCanvas;
        this.hCanvas = hCanvas;
        random = new Random();
        //загрузка текстур
        textures = new Bitmap[5];
        textures[0] = BitmapFactory.decodeResource(resources, R.drawable.grass);
        textures[1] = BitmapFactory.decodeResource(resources, R.drawable.dirt);
        textures[2] = BitmapFactory.decodeResource(resources, R.drawable.brick);
        textures[3] = BitmapFactory.decodeResource(resources, R.drawable.water);
        textures[4] = BitmapFactory.decodeResource(resources, R.drawable.lawa);
        paint = new Paint();
        //генерация карты
        map = new int[hCanvas / TEXTURE_SIZE][wCanvas / TEXTURE_SIZE];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(j > map[i].length / 3 && j < 2 * map[i].length / 3)
                    map[i][j] = 0;
                else {
                    map[i][j] = random.nextInt(4) + 1;
                    //как генерить препятствия вне коридора
                }
            }
        }
    }

    public void draw(Canvas canvas){
        float x = 0, y = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                canvas.drawBitmap(textures[map[i][j]], x, y, paint);
                x += TEXTURE_SIZE;
            }
            y += TEXTURE_SIZE;
            x = 0;
        }
        counter++;
        if(counter == 10) {
            changeMap();
            counter = 0;
        }
    }

    private void changeMap(){
        //построчный сдвиг карты "вниз"
        for (int i = map.length - 2; i >= 0 ; i--) {
            map[i + 1] = map[i].clone();
        }
        //повторная генерация нулевой (верхней) строки
        for (int j = 0; j < map[0].length; j++) {
            if(j > map[0].length / 3 && j < 2 * map[0].length / 3)
                map[0][j] = 0;
            else {
                map[0][j] = random.nextInt(4) + 1;
                //как генерить препятствия вне коридора
            }
        }
    }
}
