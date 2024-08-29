package com.example.shopbee.ui.user_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.SearchLayoutBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class UserSearchFragment extends BaseFragment<SearchLayoutBinding, UserSearchViewModel> implements UserSearchNavigator {
    SearchLayoutBinding binding;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_layout;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        binding.textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.textInputEditText.getText().toString().isEmpty()) {
                    viewModel.saveSearchHistory(binding.textInputEditText.getText().toString());
                    navigateToSearchFragment(binding.textInputEditText.getText().toString());
                }
            }
        });
        binding.textInputLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textInputLayout.clearFocus();
                binding.textInputEditText.clearFocus();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void navigateToSearchFragment(String searchText) {
        Bundle bundle = new Bundle();
        bundle.putString("product_name", searchText);
        NavController navController = NavHostFragment.findNavController(this);
        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.fade_out)
                .build();
        navController.navigate(R.id.searchFragment, bundle, navOptions);
    }
}
