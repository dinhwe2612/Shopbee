package com.example.shopbee.ui.common.dialogs.imagepreviewdialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.shopbee.databinding.ImagePreviewDialogBinding;

public class ImagePreviewDialog extends DialogFragment {
    public static ImagePreviewDialog newInstance(Bitmap bitmap) {
        ImagePreviewDialog frag = new ImagePreviewDialog();
        Bundle args = new Bundle();
        args.putParcelable("bitmap", bitmap);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bitmap bitmap = getArguments().getParcelable("bitmap");
        ImagePreviewDialogBinding binding = ImagePreviewDialogBinding.inflate(getLayoutInflater());
        binding.image.setImageBitmap(bitmap);
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, getContext().getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getContext().getResources().getDisplayMetrics()));
        return dialog;
    }
}
