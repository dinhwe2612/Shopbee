package com.example.shopbee.testUI;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopbee.R;
import com.example.shopbee.dialog.MyBottomSheetDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TestBottomSheetDialogActivity extends AppCompatActivity {
    MyBottomSheetDialogFragment bottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_bottom_sheet_dialog);
        bottomSheetDialogFragment = new MyBottomSheetDialogFragment();

        findViewById(R.id.show_bottom_sheet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

    }
}