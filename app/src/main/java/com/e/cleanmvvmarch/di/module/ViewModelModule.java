package com.e.cleanmvvmarch.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.e.cleanmvvmarch.di.util.ViewModelKey;
import com.e.cleanmvvmarch.ui.basket.BasketPageViewModel;
import com.e.cleanmvvmarch.ui.shopDetail.ProductDetailsViewModel;
import com.e.cleanmvvmarch.ui.shoppingList.ShoppingListViewModel;
import com.e.cleanmvvmarch.util.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingListViewModel.class)
    abstract ViewModel bindShoppingListViewModel(ShoppingListViewModel shoppingListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel.class)
    abstract ViewModel bindProductDetailsViewModel(ProductDetailsViewModel productDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BasketPageViewModel.class)
    abstract ViewModel bindBasketPageViewModel(BasketPageViewModel basketPageViewModel);



    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
