package com.example.shopbee.ui.productdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ProductDetailBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;

import javax.inject.Inject;

public class ProductDetailFragment extends BaseFragment<ProductDetailBinding, ProductDetailViewModel> implements ProductDetailNavigator, DialogsManager.Listener{
    ProductDetailBinding binding;
    @Inject
    DialogsManager dialogsManager;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.product_detail;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        return binding.getRoot();
    }

    @Override
    public void addToBag() {
        Toast.makeText(getContext(), "Add to bag", Toast.LENGTH_SHORT).show();
        dialogsManager.showOptionDialog("ADD TO BAG", "$11", "null", null);
    }

    @Override
    public void buyNow() {
        Toast.makeText(getContext(), "Buy now", Toast.LENGTH_SHORT).show();
        dialogsManager.showOptionDialog("BUY NOW", "$11", "null", null);
    }

    @Override
    public void goToBag() {
        Toast.makeText(getContext(), "Go to bag", Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).navigate(R.id.bagFragment);
    }

    @Override
    public void addFavorite() {

    }

    @Override
    public void shareProduct() {

    }

    @Override
    public void option() {

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
    public void onDialogEvent(Object event) {

    }
}
