package com.example.shopbee.ui.common.dialogs.optiondialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.OptionDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.optiondialog.adapter.VariationAdapter;
import com.example.shopbee.ui.common.dialogs.optiondialog.adapter.VariationDetailsAdapter;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class OptionDialog extends DialogFragment implements VariationAdapter.Listener {
    DialogsManager dialogsManager;
    OptionDialogBinding binding;
    VariationAdapter variationAdapter = new VariationAdapter(this);
    int quantity = 1;
    public static OptionDialog newInstance(DialogsManager dialogsManager, String name, String money, String urlImage, HashMap<String, List<AmazonProductDetailsResponse.Data.VariationDetail>> options) {
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("money", money);
        args.putString("urlImage", urlImage);
        args.putSerializable("options", options);
        OptionDialog fragment = new OptionDialog();
        fragment.setArguments(args);
        fragment.dialogsManager = dialogsManager;
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) throw new IllegalArgumentException("Arguments of OptionDialog cannot be null");
        binding = OptionDialogBinding.inflate(getLayoutInflater());
        bindData();
        setClickListener();
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, getContext().getResources().getDisplayMetrics()));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationBottomSheetDialog;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
    }
    void bindData() {
        binding.buttonText.setText(getArguments().getString("name"));
        binding.currentPrice.setText(getArguments().getString("money"));
        Disposable d = Observable.fromCallable(()->{
            FutureTarget<Bitmap> futureTarget = Glide.with(requireContext()).asBitmap().load(getArguments().getString("urlImage")).submit();
            return futureTarget.get();
        }).subscribe(bitmap -> {
            binding.image.setImageBitmap(bitmap);
        }, throwable -> {
            Log.e("OptionDialog", throwable.getMessage());
        });
        HashMap<String, List<AmazonProductDetailsResponse.Data.VariationDetail>> options = (HashMap<String, List<AmazonProductDetailsResponse.Data.VariationDetail>>) getArguments().getSerializable("options");
        variationAdapter.setVariations(options);
        binding.productOptRCV.setAdapter(variationAdapter);
        binding.productOptRCV.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
    }
    void setClickListener() {
        binding.button.setOnClickListener(v -> {
            OptionEvent event = new OptionEvent(null, 1);
            dialogsManager.postEvent(event);
            dismiss();
        });
        binding.minus.setOnClickListener(v -> {
            if (quantity > 1) {
                --quantity;
                binding.quanitynum.setText(String.valueOf(quantity));
                if (quantity == 1) {
                    binding.minus.setImageResource(R.drawable.icon_remove_inactive);
                }
            }
        });
        binding.plus.setOnClickListener(v -> {
            ++quantity;
            binding.quanitynum.setText(String.valueOf(quantity));
            if (quantity > 1) {
                binding.minus.setImageResource(R.drawable.icon_remove_active);
            }
        });
        binding.close.setOnClickListener(v -> dismiss());
    }

    @Override
    public void fullDetails() {

    }
}
