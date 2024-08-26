package com.example.shopbee.ui.common.dialogs;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.shopbee.ui.common.dialogs.twooptiondialog.TwoOptionDialog;

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
}
