package com.example.shopbee.ui.common.dialogs.imagepickerdialog;

import android.graphics.Bitmap;
import android.net.Uri;

public class ImagePickerEvent {
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
