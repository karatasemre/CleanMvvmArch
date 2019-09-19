package com.e.cleanmvvmarch.ui.shopDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.e.cleanmvvmarch.R;
import com.e.cleanmvvmarch.base.BaseFragment;
import com.e.cleanmvvmarch.data.model.Product;
import com.e.cleanmvvmarch.ui.basket.BasketPageFragment;
import com.e.cleanmvvmarch.util.ViewModelFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class ProductDetailsFragment extends BaseFragment {

    @BindView(R.id.product_name_detail_text_view)
    TextView productNameDetailTextView;
    @BindView(R.id.product_price_detail_text_view)
    TextView productPriceDetailTextView;
    @BindView(R.id.product_imageView)
    ImageView productImageView;
    @BindView(R.id.add_remove_from_cart_Button)
    Button addRemoveFromCartButton;
    @BindView(R.id.basket_button)
    Button basketButton;

    private static final String PRODUCT_EXTRA = "product";

    private Product mProduct;

    @Inject
    ViewModelFactory viewModelFactory;

    private ProductDetailsViewModel productDetailsViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_product_details;
    }

    public static ProductDetailsFragment getInstance(Product product){
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        if(product != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable(PRODUCT_EXTRA, product);
            productDetailsFragment.setArguments(bundle);
        }
        return productDetailsFragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailsViewModel.class);
        mProduct = getArguments() != null && getArguments().containsKey(PRODUCT_EXTRA) ? getArguments().getParcelable(PRODUCT_EXTRA) : null;
        if(mProduct == null){
            return;
        }

        productDetailsViewModel.fetchData();

        //Update UI elements

        Picasso.get().load(mProduct.getImage_url()).into(productImageView);
        productNameDetailTextView.setText(mProduct.getName());
        productPriceDetailTextView.setText(mProduct.getPrice());

        observableViewModel();

        addRemoveFromCartButton.setOnClickListener(addToCartView -> {
            productDetailsViewModel.addItemToBasket(mProduct);
        });


        basketButton.setOnClickListener( trolleyButtonView -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.screenContainer, BasketPageFragment.getInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });



    }

    private void observableViewModel() {
        productDetailsViewModel.getProductList().observe(this, product -> {
            if(product != null){
                basketButton.setText(productDetailsViewModel.getmContext().getResources().getString(
                        R.string.checkout, String.valueOf(product.size())));
            }
        });
    }


}
