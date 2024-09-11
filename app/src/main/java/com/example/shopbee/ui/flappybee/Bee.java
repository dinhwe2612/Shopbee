package com.example.shopbee.ui.flappybee;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bee {
    float positionX = 0;
    float positionY = 0;
    float velocityY = 0;
    float accelerationY = 0.7f;
    float jumpVelocity = -15;
    float width = 100.0f;
    float height = 100.0f;
    float maxVelocityY = 0;
    float curAngle = 0;
    float angleVelocity = 1f;
    final float maxAngle = 38;
    Bee(float screenWidth, float screenHeight) {
        width = GameView.BEE_WIDTH_RATIO * screenWidth;
        height = GameView.BEE_WIDTH_RATIO * screenWidth;
        jumpVelocity = -0.006f * screenHeight;
        accelerationY = 0.0002f * screenHeight;
        positionX = screenWidth / 2f;
        positionY = screenHeight / 2f;
        maxVelocityY = 0.02f * screenHeight;
    }
    void jump() {
        velocityY = jumpVelocity;
        curAngle = -38;
    }
    void update() {
        positionY += velocityY;
        if (velocityY < maxVelocityY) {
            velocityY += accelerationY;
        }
        if (curAngle < maxAngle) {
            curAngle += angleVelocity;
        }
    }
    boolean isCollidesWith(Pipe pipe) {
        // top-left
        float bottomPipeY = pipe.getTopPipeHeight();
        float leftPipeX = pipe.getPositionX() - pipe.getWidth() / 2;
        float vectory = positionY - bottomPipeY;
        float vectorx = positionX - leftPipeX;
        float distance = (float) Math.sqrt(vectorx * vectorx + vectory * vectory);
        if (distance < width / 2) {
            return true;
        }
        // bottom-left
        float topPipeY = pipe.getTopPipeHeight() + pipe.getGap();
        vectory = positionY - topPipeY;
        distance = (float) Math.sqrt(vectorx * vectorx + vectory * vectory);
        if (distance < width / 2) {
            return true;
        }
        // x
        if (positionX > leftPipeX && positionX < leftPipeX + pipe.getWidth()) {
            if (positionY - width / 2 < bottomPipeY || positionY + width / 2 > topPipeY) {
                return true;
            }
        }
        // y
        if (positionY < bottomPipeY || positionY > topPipeY) {
            if (positionX + width / 2 > leftPipeX && positionX - width / 2 < leftPipeX + pipe.getWidth()) {
                return true;
            }
        }
        return false;
    }

    void draw(Canvas canvas, Bitmap bitmap) {
        canvas.save();
        canvas.rotate(curAngle, positionX, positionY);
        canvas.drawBitmap(bitmap, positionX - width / 2, positionY - height / 2, null);
        canvas.restore();
    }
    public float getPositionYmin() {
        return positionY - height / 2;
    }
    public float getPositionYmax() {
        return positionY + height / 2;
    }
    public float getPositionXmax() {
        return positionX + width / 2;
    }
    public float getPositionXmin() {
        return positionX - width / 2;
    }

    public Float getVelocityY() {
        return velocityY;
    }
}
