package com.example.graphexample;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class SurfaceThread extends Thread{
    MySurface mySurface;
    SurfaceHolder holder;
    boolean isRun = false;
    long prevTime, nowTime, elapsedTime;

    public SurfaceThread(MySurface surface, SurfaceHolder holder){
        mySurface = surface;
        this.holder = holder;
        prevTime = System.currentTimeMillis();
    }
    public void setRun(boolean isRun){
        this.isRun = isRun;
    }
    @Override
    public void run() {
        while (isRun){
            Canvas canvas = null;
            nowTime = System.currentTimeMillis();
            elapsedTime = nowTime - prevTime;
            if(elapsedTime > 50) {
                prevTime = nowTime;
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    mySurface.draw(canvas);
                }
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
