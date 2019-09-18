package com.e.cleanmvvmarch.db.basket;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BasketDao {

    @Query("SELECT * FROM basket_products")
    Single<List<BasketProduct>> getAllBasketProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BasketProduct basketProduct);

    @Delete
    void remove(BasketProduct basketProduct);

}
