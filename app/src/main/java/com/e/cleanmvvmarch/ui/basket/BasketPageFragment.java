package com.e.cleanmvvmarch.ui.basket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cleanmvvmarch.R;
import com.e.cleanmvvmarch.base.BaseFragment;
import com.e.cleanmvvmarch.db.basket.BasketProduct;
import com.e.cleanmvvmarch.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class BasketPageFragment extends BaseFragment implements BasketItemActionListener{

    @BindView(R.id.trolley_list_recycler_view)
    RecyclerView trolley_list_recycler_view;
    @BindView(R.id.generic_error_text_view)
    TextView generic_error_text_view;
    @BindView(R.id.basket_page_loading_progress_view)
    View loadingView;
    @BindView(R.id.summary_layout)
    ConstraintLayout summary_layout;
    @BindView(R.id.checkout_button)
    Button checkout_button;
    @BindView(R.id.total_items_text_view)
    TextView total_items_text_view;
    @BindView(R.id.net_total_price_text_view)
    TextView net_total_price_text_view;


    public static BasketPageFragment getInstance(){
        return new BasketPageFragment();
    }

    @Inject
    ViewModelFactory viewModelFactory;

    private BasketPageViewModel basketPageViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_basket_page;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        basketPageViewModel = ViewModelProviders.of(this, viewModelFactory).get(BasketPageViewModel.class);

        basketPageViewModel.fetchData();

        trolley_list_recycler_view.setAdapter(new BasketListAdapter(basketPageViewModel, this, this));
        trolley_list_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        observableViewModel();

        checkout_button.setOnClickListener(checkout -> {
            Toast.makeText(getContext(), getResources().getString(R.string.to_be_continued), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void deleteProduct(BasketProduct product) {
        basketPageViewModel.deleteProduct(product);

    }

    private void observableViewModel(){
        basketPageViewModel.getProductList().observe(this, product->{
            if(product != null){
                if(product.size() > 0){
                    total_items_text_view.setText(getResources().getString(R.string.no_of_items, String.valueOf(basketPageViewModel.getProductCount())));
                    net_total_price_text_view.setText(getResources().getString(R.string.net_total, String.valueOf(basketPageViewModel.getNetAmount())));
                    summary_layout.setVisibility(View.VISIBLE);
                    trolley_list_recycler_view.setVisibility(View.VISIBLE);
                }
                else{
                    summary_layout.setVisibility(View.GONE);
                    generic_error_text_view.setText(basketPageViewModel.getContext().getResources().getString(R.string.no_items_error_statement));
                }
            }
        });

        basketPageViewModel.getError().observe(this, isError -> {
            if(isError != null && isError){
                generic_error_text_view.setVisibility(View.VISIBLE);
                trolley_list_recycler_view.setVisibility(View.GONE);
                generic_error_text_view.setText(basketPageViewModel.getContext().getResources().getString(R.string.generic_error_statement));
            } else {
                generic_error_text_view.setVisibility(View.GONE);
                generic_error_text_view.setText(null);
            }
        });

        basketPageViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    generic_error_text_view.setVisibility(View.GONE);
                    trolley_list_recycler_view.setVisibility(View.GONE);
                }
            }
        });

        basketPageViewModel.getNoItemsError().observe(this, noItems -> {
            if (noItems != null) if (noItems) {
                summary_layout.setVisibility(View.GONE);
                generic_error_text_view.setVisibility(View.VISIBLE);
                trolley_list_recycler_view.setVisibility(View.GONE);
                generic_error_text_view.setText(basketPageViewModel.getContext().getResources().getString(R.string.no_items_error_statement));
            } else {
                summary_layout.setVisibility(View.VISIBLE);
                generic_error_text_view.setVisibility(View.GONE);
                generic_error_text_view.setText(null);
            }
        });
    }
}
