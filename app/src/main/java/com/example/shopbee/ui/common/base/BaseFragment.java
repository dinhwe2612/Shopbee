package com.example.shopbee.ui.common.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.shopbee.BaseApplication;
import com.example.shopbee.di.component.DaggerFragmentComponent;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.di.module.FragmentModule;
import com.example.shopbee.ui.common.bottombar.BottomBarUserReactionImplementation;
import com.example.shopbee.ui.main.MainActivity;

import javax.inject.Inject;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private BaseActivity activity;
    private View rootView;
    private T viewDataBinding;

    @Inject
    protected V viewModel;

    public enum FragmentType {
        SELECT_HOME_ICON,
        SELECT_SHOP_ICON,
        SELECT_BAG_ICON,
        SELECT_FAVORITE_ICON,
        SELECT_PROFILE_ICON,
        NO_ACTION,
        HIDE_BOTTOM_BAR
    }
    @Inject
    protected BottomBarUserReactionImplementation bottomBar;

    public FragmentType getFragmentType() {
        return FragmentType.NO_ACTION;
    }

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
            activity.onFragmentAttached(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        rootView = viewDataBinding.getRoot();
        Log.d("onCreateView", this.getClass().getName());
        if (requireActivity() instanceof MainActivity) {
            if (getFragmentType() == BaseFragment.FragmentType.HIDE_BOTTOM_BAR) {
                bottomBar.hideBottomBar();
            } else {
                bottomBar.showBottomBar();
            }
        }
        return rootView;
    }

    @Override
    public void onDetach() {
        activity.onFragmentDetached(this);
        activity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDataBinding.setVariable(getBindingVariable(), viewModel);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.executePendingBindings();
        switch (getFragmentType()) {
            case SELECT_HOME_ICON:
                bottomBar.update(0);
                return;
            case SELECT_SHOP_ICON:
                bottomBar.update(1);
                return;
            case SELECT_BAG_ICON:
                bottomBar.update(2);
                return;
            case SELECT_FAVORITE_ICON:
                bottomBar.update(3);
                return;
            case SELECT_PROFILE_ICON:
                bottomBar.update(4);
                return;
            default:
                break;
        }
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public T getViewDataBinding() {
        return viewDataBinding;
    }

    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    public void openActivityOnTokenExpire() {
        if (activity != null) {
            activity.openActivityOnTokenExpire();
        }
    }

    public abstract void performDependencyInjection(FragmentComponent buildComponent);


    private FragmentComponent getBuildComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(((BaseApplication)(requireContext().getApplicationContext())).appComponent)
                .activityComponent(((BaseActivity<?, ?>) requireActivity()).activityComponent)
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    public interface Callback {

        void onFragmentAttached(BaseFragment fragment);

        void onFragmentDetached(BaseFragment fragment);
    }
}