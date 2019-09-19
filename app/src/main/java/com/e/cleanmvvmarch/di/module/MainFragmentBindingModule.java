package com.e.cleanmvvmarch.di.module;

import com.e.cleanmvvmarch.ui.shopDetail.ProductDetailsFragment;
import com.e.cleanmvvmarch.ui.shoppingList.ShoppingListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ShoppingListFragment provideShoppingListFragment();

    @ContributesAndroidInjector
    abstract ProductDetailsFragment provideProductDetailsFragment();
}
