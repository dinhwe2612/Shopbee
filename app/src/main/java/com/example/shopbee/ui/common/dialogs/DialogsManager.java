package com.example.shopbee.ui.common.dialogs;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryDialog;
import com.example.shopbee.ui.common.dialogs.twooptiondialog.TwoOptionDialog;
import com.example.shopbee.ui.common.dialogs.changePassword.changePassDialog;

import java.util.List;

public class DialogsManager {
    Context context;
    final FragmentManager fragmentManager;
    public DialogsManager(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }
    public void showYesNoDialog(String title, String message) {
        TwoOptionDialog dialog = TwoOptionDialog.newInstance(title, message, "Yes", "No");
        dialog.show(fragmentManager, "yes_no_dialog");
    }
    public void changePasswordDialog(String old_password, String full_name, String email) {
        changePassDialog dialog = changePassDialog.newInstance(old_password, full_name, email);
        dialog.show(fragmentManager, "change_password_dialog");
    }
    public void changeCountryDialog(String old_country, List<CountryRespone> listCountry){
        changeCountryDialog dialog = changeCountryDialog.newInstance(old_country, listCountry);
        dialog.show(fragmentManager, "change_country_dialog");
    }
}
