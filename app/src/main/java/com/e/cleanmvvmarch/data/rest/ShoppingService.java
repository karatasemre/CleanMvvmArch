package com.e.cleanmvvmarch.data.rest;

import com.e.cleanmvvmarch.data.model.Products;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ShoppingService {
    @GET(".")
    Single<Products> getProductList();
}
