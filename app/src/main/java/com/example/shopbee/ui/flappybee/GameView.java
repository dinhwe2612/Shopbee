package com.example.shopbee.ui.flappybee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import com.example.shopbee.R;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public interface Listener {
        void updateScore();
        void endGame();
    }
    static public final Float PIPE_WIDTH_RATIO = 0.24f;
    static public final Float PIPE_HEIGHT_RATIO = 0.16f;
    static public final Float BEE_WIDTH_RATIO = 0.18f;
    static public final Float BEE_HEIGHT_RATIO = 0.18f;
    Bitmap pipeBitmap;
    Bitmap beeBitmap;
    Listener listener;
    Thread thread;
    private float measuredWidth;
    private float measuredHeight;
    private SurfaceHolder surfaceHolder;
    private int score = 0;
    boolean isJump = false;
    public int getScore() {
        return score;
    }
    Integer countRemove = 0;
    Integer curPipe = 0;
    Bee bee;
    private List<Pipe> pipeList;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet a) {
        super(context, a);
        init();
    }

    public GameView(Context context, AttributeSet a, int b) {
        super(context, a, b);
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);

        // For the pipes
        pipeList = new ArrayList<Pipe>();
        setKeepScreenOn(true);
    }
    void setListener(Listener listener) {
        this.listener = listener;
    }
    @Override
    public void run() {
        resetData();
        while(isAlive()) {
            update();
            draw();
            sleep();
        }
        listener.endGame();
        pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("FlappyBee", "Jump");
            bee.jump();
            return true;
        }
        return super.onTouchEvent(event);
    }

    void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        // Clear the canvas
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        // Draw the bird
        bee.draw(canvas, beeBitmap);
        // Draw the pipes
        List<Integer> removeList = new ArrayList<>();
        int size = pipeList.size();
        for (int index = 0; index < size; index++) {
            Pipe pipe = pipeList.get(index);
            if (isPipeOut(pipe)) {
                removeList.add(index);
            } else {
                // Draw the upper part of the pipe
                pipe.draw(canvas, pipeBitmap);
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    void update() {
        Log.d("FlappyBee", "bee " + bee.getVelocityY().toString());
        if (isJump) {
            Log.d("FlappyBee", "Jump");
            bee.jump();
            isJump = false;
        }
        // Update the data for the bird
        bee.update();
        // Update the data for the pipes
        if (countRemove != 0) {
            for (int i = 0; i < countRemove; i++) {
                pipeList.remove(0);
            }
            curPipe -= countRemove;
            countRemove = 0;
        }
        for (Pipe pipe : pipeList) {
            pipe.update();
        }
        if (!pipeList.isEmpty()) {
            Pipe lastPipe = pipeList.get(pipeList.size() - 1);
            if (lastPipe.getPositionX() + lastPipe.getWidth() / 2 < 0.7 * measuredWidth) {
                addPipe();
            }
        }
    }
    void resume() {
        thread = new Thread(this);
        thread.start();
    }

    void pause() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("FlappyBee", e.getMessage());
        }
    }

    void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Get the measured size of the view
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();

        beeBitmap = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(getContext(), R.drawable.bee), (int)(measuredWidth * BEE_WIDTH_RATIO), (int)(measuredWidth * BEE_HEIGHT_RATIO), false);
        bee = new Bee(measuredWidth, measuredHeight);

        pipeBitmap = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(getContext(), R.drawable.log), (int)(measuredWidth * PIPE_WIDTH_RATIO), (int)(measuredHeight), false);

        // Add the initial pipe
        addPipe();
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public boolean isAlive() {
        // Update the nearest pipe index
        for(; curPipe < pipeList.size(); curPipe++) {
            Pipe pipe = pipeList.get(curPipe);
            if (pipe.getPositionX() + pipe.getWidth() / 2f > bee.getPositionXmin()) {
                break;
            } else {
                // the bee passed the pipe
                listener.updateScore();
            }
        }
        // Check if the bird hits the pipes
        if (!pipeList.isEmpty() && bee.isCollidesWith(pipeList.get(curPipe))) {
            Log.d("FlappyBee", "Collides with pipe");
            return false;
        }

        // Check if the bird goes beyond the border
        if (bee.getPositionYmin() < 0.0f || bee.getPositionYmax() > measuredHeight) {
            Log.d("FlappyBee", "Out of border");
            return false;
        }

        return true;
    }
    private boolean isPipeOut(Pipe pipe) {
        return (pipe.getPositionX() + pipe.getWidth() / 2.0f) < 0.0f;
    }
    public void resetData() {
        // For the pipes
        pipeList = new ArrayList<>();
        curPipe = 0;
        countRemove = 0;
        score = 0;
        // Add the initial pipe
        addPipe();
        // reset bee
        beeBitmap = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(getContext(), R.drawable.bee), (int)(measuredWidth * BEE_WIDTH_RATIO), (int)(measuredWidth * BEE_HEIGHT_RATIO), false);
        bee = new Bee(measuredWidth, measuredHeight);
    }
    private void addPipe() {
        pipeList.add(new Pipe(measuredWidth, measuredHeight));
    }

}
