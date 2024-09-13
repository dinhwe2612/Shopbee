package com.example.shopbee.ui.main;

import static com.example.shopbee.noti.NotificationScheduler.scheduleDailyNotification;
import static java.util.Map.entry;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.ActivityMainBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.noti.NotificationReceiver;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.bottombar.BottomBarUserReactionImplementation;
import com.example.shopbee.ui.common.bottombar.BottomBarUserReactionListener;
import com.example.shopbee.ui.common.base.BaseActivity;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator
                , BottomBarUserReactionListener {
    private NavController navController;
    ActivityMainBinding binding;
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
    @Inject
    DialogsManager dialogsManager;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        bottomBar.bindView(binding.bottomBar, this);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentContainer.getId());
        navController = navHostFragment.getNavController();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            viewModel.syncDataResponse(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        viewModel.getInProgress().observe(this, isLoading->{
            if (isLoading) {
                dialogsManager.showLoadingDialog();
            } else {
                dialogsManager.dismiss(dialogsManager.LOADING_DIALOG);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        createNotificationChannel(this);
        // get notifications state
//        SharedPreferences preferences = getSharedPreferences("NotificationPrefs", MODE_PRIVATE);
//        boolean isNotificationForSalesScheduled = preferences.getBoolean("isNotificationForSalesScheduled", false);

//        Log.d("NotificationScheduler", "Scheduling daily notification pending");
//        scheduleDailyNotification(this, 14, 35);

//        if (!isNotificationForSalesScheduled) {
//            scheduleDailyNotification(this, 15, 0);
//            scheduleDailyNotification(this, 18, 0);
//            scheduleDailyNotification(this, 21, 0);
//
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("isNotificationScheduled", true);
//            editor.apply();
//        }
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null && intent.getExtras().getBoolean("isFromNotifications")) {
                NavController navController = NavHostFragment.findNavController(getSupportFragmentManager().getFragments().get(0));
                navController.navigate(R.id.voucherFragment);
            }
        }
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();                     // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d("TAG", msg);
////                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position) {
        int fromFragmentId = NavGraph.findStartDestination(navController.getGraph()).getId();
        switch (position) {
            case 0:
                navigateWithOptions(fromFragmentId, R.id.homeFragment, navController);
                break;
            case 1:
                navigateWithOptions(fromFragmentId, R.id.shopFragment, navController);
                break;
            case 2:
                navigateWithOptions(fromFragmentId, R.id.bagFragment, navController);
                break;
            case 3:
                navigateWithOptions(fromFragmentId, R.id.favoritesFragment, navController);
                break;
            case 4:
                navigateWithOptions(fromFragmentId, R.id.profileFragment, navController);
                break;
        }
    }

    public void navigateWithOptions(int fromFragmentId, int toFragmentId, NavController navController) {
        // Set up navigation options
        NavOptions navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)  // Ensure we do not recreate the fragment if it's already on top
                .setRestoreState(true)     // Restore state of the destination fragment
                .setPopUpTo(fromFragmentId, false, true)  // Pop up to the fromFragmentId and remove all fragments above it
                .build();

        // Perform the navigation
        navController.navigate(toFragmentId, null, navOptions);

        Log.d("TAG", "navigateWithOptions: Navigated to " + toFragmentId + " from " + fromFragmentId);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can schedule notifications from here
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel";
            String description = "Channel for scheduled notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            if (notificationManager.getNotificationChannel("channel_id") == null) {
                notificationManager.createNotificationChannel(channel);
//            }
        }
    }

}