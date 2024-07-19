package com.example.shopbee.di.module;

import androidx.fragment.app.Fragment;

import dagger.Module;

@Module
public class FragmentModule {
    Fragment fragment;
    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }
}
