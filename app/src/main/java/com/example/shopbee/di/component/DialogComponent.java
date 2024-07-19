package com.example.shopbee.di.component;

import com.example.shopbee.di.module.DialogModule;
import com.example.shopbee.di.scope.DialogScope;

import dagger.Component;

/*
 * Author: rotbolt
 */

@DialogScope
@Component(modules = DialogModule.class, dependencies = AppComponent.class)
public interface DialogComponent {

}
