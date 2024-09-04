package com.example.shopbee.ui.common.dialogs.imagepickerdialog;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.databinding.ImagePickerDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImagePickerDialog extends DialogFragment {
    private static final int REQUEST_IMAGE_PICK = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;
    private Uri photoURI;
    DialogsManager dialogsManager;
    ImagePickerDialogBinding binding;

    public static ImagePickerDialog newInstance(DialogsManager dialogsManager) {
        ImagePickerDialog dialog = new ImagePickerDialog();
        dialog.dialogsManager = dialogsManager;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = ImagePickerDialogBinding.inflate(getLayoutInflater());

        binding.close.setOnClickListener(v -> dismiss());

        binding.gallery.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
        });

        binding.camera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request Camera Permission
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                // Permission already granted, open the camera
                openCamera();
            }
        });

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(binding.getRoot());
        return dialog;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(requireContext(), "Failed to create image file.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(requireContext(), "com.example.shopbee.fileprovider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(requireContext(), "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ImagePickerEvent event = new ImagePickerEvent();
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                // set bitmap
                event.setBitmap(getBitmapFromUri(data.getData()));
                dialogsManager.postEvent(event);
                dismiss();
                Log.d("ImagePickerDialog", "onActivityResult: Gallery");
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                event.setBitmap(getBitmapFromUri(data.getData()));
                dialogsManager.postEvent(event);
                dismiss();
                Log.d("ImagePickerDialog", "onActivityResult: Camera");
            }
            // Trigger your event or pass the path to where it needs to go
        } else {
            Log.d("ImagePickerDialog", "onActivityResult: Cancelled");
            dismiss();
        }
    }

    Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
        } catch (IOException e) {
            Log.e("ImagePickerDialog", "getBitmapFromUri: ", e);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the camera
                openCamera();
            } else {
                // Permission denied, show a message
                Toast.makeText(requireContext(), "Camera permission is required to take photos.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


