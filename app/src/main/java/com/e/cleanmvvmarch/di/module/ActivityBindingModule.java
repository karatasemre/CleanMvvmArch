package com.e.cleanmvvmarch.di.module;

import com.e.cleanmvvmarch.ui.main.MainActivity;
import com.e.cleanmvvmarch.ui.main.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
