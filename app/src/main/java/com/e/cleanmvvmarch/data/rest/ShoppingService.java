package com.e.cleanmvvmarch.data.rest;

import com.e.cleanmvvmarch.data.model.Product;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ShoppingService {
    @GET(".")
    Single<Product> getProductList();
}
