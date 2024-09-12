package com.example.shopbee.ui.profile;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.ProfileBinding;
import com.example.shopbee.databinding.VoucherBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;
import com.example.shopbee.ui.voucher.adapter.VoucherAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends BaseFragment<ProfileBinding, ProfileViewModel> implements ProfileNavigator, ProfileAdapter.Listener {
    private ProfileBinding binding;
    private ProfileAdapter adapter;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private FirebaseAuth mAuth;

    Map<Integer, Runnable> triggered = Map.of(
            0, this::myOrder,
            1, this::shippingAddresses,
            2, this::paymentMethods,
            3, this::promocodes,
            4, this::myReviews,
            5, this::setting
    );
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.profile;
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.SELECT_PROFILE_ICON;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = ProfileBinding.inflate(LayoutInflater.from(getContext()));
        loadRealtimeData();
        if (userResponse == null){
            PopupDialog.getInstance(getContext())
                    .standardDialogBuilder()
                    .createStandardDialog()
                    .setHeading("LOGIN")
                    .setDescription("Login to save order and your information")
                    .setPositiveButtonText("Go to login")
                    .setPositiveButtonTextColor(R.color.white)
                    .setIcon(R.drawable.login_icon)
                    .build(new StandardDialogActionListener() {
                        @Override
                        public void onPositiveButtonClicked(Dialog dialog) {
                            navigateToLogin();
                        }

                        @Override
                        public void onNegativeButtonClicked(Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            binding.loginText.setVisibility(View.VISIBLE);
            binding.loginText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToLogin();
                }
            });
        } else {
            mAuth = FirebaseAuth.getInstance();
            binding.loginText.setText("Log out");
            binding.loginText.setVisibility(View.VISIBLE);
            viewModel.getAvatar().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap bitmap) {
                    if (bitmap == null){
                        binding.userAvatar.setImageResource(R.drawable.def_avatar);
                    } else {
                        binding.userAvatar.setImageBitmap(bitmap);
                    }
                }
            });
            binding.fullName.setText(userResponse.getFull_name());
            binding.email.setText(userResponse.getEmail());

            RecyclerView recyclerView = binding.recyclerViewProfile;
            adapter = new ProfileAdapter(this, getContentEachOption());
            adapter.setUpListProfileItem();
            recyclerView.setAdapter(adapter);

            binding.userAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence[] options = {"Take Photo", "Choose from Gallery"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Select an Option");
                    builder.setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            imagePickerLauncher.launch(takePictureIntent);
                            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                imagePickerLauncher.launch(takePictureIntent);
                            }
                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            imagePickerLauncher.launch(intent);
                        }
                    });
                    builder.show();
                }
            });
            imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            if (result.getData().getExtras() != null) {
                                Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                                binding.userAvatar.setImageBitmap(photo);
                                viewModel.uploadImageBitmapFirebase(photo, "avatar", userResponse.getEmail());
                            } else {
                                Uri imageUri = result.getData().getData();
                                try {
                                    Bitmap photo = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                                    binding.userAvatar.setImageBitmap(photo);
                                    viewModel.uploadImageBitmapFirebase(photo, "avatar", userResponse.getEmail());
                                } catch (IOException e) {
                                    Log.e(TAG, "Failed to load image from gallery", e);
                                }
                            }
                        }
                    });
            binding.loginText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    navigateToLogin();
                }
            });
        }
        return binding.getRoot();
    }
    @Override
    public void handleError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void myOrder() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_profileFragment_to_myOrderFragment);
    }

    @Override
    public void shippingAddresses() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.shippingFragment);
    }

    @Override
    public void paymentMethods() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.paymentFragment);
    }

    @Override
    public void promocodes() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.myVouchersFragment);
    }

    @Override
    public void myReviews() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.myReviewFragment);
    }

    @Override
    public void setting() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.settingsFragment);
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void navigateToLogin() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.login);
    }

    @Override
    public void onItemClicked(int position) {
        triggered.get(position).run();
    }
    public void loadRealtimeData(){
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
        viewModel.syncImageBitmapFirebase("avatar", userResponse.getEmail());

    }
    public List<String> getContentEachOption(){
        List<String> listContent = new ArrayList<>();
        if (userResponse == null){
            for (int i = 0; i < 6; i++) listContent.add("");
            return listContent;
        }
        if (listOrderResponse.getList_order().isEmpty()) listContent.add("You have no order yet.");
        else listContent.add("Already have " + String.valueOf(listOrderResponse.getList_order().size()) + " order(s)");
        if (userResponse.getAddress().isEmpty()){
            listContent.add("You have no address yet.");
        } else listContent.add(String.valueOf(userResponse.getAddress().size()) + " address(es)");
        if (userResponse.getPayment().isEmpty()){
            listContent.add("You have no payment method yet.");
        } else {
            listContent.add("You have " + String.valueOf(userResponse.getPayment().size()) + " payment method(s)");
        }
        listContent.add("You have special promocodes");
        listContent.add("Review for 3 item(s)");
        listContent.add("Notification, password");
        return listContent;
    }
}