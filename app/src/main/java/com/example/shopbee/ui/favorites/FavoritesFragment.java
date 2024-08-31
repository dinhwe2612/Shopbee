package com.example.shopbee.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.databinding.FavoritesBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapter;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapterGridView;

import java.util.List;

import javax.inject.Inject;

public class FavoritesFragment extends BaseFragment<FavoritesBinding, FavoritesViewModel> implements  FavoritesNavigator, DialogsManager.Listener, FavoriteAdapter.OnItemClickListener {
    @Inject
    DialogsManager dialogsManager;
    FavoriteAdapter productAdapter;
    FavoriteAdapterGridView productAdapterGridView;
    boolean isInListView = true;
    FavoritesBinding binding;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.favorites;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
//        viewModel.setGetLifeCycleOwner(new FavoritesViewModel.GetLifeCycleOwner() {
//            @Override
//            public LifecycleOwner getLifeCycleOwner() {
//                return this.getLifeCycleOwner();
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        dialogsManager.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        dialogsManager.unregisterListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.syncFavoriteLists();

        productAdapter = new FavoriteAdapter(null);
        productAdapter.setOnItemClickListener(this::onItemClick);
        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getViewDataBinding().recyclerView.setAdapter(productAdapter);

        viewModel.getFavoriteProducts().observe(getViewLifecycleOwner(), products -> {
            changeView(isInListView, products);
        });
        binding.imageView.setVisibility(View.VISIBLE);
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInListView = !isInListView;
                changeView(isInListView, viewModel.getFavoriteProducts().getValue());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDialogEvent(Object event) {

    }
    public void changeView(boolean isInListView, List<AmazonProductByCategoryResponse.Data.Product> products) {
        if (isInListView) {
            binding.imageView.setImageResource(R.drawable.list_view_icon);
            productAdapter = new FavoriteAdapter(products);
            productAdapter.setOnItemClickListener(this::onItemClick);
            getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            getViewDataBinding().recyclerView.setAdapter(productAdapter);
        }
        else {
            binding.imageView.setImageResource(R.drawable.grid_view_icon);
            productAdapterGridView = new FavoriteAdapterGridView(products);
            productAdapterGridView.setOnItemClickListener(this::onItemClick);
            getViewDataBinding().recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            getViewDataBinding().recyclerView.setAdapter(productAdapterGridView);
        }
    }

    @Override
    public void onItemClick(String asin) {
        Bundle bundle = new Bundle();
        bundle.putString("asin", asin);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.productDetailFragment, bundle);
    }
}
