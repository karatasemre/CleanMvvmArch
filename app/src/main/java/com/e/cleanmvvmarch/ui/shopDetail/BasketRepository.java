package com.e.cleanmvvmarch.ui.shopDetail;

import com.e.cleanmvvmarch.data.model.Product;
import com.e.cleanmvvmarch.db.basket.BasketDao;
import com.e.cleanmvvmarch.db.basket.BasketProduct;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BasketRepository {
    private BasketDao basketDao;

    @Inject
    BasketRepository(BasketDao basketDao){
        this.basketDao = basketDao;
    }

    public Single<List<BasketProduct>> getAllTrolleyProducts() {
        return basketDao.getAllBasketProducts();
    }

    public void insertIntoBasket(Product product){
        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setName(product.getName());
        basketProduct.setImageUrl(product.getImage_url());
        basketProduct.setPrice(Float.parseFloat(product.getPrice()));
        basketProduct.setRating(product.getRating());
        basketDao.insert(basketProduct);

    }
    public void deleteProduct(BasketProduct basketProduct){
        basketDao.remove(basketProduct);
    }
}
