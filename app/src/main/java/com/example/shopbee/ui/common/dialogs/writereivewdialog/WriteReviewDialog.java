package com.example.shopbee.ui.common.dialogs.writereivewdialog;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.WriteReviewDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.writereivewdialog.adapter.ImagesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteReviewDialog extends DialogFragment implements ImagesAdapter.Listener {
    DialogsManager dialogsManager;
    public static WriteReviewDialog newInstance(DialogsManager dialogsManager) {
        WriteReviewDialog fragment = new WriteReviewDialog();
        fragment.dialogsManager = dialogsManager;
        return fragment;
    }
    WriteReviewDialogBinding binding;
    ImagesAdapter imagesAdapter;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = WriteReviewDialogBinding.inflate(getLayoutInflater());
        setOnClick();
        imagesAdapter  = new ImagesAdapter(this);
        binding.imagesRCV.setAdapter(imagesAdapter);
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600, getContext().getResources().getDisplayMetrics()));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationBottomSheetDialog;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
    }
    void setOnClick() {
        binding.sendButton.setOnClickListener(v -> {
            if (isValid()) {
                int rating = (int) binding.ratingBar.getRating();
                String reviewTitle = binding.reviewTitle.getText().toString();
                String reviewContent = binding.reviewContent.getText().toString();
                List<Bitmap> images = imagesAdapter.getImages();
                dialogsManager.postEvent(new WriteReviewEvent(rating, reviewTitle, reviewContent, images));
                dismiss();
            }
        });
        binding.close.setOnClickListener(v -> dismiss());
        binding.addImage.setOnClickListener(v -> {
            pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                    .build());
        });
    }
    boolean isValid() {
        if (binding.reviewTitle.getText().toString().length() < 5) {
            Toast.makeText(getContext(), "Review title must be at least 5 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.reviewContent.getText().toString().length() < 20) {
            Toast.makeText(getContext(), "Review must be at least 20 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.ratingBar.getRating() == 0) {
            Toast.makeText(getContext(), "Please rate the product", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), uris -> {
                // Callback is invoked after the user selects media items or closes the
                // photo picker.
                if (!uris.isEmpty()) {
                    Log.d("PhotoPicker", "Number of items selected: " + uris.size());
                    imagesAdapter.addImages(convertUriToBitmap(uris));

                } else {
                    Toast.makeText(getContext(), "No media selected", Toast.LENGTH_SHORT).show();
                }
            });
    List<Bitmap> convertUriToBitmap(List<Uri> uris) {
        List<Bitmap> bitmaps = new ArrayList<>();
        for (Uri uri : uris) {
            try {
                bitmaps.add(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri));
            } catch (IOException e) {
                Log.e("PhotoPicker", "Error converting URI to bitmap", e);
            }
        }
        return bitmaps;
    }

    @Override
    public void onItemClick(Bitmap bitmap) {
        dialogsManager.showImagePreviewDialog(bitmap);
    }
}
