package com.example.shopbee.ui.profile.myorder.typeOrderFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.OrderProductItem;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.TypeOrderFragmentBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.OrderProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class TypeOrderFragment extends BaseFragment<TypeOrderFragmentBinding, TypeOrderViewModel> implements TypeOrderNavigator, OrderProductAdapter.Listener{
    private ListOrderResponse listOrderResponse;
    private List<OrderProductItem> orderProductItemList;
    private UserResponse userResponse;
    private String statusOfOrder;
    TypeOrderFragmentBinding binding;
    public static TypeOrderFragment newInstance(String orderType) {
        TypeOrderFragment fragment = new TypeOrderFragment();
        Bundle args = new Bundle();
        args.putString("statusOfOrder", orderType);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {return R.layout.type_order_fragment;}

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() == null) throw new RuntimeException("Arguments of TypeOrderFragment cannot be null");
        statusOfOrder = getArguments().getString("statusOfOrder");
        binding = getViewDataBinding();
        loadRealtimeData();
        setUpData();

        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(orderProductItemList, this);
        RecyclerView recyclerView = binding.recyclerViewCancelled;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderProductAdapter);

        return binding.getRoot();
    }

    private void setUpData() {
        orderProductItemList = new ArrayList<>();
        for (int i = 0; i < listOrderResponse.getList_order().size(); i++){
            if (listOrderResponse.getList_order().get(i).getStatus().equals(statusOfOrder)){
                OrderResponse orderResponse = listOrderResponse.getList_order().get(i);
                orderProductItemList.add(new OrderProductItem(i, orderResponse.getOrder_number(), orderResponse.getDate(), orderResponse.getTracking_number(), orderResponse.getQuantity(), orderResponse.getTotal_amount(), orderResponse.getStatus()));
            }
        }
    }

    private void loadRealtimeData() {
        viewModel.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse = response;
            }
        });
        viewModel.getOrderResponse().observeForever(new Observer<ListOrderResponse>() {
            @Override
            public void onChanged(ListOrderResponse responses) {
                listOrderResponse = responses;
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        OrderProductItem orderProductItem = orderProductItemList.get(position);
        navigateToOrderDetailFragment(orderProductItem.getPosition());
    }

    @Override
    public void navigateToOrderDetailFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("status", statusOfOrder);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.myOrderDetailFragment, bundle);
    }
}
