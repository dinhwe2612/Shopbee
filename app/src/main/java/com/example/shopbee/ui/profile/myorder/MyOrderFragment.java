package com.example.shopbee.ui.profile.myorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.databinding.MyOrdersBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOrderFragment extends BaseFragment<MyOrdersBinding, MyOrderViewModel> implements MyOrderNavigator {
    MyOrdersBinding binding;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPage;
    private View mView;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ListOrderResponse listOrderResponse;
    private static final float padding = 32.0f;

    @Override
    public int getBindingVariable() {return BR.vm;}

    @Override
    public int getLayoutId() {return R.layout.my_orders;}

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        mTabLayout = binding.tabLayout;
        mViewPage = binding.viewPager;
        mView = binding.tabBackground;

        myViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPage.setAdapter(myViewPagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPage, (tab, position) -> {
            View tabView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_my_orders, null);
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
        mTabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout(){

                // Remove the listener to prevent multiple calls
                mTabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (mTabLayout.getTabCount() > 0 && mTabLayout.getTabAt(0).view != null) {
                    mView.setX(mTabLayout.getTabAt(0).view.getX() + padding);
                    mView.setY(355.0f);
                    mView.setVisibility(View.INVISIBLE);
                }
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToProfile();
            }
        });
    }

    @Override
    public void backToProfile() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }
}

