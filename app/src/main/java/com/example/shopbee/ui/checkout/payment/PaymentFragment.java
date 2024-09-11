package com.example.shopbee.ui.checkout.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.PaymentResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.PaymentBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.checkout.adapter.PaymentAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.addNewCard.addNewCardEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PaymentFragment extends BaseFragment<PaymentBinding, PaymentViewModel> implements PaymentNavigator, PaymentAdapter.Listener, DialogsManager.Listener {
    private PaymentBinding binding;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;
    private PaymentAdapter paymentAdapter;
    private RecyclerView recyclerView;

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }

    @Inject
    DialogsManager dialogsManager;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.payment;
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
        loadRealtimeData();
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int positionDef = -1;
        for (int i = 0; i < userResponse.getPayment().size(); i++) {
            if (userResponse.getPayment().get(i).getDef()) {
                positionDef = i;
            }
        }
        paymentAdapter = new PaymentAdapter(this, userResponse.getPayment(), positionDef);
        recyclerView.setAdapter(paymentAdapter);
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCheckout();
            }
        });
        binding.addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsManager.addNewCardDialog();
            }
        });
        return binding.getRoot();
    }

    private void loadRealtimeData() {
        userResponse = viewModel.getUserResponse().getValue();
        listOrderResponse = viewModel.getOrderResponse().getValue();
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
    public void onClickItems(int position) {
        if (position == -1){
            for (int i = 0; i < userResponse.getPayment().size(); i++) {
                userResponse.getPayment().get(i).setDef(false);
            }
        } else {
            for (int i = 0; i < userResponse.getPayment().size(); i++) {
                userResponse.getPayment().get(i).setDef(false);
            }
            userResponse.getPayment().get(position).setDef(true);
        }
        viewModel.updateUserFirebase();
    }
    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof addNewCardEvent){
            addNewCardEvent mAddNewCardEvent = (addNewCardEvent) event;
            PaymentResponse newPayment = new PaymentResponse(mAddNewCardEvent.getCvv()
                    , mAddNewCardEvent.getDef(), mAddNewCardEvent.getExpiryDate()
                    , mAddNewCardEvent.getName(), mAddNewCardEvent.getCardNumber()
                    , mAddNewCardEvent.getType());
            if (userResponse.getPayment() == null){
                userResponse.setPayment(new ArrayList<>());
                userResponse.getPayment().add(newPayment);
            } else {
                if (mAddNewCardEvent.getDef()) {
                    for (int i = 0; i < userResponse.getPayment().size(); i++) {
                        userResponse.getPayment().get(i).setDef(false);
                    }
                }
                int sizeOfOldPayment = userResponse.getPayment().size();
                userResponse.getPayment().add(sizeOfOldPayment - 1, newPayment);
            }
            viewModel.updateUserFirebase();
        }
    }
    @Override
    public void backToCheckout() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }
}
