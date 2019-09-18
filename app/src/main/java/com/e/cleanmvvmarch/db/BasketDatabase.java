package com.e.cleanmvvmarch.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.e.cleanmvvmarch.db.basket.BasketDao;
import com.e.cleanmvvmarch.db.basket.BasketProduct;

@Database(entities = {BasketProduct.class}, version = 2, exportSchema = false)
public abstract class BasketDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "shop.db";

    public abstract BasketDao getBasketDao();
}
