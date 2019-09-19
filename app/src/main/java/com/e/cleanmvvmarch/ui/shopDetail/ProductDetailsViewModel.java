package com.e.cleanmvvmarch.ui.shopDetail;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cleanmvvmarch.data.model.Product;
import com.e.cleanmvvmarch.db.basket.BasketProduct;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailsViewModel extends ViewModel {

    private static final String TAG = ProductDetailsViewModel.class.getSimpleName();

    private BasketRepository mBasketRepository;

    private CompositeDisposable mCompositeDisposable;

    private Context mContext;

    private final MutableLiveData<List<BasketProduct>> productList = new MutableLiveData<>();

    @Inject
    ProductDetailsViewModel(BasketRepository basketRepository, Context context){
        mBasketRepository = basketRepository;
        mContext = context;
        mCompositeDisposable = new CompositeDisposable();
    }

    public Context getmContext(){
        return mContext;
    }

    LiveData<List<BasketProduct>> getProductList(){
        return productList;
    }

    void addItemToBasket(Product product){
        mBasketRepository.insertIntoBasket(product);
        fetchData();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    void fetchData(){
        mCompositeDisposable.add(mBasketRepository.getAllTrolleyProducts().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<BasketProduct>>() {
                    @Override
                    public void onSuccess(List<BasketProduct> basketProducts) {
                        productList.setValue(basketProducts);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }


}
