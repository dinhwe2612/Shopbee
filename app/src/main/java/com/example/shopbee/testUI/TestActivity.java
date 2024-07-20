package com.example.shopbee.testUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopbee.R;
import com.example.shopbee.bottombar.BottomBarUserReactionImplementation;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_bar);

        /*ImageView[] icons;
        icons = new ImageView[5];
        icons[0] = this.findViewById(R.id.homeIcon);
        icons[1] = this.findViewById(R.id.shopIcon);
        icons[2] = this.findViewById(R.id.bagIcon);
        icons[3] = this.findViewById(R.id.heartIcon);
        icons[4] = this.findViewById(R.id.profileIcon);
        TextView[] labels;
        labels = new TextView[5];
        labels[0] = this.findViewById(R.id.homeLabel);
        labels[1] = this.findViewById(R.id.shopLabel);
        labels[2] = this.findViewById(R.id.bagLabel);
        labels[3] = this.findViewById(R.id.heartLabel);
        labels[4] = this.findViewById(R.id.profileLabel);
        View animatedBackground = this.findViewById(R.id.animatedBackground);

        LinearLayout[] containers = new LinearLayout[5];
        containers[0] = this.findViewById(R.id.home);
        containers[1] = this.findViewById(R.id.shop);
        containers[2] = this.findViewById(R.id.bag);
        containers[3] = this.findViewById(R.id.heart);
        containers[4] = this.findViewById(R.id.profile);
        BottomBarUserReactionImplementation impl = new BottomBarUserReactionImplementation();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            containers[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    impl.onClick(icons, labels, animatedBackground, finalI);
                }
            });
        }*/

    }
}