package com.example.shopbee.ui.common.dialogs;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.fragment.app.FragmentManager;

import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryDialog;
import com.example.shopbee.ui.common.dialogs.imagepickerdialog.ImagePickerDialog;
import com.example.shopbee.ui.common.dialogs.imagepreviewdialog.ImagePreviewDialog;
import com.example.shopbee.ui.common.dialogs.optiondialog.OptionDialog;
import com.example.shopbee.ui.common.dialogs.twooptiondialog.TwoOptionDialog;
import com.example.shopbee.ui.common.dialogs.changePassword.changePassDialog;

import java.util.HashMap;
import java.util.List;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DialogsManager {
    Context context;
    final FragmentManager fragmentManager;

    public interface Listener {
        void onDialogEvent(Object event);
    }
    Set<Listener> mListener = new HashSet<>();
    public final void registerListener(Listener listener) {
        mListener.add(listener);
    }
    public final void unregisterListener(Listener listener) {
        mListener.remove(listener);
    }
    public final Set<Listener> getListeners() {
        return Collections.unmodifiableSet(mListener);
    }
    public void postEvent(Object event) {
        for (Listener listener : getListeners()) {
            listener.onDialogEvent(event);
        }
    }
    public DialogsManager(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }
    public void showYesNoDialog(String title, String message) {
        TwoOptionDialog dialog = TwoOptionDialog.newInstance(this, title, message, "Yes", "No");
        dialog.show(fragmentManager, "yes_no_dialog");
    }
    public void changePasswordDialog(String old_password, String full_name, String email) {
        changePassDialog dialog = changePassDialog.newInstance(this, old_password, full_name, email);
        dialog.show(fragmentManager, "change_password_dialog");
    }
    public void changeCountryDialog(String old_country, List<CountryRespone> listCountry){
        changeCountryDialog dialog = changeCountryDialog.newInstance(this, old_country, listCountry);
        dialog.show(fragmentManager, "change_country_dialog");
    }
    public void showOptionDialog(String name, String money, String urlImage, HashMap<String, List<AmazonProductDetailsResponse.Data.VariationDetail>> options) {
        OptionDialog dialog = OptionDialog.newInstance(this, name, money, urlImage, options);
        dialog.show(fragmentManager, "option_dialog");
    }
    public void showImagePickerDialog() {
        ImagePickerDialog dialog = ImagePickerDialog.newInstance(this);
        dialog.show(fragmentManager, "image_picker_dialog");
    }
    public void showImagePreviewDialog(Bitmap bitmap) {
        ImagePreviewDialog dialog = ImagePreviewDialog.newInstance(bitmap);
        dialog.show(fragmentManager, "image_preview_dialog");
    }
}
