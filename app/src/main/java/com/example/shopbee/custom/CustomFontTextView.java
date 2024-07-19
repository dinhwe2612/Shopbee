package com.example.shopbee.custom;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

public class CustomFontTextView extends AppCompatTextView {

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        public CustomTypefaceSpan(Context context, String family, int fontResId) {
            super(family);
            newType = ResourcesCompat.getFont(context, fontResId);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private static void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }

    public void setCustomText(String[] texts, int[] fontResIds, int[] sizes) {
        if (texts.length != fontResIds.length || texts.length != sizes.length) {
            throw new IllegalArgumentException("Texts, fontResIds, and sizes must have the same length");
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int start = 0;
        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];
            int fontResId = fontResIds[i];
            int size = sizes[i];

            ssb.append(text);
            ssb.setSpan(new CustomTypefaceSpan(getContext(), "sans-serif", fontResId), start, start + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new AbsoluteSizeSpan(size, true), start, start + text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            start += text.length();
        }

        setText(ssb);
    }
}
