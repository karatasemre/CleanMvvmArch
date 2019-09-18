package com.e.cleanmvvmarch.data.rest;

import com.e.cleanmvvmarch.data.model.Product;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShoppingRepository {

    private final ShoppingService shoppingService;

    @Inject
    public ShoppingRepository(ShoppingService shoppingService){
        this.shoppingService = shoppingService;
    }

    public Single<Product> getProducts(){
        return shoppingService.getProductList();
    }

}
