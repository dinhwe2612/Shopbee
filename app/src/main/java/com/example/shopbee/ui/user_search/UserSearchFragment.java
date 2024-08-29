package com.example.shopbee.ui.user_search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.databinding.SearchLayoutBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.user_search.adapter.HistoryAdapter;
import com.example.shopbee.ui.user_search.adapter.SuggestionAdapter;

public class UserSearchFragment extends BaseFragment<SearchLayoutBinding, UserSearchViewModel> implements UserSearchNavigator, HistoryAdapter.OnHistorySearchClick {
    SearchLayoutBinding binding;
    HistoryAdapter historyAdapter = new HistoryAdapter();
    SuggestionAdapter suggestionAdapter = new SuggestionAdapter();
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
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(historyAdapter);
        viewModel.syncSearchHistory();
        viewModel.setHistoryIsShort(true);
        viewModel.getHistoryIsShort().observe(getViewLifecycleOwner(), isShort -> {
                if (isShort) {
                    binding.textView1.setText("Click for more...");
                    observeShortList();
                }
                else {
                    binding.textView1.setText("Click to show less...");
                    observeFullList();
                }
        });
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
                clearSuggestions();
            }
        });
        binding.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getHistoryIsShort().getValue()) {
                    viewModel.setHistoryIsShort(false);
                }
                else {
                    viewModel.setHistoryIsShort(true);
                }
            }
        });
        binding.textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to use this method for suggestions
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Trigger suggestion logic here
                String userInput = s.toString();
                if (!userInput.isEmpty()) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.relativeLayout.setVisibility(View.GONE);
                    binding.recyclerView1.setVisibility(View.VISIBLE);
                    // Call a method to update suggestions based on the user input
                    updateSuggestions(userInput);
                } else {
                    // Clear suggestions if input is empty
                    clearSuggestions();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to use this method for suggestions
//                clearSuggestions();
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

    @Override
    public void onHistorySearchClick(String product_name) {
        navigateToSearchFragment(product_name);
    }

    public void observeShortList() {
        viewModel.getFullListOfSearchHistory().removeObservers(getViewLifecycleOwner());
        historyAdapter.setHistoryList(viewModel.getShortListOfSearchHistory().getValue());
        historyAdapter.notifyDataSetChanged();
        viewModel.getShortListOfSearchHistory().observe(getViewLifecycleOwner(), historyList -> {
            historyAdapter.setHistoryList(historyList);
            historyAdapter.notifyDataSetChanged();
        });
    }
    public void observeFullList() {
        viewModel.getShortListOfSearchHistory().removeObservers(getViewLifecycleOwner());
        historyAdapter.setHistoryList(viewModel.getFullListOfSearchHistory().getValue());
        historyAdapter.notifyDataSetChanged();
        viewModel.getFullListOfSearchHistory().observe(getViewLifecycleOwner(), historyList -> {
            historyAdapter.setHistoryList(historyList);
            historyAdapter.notifyDataSetChanged();
        });
    }
    private void updateSuggestions(String userInput) {
        // Here you would typically query your data source for suggestions matching the user input
//        List<String> suggestions = getSuggestionsFromDataSource(userInput);

        // Update your suggestions list (e.g., RecyclerView or ListView)
//        suggestionAdapter.setSuggestions(suggestions);
//        suggestionAdapter.notifyDataSetChanged();
    }

    private void clearSuggestions() {
        // Clear your suggestions list
//        suggestionAdapter.setSuggestions(new ArrayList<>());
//        suggestionAdapter.notifyDataSetChanged();
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.relativeLayout.setVisibility(View.VISIBLE);
        binding.recyclerView1.setVisibility(View.GONE);
    }
}
