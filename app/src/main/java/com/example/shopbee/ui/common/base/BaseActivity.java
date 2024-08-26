package com.example.shopbee.ui.common.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.shopbee.BaseApplication;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.di.component.DaggerActivityComponent;
import com.example.shopbee.di.module.ActivityModule;
import com.example.shopbee.toolbar.ToolbarView;
import com.example.shopbee.ui.login.LoginActivity;
import com.example.shopbee.utils.CommonUtils;

import javax.inject.Inject;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    public ActivityComponent activityComponent;
    private ProgressDialog progressDialog;
    ToolbarView toolbarView;
    private T viewDataBinding;

    @Inject
    protected V viewModel;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    public T getViewDataBinding() {
        return viewDataBinding;
    }

    public boolean hasPermission(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return CommonUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    protected ActivityComponent getBuildComponent() {
        return activityComponent = DaggerActivityComponent.builder()
                .appComponent(((BaseApplication)getApplication()).appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    public abstract void performDependencyInjection(ActivityComponent buildComponent);

    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }

    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        viewDataBinding.setVariable(getBindingVariable(), viewModel);
        viewDataBinding.executePendingBindings();
    }
}
