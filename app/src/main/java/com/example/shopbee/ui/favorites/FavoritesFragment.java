package com.example.shopbee.ui.favorites;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.FavoritesBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapter;
import com.example.shopbee.ui.favorites.adapter.FavoriteAdapterGridView;
import com.example.shopbee.ui.login.LoginActivity;
import com.example.shopbee.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesFragment extends BaseFragment<FavoritesBinding, FavoritesViewModel> implements  FavoritesNavigator, DialogsManager.Listener, FavoriteAdapter.OnItemClickListener {
    @Inject
    DialogsManager dialogsManager;
    FavoriteAdapter productAdapter = new FavoriteAdapter();
    FavoriteAdapterGridView productAdapterGridView = new FavoriteAdapterGridView();
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
        if (viewModel.getRepository().getUserResponse() != null) {
            viewModel.syncFavoriteLists();
            viewModel.getFavoriteLists().observe(getViewLifecycleOwner(), lists ->{
                if (lists.isEmpty()) {
                    binding.emptyFavorites.setVisibility(View.VISIBLE);
                }
                else {
                    binding.emptyFavorites.setVisibility(View.GONE);
//                    productAdapter = new FavoriteAdapter();
                    productAdapter.setOnItemClickListener(this);

//                    productAdapterGridView = new FavoriteAdapterGridView();
                    productAdapterGridView.setOnItemClickListener(this);

//        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    changeView(isInListView, null);

                    viewModel.getFavoriteProducts().observe(getViewLifecycleOwner(), products -> {
                        changeView(isInListView, products);
                    });
                    viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
                        if (inProgress) {
                            binding.loading.setVisibility(View.VISIBLE);
                            animateLoading();
                        } else {
                            stopLoadingAnimations();
                            binding.loading.setVisibility(View.GONE);
                        }
                    });
//        viewModel.getFavoriteVariations().observe(getViewLifecycleOwner(), variations -> {
//            changeView(isInListView, viewModel.getFavoriteProducts().getValue());
//        });
//        binding.imageView.setVisibility(View.VISIBLE);
                    binding.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isInListView = !isInListView;
                            changeView(isInListView, viewModel.getFavoriteProducts().getValue());
                        }
                    });
                }
            });

        } else {
            getViewDataBinding().signIn.setVisibility(View.VISIBLE);
            getViewDataBinding().signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        return binding.getRoot();
    }

    public void animateLoading() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.loading1.getBackground();
        if (!animationDrawable1.isRunning()) {
            animationDrawable1.start();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) binding.loading2.getBackground();
        if (!animationDrawable2.isRunning()) {
            animationDrawable2.start();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) binding.loading3.getBackground();
        if (!animationDrawable3.isRunning()) {
            animationDrawable3.start();
        }

        AnimationDrawable animationDrawable4 = (AnimationDrawable) binding.loading4.getBackground();
        if (!animationDrawable4.isRunning()) {
            animationDrawable4.start();
        }
    }

    public void stopLoadingAnimations() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.loading1.getBackground();
        if (animationDrawable1.isRunning()) {
            animationDrawable1.stop();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) binding.loading2.getBackground();
        if (animationDrawable2.isRunning()) {
            animationDrawable2.stop();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) binding.loading3.getBackground();
        if (animationDrawable3.isRunning()) {
            animationDrawable3.stop();
        }

        AnimationDrawable animationDrawable4 = (AnimationDrawable) binding.loading4.getBackground();
        if (animationDrawable4.isRunning()) {
            animationDrawable4.stop();
        }
    }

    @Override
    public void onDialogEvent(Object event) {

    }
    public void changeView(boolean isInListView, List<AmazonProductDetailsResponse> products) {
        if (isInListView) {
            binding.imageView.setImageResource(R.drawable.grid_view_icon);

            productAdapter.setProducts(products);
            productAdapter.setVariations(viewModel.getFavoriteVariations().getValue());
            productAdapter.notifyDataSetChanged();
//            productAdapter.setOnItemClickListener(this::onItemClick);

            getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            getViewDataBinding().recyclerView.setAdapter(productAdapter);
        }
        else {
            binding.imageView.setImageResource(R.drawable.list_view_icon);

            productAdapterGridView.setProducts(products);
            productAdapterGridView.setVariations(viewModel.getFavoriteVariations().getValue());
            productAdapterGridView.notifyDataSetChanged();
//            productAdapterGridView.setOnItemClickListener(this::onItemClick);

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

    @Override
    public void onItemDeleteClick(String asin, List<Pair<String, String>> variation) {
        viewModel.getCompositeDisposable().add(viewModel.getRepository().deleteUserVariation(Repository.UserVariation.FAVORITE, asin, variation)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success -> {
                                    if (success) {
                                        viewModel.syncFavoriteListsOnly();
//                        viewModel.getBagProducts().setValue(viewModel.getBagProducts().getValue());
                                        // Handle successful deletion
                                    } else {
                                        // Handle failure
                                    }
                                },
                                error -> {

                                })
        );
    }

    @Override
    public void onAddToBagClick(String asin, List<Pair<String, String>> variation, ImageView imageView) {
        viewModel.getRepository().saveUserVariation(Repository.UserVariation.BAG, asin, variation, 1);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getBottomBar().animateAddToFavorite(imageView, mainActivity.findViewById(R.id.main), Repository.UserVariation.BAG);
    }
}
