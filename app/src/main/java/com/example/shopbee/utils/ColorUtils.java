package com.example.shopbee.utils;

import android.graphics.Color;

public final class ColorUtils {
    private ColorUtils(){}
    public static int adjustAlpha(int color, float percent) {
        int alpha = Math.round(Color.alpha(color) * percent);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static int interpolateColor(int startColor, int endColor, float fraction) {
        int startAlpha = Color.alpha(startColor);
        int startRed = Color.red(startColor);
        int startGreen = Color.green(startColor);
        int startBlue = Color.blue(startColor);

        int endAlpha = Color.alpha(endColor);
        int endRed = Color.red(endColor);
        int endGreen = Color.green(endColor);
        int endBlue = Color.blue(endColor);

        int alpha = (int) (startAlpha + fraction * (endAlpha - startAlpha));
        int red = (int) (startRed + fraction * (endRed - startRed));
        int green = (int) (startGreen + fraction * (endGreen - startGreen));
        int blue = (int) (startBlue + fraction * (endBlue - startBlue));

        return Color.argb(alpha, red, green, blue);
    }
}
