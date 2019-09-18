package com.e.cleanmvvmarch.ui.shoppingList;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.e.cleanmvvmarch.R;
import com.e.cleanmvvmarch.base.BaseFragment;
import com.e.cleanmvvmarch.data.model.Product;
import com.e.cleanmvvmarch.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class ShoppingListFragment extends BaseFragment implements ProductSelectionListener{

    @BindView(R.id.shopping_list_recycler_view)
    RecyclerView shoppingListRecyclerView;
    @BindView(R.id.generic_error_text_view)
    TextView genericErrorTextView;
    @BindView(R.id.loading_progress_view)
    View loadingView;

    private static final int NO_OF_COLUMNS = 2;

    @Inject
    ViewModelFactory viewModelFactory;

    private ShoppingListViewModel shoppingListViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_shopiing_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingListViewModel.class);
        shoppingListViewModel.fetchData();

        shoppingListRecyclerView.setAdapter(new ShoppingListAdapter(shoppingListViewModel, this, this));
        shoppingListRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(NO_OF_COLUMNS, StaggeredGridLayoutManager.VERTICAL));


    }

    private void observableViewModel(){
        shoppingListViewModel.getProductList().observe(this, products -> {
            if(products != null && products.size() > 0){
                shoppingListRecyclerView.setVisibility(View.VISIBLE);

            }
        });

        shoppingListViewModel.getError().observe(this, isError -> {
            if(isError && isError != null){
                genericErrorTextView.setVisibility(View.VISIBLE);
                shoppingListRecyclerView.setVisibility(View.GONE);
                genericErrorTextView.setText(shoppingListViewModel.getContext().getResources().getString(R.string.generic_error_statement));
            }
            else{
                genericErrorTextView.setVisibility(View.GONE);
                genericErrorTextView.setText(null);
            }
        });

        shoppingListViewModel.getLoading().observe(this, isLoading -> {
            if(isLoading != null){
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);

                if(isLoading){
                    genericErrorTextView.setVisibility(View.GONE);
                    shoppingListRecyclerView.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    public void onProductSelected(Product product) {
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.screenContainer, ProductDetailsFragment.getInstance(product));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }
}
