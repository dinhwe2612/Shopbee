package com.example.shopbee.di.component;


import com.example.shopbee.di.module.FragmentModule;
import com.example.shopbee.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
}
