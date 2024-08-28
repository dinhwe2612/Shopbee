package com.example.shopbee.ui.shop.search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.example.shopbee.ui.shop.search.adapter.ProductAdapter;
import com.example.shopbee.ui.shop.search.adapter.ProductAdapterGridView;
import com.example.shopbee.ui.common.dialogs.sortbydialog.SortByDialog;
import com.example.shopbee.ui.common.dialogs.sortbydialog.SortByEvent;
import com.example.shopbee.ui.shop.search.dialog.filter.Filter;
import com.example.shopbee.ui.shop.search.dialog.filter.FilterDialog;
import com.example.shopbee.ui.shop.search.dialog.filter.FilterEvent;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment<SearchCatalogNewBinding, SearchViewModel> implements SearchNavigator, DialogsManager.Listener {
    int isInListView;
    @Inject
    DialogsManager dialogsManager;
    ProductFilter productFilter;
    private String category;
//    public SearchFragment(String category) {
//        super();
//        this.category = category;
//        isInListView = 1;
//    }
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
        isInListView = 1;
        category = getArguments().getString("category");
        productFilter = new ProductFilter();
        productFilter.setMin_price(1f);
        productFilter.setMax_price(1000f);
        productFilter.setProduct_condition(ProductCondition.ALL);
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
            if (isInListView == 1) {
                ProductAdapter productAdapter = new ProductAdapter(products);
                getViewDataBinding().recyclerView1.setAdapter(productAdapter);
            }
            else {
                ProductAdapterGridView productAdapterGridView = new ProductAdapterGridView(products);
                getViewDataBinding().recyclerView1.setAdapter(productAdapterGridView);
            }
        });
        Animation clickAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_click_animation);
        getViewDataBinding().linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortByDialog sortByDialog = SortByDialog.newInstance(dialogsManager, productFilter.getSort_by_choice());
                Log.d("SearchFragment", "onClick: Clicked");
                getViewDataBinding().linearLayout2.startAnimation(clickAnimation);
                sortByDialog.show(requireActivity().getSupportFragmentManager(), sortByDialog.getTag());
            }
        });
        getViewDataBinding().imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInListView == 1) {
                    getViewDataBinding().imageView.setImageResource(R.drawable.list_view_icon);
                    getViewDataBinding().recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    if (viewModel.getCategoryProducts().getValue() != null) {
                        getViewDataBinding().recyclerView1.setAdapter(new ProductAdapterGridView(viewModel.getCategoryProducts().getValue()));
                    }
                    isInListView = 0;
                }
                else {
                    getViewDataBinding().imageView.setImageResource(R.drawable.grid_view_icon);
                    getViewDataBinding().recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    if (viewModel.getCategoryProducts().getValue() != null) {
                        getViewDataBinding().recyclerView1.setAdapter(new ProductAdapter(viewModel.getCategoryProducts().getValue()));
                    }
                    isInListView = 1;
                }
            }
        });
        getViewDataBinding().linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialog filterDialog = FilterDialog.newInstance(dialogsManager, new Filter(productFilter.getMin_price(), productFilter.getMax_price(), productFilter.getProduct_condition()));
                Log.d("SearchFragment", "onClick: Clicked");
                getViewDataBinding().linearLayout.startAnimation(clickAnimation);
                filterDialog.show(requireActivity().getSupportFragmentManager(), filterDialog.getTag());
            }
        });
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
        if (event instanceof SortByEvent) {
            if (((SortByEvent) event).getSortByChoice() != productFilter.getSort_by_choice()) {
                productFilter.setSort_by_choice(((SortByEvent) event).getSortByChoice());
                viewModel.syncProductsByCategory(productFilter.getProductFilter());
                assert productFilter.getSortByChoiceMap() != null;
                getViewDataBinding().textView11.setText(productFilter.getSortByChoiceMap().get(productFilter.getSort_by_choice()));
            }
        }
        else if (event instanceof FilterEvent) {
            if (((FilterEvent) event).getFilter().getMin_price() != productFilter.getMin_price() || ((FilterEvent) event).getFilter().getMax_price() != productFilter.getMax_price() || ((FilterEvent) event).getFilter().getProductCondition() != productFilter.getProduct_condition()) {
                productFilter.setMin_price(((FilterEvent) event).getFilter().getMin_price());
                productFilter.setMax_price(((FilterEvent) event).getFilter().getMax_price());
                productFilter.setProduct_condition(((FilterEvent) event).getFilter().getProductCondition());
                viewModel.syncProductsByCategory(productFilter.getProductFilter());
            }
        }
    }
}
