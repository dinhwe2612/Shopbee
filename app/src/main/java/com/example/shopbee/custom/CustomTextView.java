package com.example.shopbee.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.shopbee.R;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView,
                0, 0);

        try {
            int textAppearanceResId = a.getResourceId(R.styleable.CustomTextView_customTextAppearance, 0);
            if (textAppearanceResId != 0) {
                setTextAppearance(context, textAppearanceResId);
            }
        } finally {
            a.recycle();
        }
    }
}
