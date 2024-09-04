package com.example.shopbee.popup;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.example.shopbee.R;
import com.example.shopbee.ui.main.MainActivity;

public class MyBagMenu extends PopupMenu {
    public MyBagMenu(@NonNull Context context, @NonNull View anchor) {
        super(context, anchor);
        init();
    }

    public MyBagMenu(@NonNull Context context, @NonNull View anchor, int gravity) {
        super(context, anchor, gravity);
        init();
    }

    public MyBagMenu(@NonNull Context context, @NonNull View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {
        super(context, anchor, gravity, popupStyleAttr, popupStyleRes);
        init();
    }

    @Override
    public void show() {
        super.show();
    }

    public void init() {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, this.getMenu());
        this.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_to_favorites_popup) {
                    return true;
                }
                else if (item.getItemId() == R.id.delete_from_list_popup) {
                    return true;
                }
                return false;
            }
        });
    }
}
