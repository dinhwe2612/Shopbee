package com.example.shopbee.ui.flappybee;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Pipe {
    float positionX;
    float velocityX = 10;
    float accelerationX = 0.001f;
    float width = 10;
    float gap = 100;
    float topPipeHeight;
    float screenHeight;
    final int colorPipe = Color.parseColor("#C75B39");
    Pipe(float screenWidth, float screenHeight) {
        width = GameView.PIPE_WIDTH_RATIO * screenWidth;
        this.screenHeight = screenHeight;
        gap = GameView.BEE_WIDTH_RATIO * 3f * screenWidth;
        velocityX = 0.005f * screenWidth;
        topPipeHeight = (float)(Math.random() * (screenHeight - 2 * gap) + gap);
        positionX = screenWidth + width;
    }
    void update() {
        positionX -= velocityX;
        velocityX += accelerationX;
    }

    void draw(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setColor(colorPipe);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        // draw upper pipe
        canvas.drawBitmap(bitmap, positionX - width / 2f, topPipeHeight - screenHeight, null);
        // draw lower pipe
        canvas.drawBitmap(bitmap, positionX - width / 2f, topPipeHeight + gap, null);

    }

    public float getPositionX() {
        return positionX;
    }

    public float getWidth() {
        return width;
    }

    public float getGap() {
        return gap;
    }

    public float getTopPipeHeight() {
        return topPipeHeight;
    }
}
