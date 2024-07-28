package com.example.shopbee.testUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopbee.R;
import com.example.shopbee.testUI.PagerAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TestTabLayoutActivity extends AppCompatActivity {
    private static final float padding = 32.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        View backgroundView = findViewById(R.id.tab_background);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_my_orders, null);
            tab.setCustomView(tabView);
            TextView tabText = tabView.findViewById(R.id.tab_text);
            switch (position) {
                case 0:
                    tabText.setText("Delivered");
                    break;
                case 1:
                    tabText.setText("Processing");
                    break;
                case 2:
                    tabText.setText("Cancelled");
                    break;
            }
        }).attach();

        // Listen for the layout being completed
        tabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // log all getX of the tabs
                Log.d("TestTabLayoutActivity", "onGlobalLayout: " + tabLayout.getTabAt(0).view.getX());
                Log.d("TestTabLayoutActivity", "onGlobalLayout: " + tabLayout.getTabAt(1).view.getX());
                Log.d("TestTabLayoutActivity", "onGlobalLayout: " + tabLayout.getTabAt(2).view.getX());


                // Remove the listener to prevent multiple calls
                tabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (tabLayout.getTabCount() > 0 && tabLayout.getTabAt(0).view != null) {
                    backgroundView.setX(tabLayout.getTabAt(0).view.getX() + padding);
                    backgroundView.setY(355.0f);
                    backgroundView.setVisibility(View.INVISIBLE);
                }

                viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        backgroundView.setVisibility(View.VISIBLE);
                        TabLayout.Tab tab = tabLayout.getTabAt(position);
                        if (tab != null && tab.view != null) {
                            tab.select();
                            // Custom animation logic here
                            ValueAnimator animator = ValueAnimator.ofFloat(backgroundView.getX(), tab.view.getX() + padding);
                            animator.addUpdateListener(animation -> {
                                float value = (float) animation.getAnimatedValue();
                                backgroundView.setX(value);
                            });
                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    // Make the background view invisible when the animation ends
                                    backgroundView.setVisibility(View.INVISIBLE);
                                }
                            });
                            animator.setDuration(300); // Duration of the animation in ms
                            animator.start();
                        }
                    }

                });
            }
        });
    }
}
