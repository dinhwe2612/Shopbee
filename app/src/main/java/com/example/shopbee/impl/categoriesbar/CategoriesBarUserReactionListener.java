package com.example.shopbee.impl.categoriesbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface CategoriesBarUserReactionListener {
    void animateLine(TextView tag, View view, float viewX);
    void UIUnselected(TextView tag);
    void UISelected(TextView tag);
}
