package com.e.cleanmvvmarch.ui.basket;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cleanmvvmarch.db.basket.BasketProduct;
import com.e.cleanmvvmarch.ui.shopDetail.BasketRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BasketPageViewModel extends ViewModel {
    private static final String TAG = BasketPageViewModel.class.getSimpleName();

    private BasketRepository mBasketRepository;
    private CompositeDisposable mCompositeDisposable;
    private Context mContext;

    private final MutableLiveData<List<BasketProduct>> productList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> noItemsError = new MutableLiveData<>();

    @Inject
    BasketPageViewModel(BasketRepository basketRepository, Context context) {
        mContext = context;
        mBasketRepository = basketRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    public Context getContext(){
        return mContext;
    }

    LiveData<List<BasketProduct>> getProductList(){
        return productList;
    }

    LiveData<Boolean> getError(){
        return repoLoadError;
    }

    LiveData<Boolean> getLoading(){
        return loading;
    }

    LiveData<Boolean> getNoItemsError(){
        return noItemsError;
    }

    void fetchData(){
        mCompositeDisposable.add(mBasketRepository.getAllTrolleyProducts().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<BasketProduct>>() {
                    @Override
                    public void onSuccess(List<BasketProduct> basketProducts) {

                        repoLoadError.setValue(false);
                        loading.setValue(false);

                        if(basketProducts.size() == 0){
                            noItemsError.setValue(true);
                        }
                        else{
                            productList.setValue(basketProducts);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                        noItemsError.setValue(false);
                    }
                }));

    }

    void deleteProduct(BasketProduct basketProduct){
        mBasketRepository.deleteProduct(basketProduct);
        fetchData();
    }

    int getProductCount(){
        return productList.getValue() != null ? productList.getValue().size() : 0;
    }

    float getNetAmount(){
        if(productList.getValue() == null){
          return 0;
        }
        float totalAmount = 0;
        for(BasketProduct product : productList.getValue()){
            totalAmount += product.getPrice();
        }
        return totalAmount;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }
}
