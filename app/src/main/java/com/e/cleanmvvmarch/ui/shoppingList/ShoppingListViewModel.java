package com.e.cleanmvvmarch.ui.shoppingList;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cleanmvvmarch.data.model.Product;
import com.e.cleanmvvmarch.data.model.Products;
import com.e.cleanmvvmarch.data.rest.ShoppingRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ShoppingListViewModel extends ViewModel {
    private static final String TAG = ShoppingListViewModel.class.getSimpleName();

    private ShoppingRepository mShoppingRepository;
    private Context mContext;

    private CompositeDisposable mCompositeDisposable;

    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public ShoppingListViewModel(ShoppingRepository shoppingRepository, Context context) {
        mShoppingRepository = shoppingRepository;
        mContext = context;
        mCompositeDisposable = new CompositeDisposable();
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public LiveData<Boolean> getError() {
        return repoLoadError;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void fetchData() {
        loading.setValue(true);
        mCompositeDisposable.add(mShoppingRepository.getProducts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Products>() {
                    @Override
                    public void onSuccess(Products products) {
                        repoLoadError.setValue(false);
                        productList.setValue(products.products);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;

        }
    }
}
