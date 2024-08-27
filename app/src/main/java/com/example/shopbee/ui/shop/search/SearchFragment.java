package com.example.shopbee.ui.shop.search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.model.filter.ProductCondition;
import com.example.shopbee.data.model.filter.ProductCountry;
import com.example.shopbee.data.model.filter.ProductFilter;
import com.example.shopbee.data.model.filter.SortByChoice;
import com.example.shopbee.databinding.SearchCatalogNewBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.twooptiondialog.TwoOptionEvent;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.example.shopbee.ui.shop.search.adapter.ProductAdapter;
import com.example.shopbee.ui.shop.search.dialog.SortByDialog;
import com.example.shopbee.ui.shop.search.dialog.SortByEvent;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment<SearchCatalogNewBinding, SearchViewModel> implements SearchNavigator, DialogsManager.Listener {
    @Inject
    DialogsManager dialogsManager;
    private String category;
    public SearchFragment(String category) {
        super();
        this.category = category;
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_catalog_new;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductFilter productFilter = new ProductFilter();
        productFilter.setCategory_id(category);
        productFilter.setPage(1);
        productFilter.setProduct_country(ProductCountry.US);
        productFilter.setSort_by_choice(SortByChoice.RELEVANCE);
        productFilter.setProduct_condition(ProductCondition.ALL);
        assert viewModel != null;
        viewModel.syncProductsByCategory(productFilter.getProductFilter());
        getViewDataBinding().textView.setText("Category: " + CategoriesHashMap.getInstance().getCategories().get(category));
        getViewDataBinding().recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        viewModel.getCategoryProducts().observe(getViewLifecycleOwner(), products -> {
            ProductAdapter productAdapter = new ProductAdapter(products);
            getViewDataBinding().recyclerView1.setAdapter(productAdapter);
        });
        Animation clickAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_click_animation);
        getViewDataBinding().linearLayout2.setOnClickListener(new View.OnClickListener() {
            SortByDialog sortByDialog = SortByDialog.newInstance(dialogsManager);
            @Override
            public void onClick(View view) {
                Log.d("SearchFragment", "onClick: Clicked");
                getViewDataBinding().linearLayout2.startAnimation(clickAnimation);
                sortByDialog.show(requireActivity().getSupportFragmentManager(), sortByDialog.getTag());
            }
        });
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof SortByEvent) {

        }
    }
}
