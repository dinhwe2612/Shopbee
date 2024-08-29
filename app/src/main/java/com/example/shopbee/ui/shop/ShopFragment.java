package com.example.shopbee.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ShopBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.toolbar.ToolbarView;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.shop.adapter.CategoriesAdapter;
import com.example.shopbee.ui.shop.adapter.SubCategoriesAdapter;

import java.io.IOException;

public class ShopFragment extends BaseFragment<ShopBinding, ShopViewModel> implements ShopNavigator,ToolbarView.NavigateUpClickListener, ToolbarView.SearchClickListener {
    ToolbarView toolbarView;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shop;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewDataBinding().topBar.addView(toolbarView.getRootView());
        SubCategoriesAdapter subCategoriesAdapter;
        try {
            subCategoriesAdapter = new SubCategoriesAdapter(requireContext(), this);
            getViewDataBinding().recyclerView2.setAdapter(subCategoriesAdapter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getViewDataBinding().recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getViewDataBinding().recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        try {
            getViewDataBinding().recyclerView1.setAdapter(new CategoriesAdapter(requireContext(), new CategoriesAdapter.OnCategoryClickListener() {
                @Override
                public void onCategoryClick(String category) throws IOException {
                    if (subCategoriesAdapter != null) {
                        subCategoriesAdapter.notifyCategoryChanged(category);
                    }
                }
            }, this));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolbarView = new ToolbarView(inflater, container);
        toolbarView.setTitle("Categories");
        toolbarView.enableNavigateUp(this);
        toolbarView.setSearchClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onNavigateUpClick() {

    }

    @Override
    public void onSearchClick() {

    }

    @Override
    public void navigateToSearchByCategory(String category) {
//        NavController navController = NavHostFragment.findNavController(this);
//        navController.navigate(R.id.searchFragment);
//        SearchFragment searchFragment = new SearchFragment(category);
//        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(
//                R.id.fragmentContainer,
//                searchFragment
//        );
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        // using navigation graph to pass argument
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        navController.navigate(R.id.searchFragment, bundle);
    }
}
