package com.example.shopbee.bottombar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface BottomBarUserReactionListener {
    void animateBackground(View view, int position);
    void UIUnselected(ImageView icon, TextView label, int position);
    void UISelected(ImageView icon, TextView label, int position);
    void onClick(ImageView[] icons, TextView[] labels, View view, int position);
}
