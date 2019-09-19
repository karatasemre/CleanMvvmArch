package com.e.cleanmvvmarch.ui.basket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cleanmvvmarch.R;
import com.e.cleanmvvmarch.db.basket.BasketProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasketListAdapter extends RecyclerView.Adapter<BasketListAdapter.ProductViewHolder> {

    private final List<BasketProduct> data = new ArrayList<>();
    private BasketItemActionListener basketItemActionListener;

    private BasketPageViewModel basketPageViewModel;


    BasketListAdapter(BasketPageViewModel basketPageViewModel, LifecycleOwner lifecycleOwner, BasketItemActionListener basketItemActionListener){

        this.basketItemActionListener = basketItemActionListener;
        this.basketPageViewModel = basketPageViewModel;
        basketPageViewModel.getProductList().observe(lifecycleOwner, product ->{
            data.clear();
            if(product != null){
                data.addAll(product);
                notifyDataSetChanged();
            }
        });

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_basket_list_adapter, parent, false);
        return new ProductViewHolder(view, basketItemActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(basketPageViewModel, data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trolley_list_productname)
        TextView productNameTextView;

        @BindView(R.id.trolley_list_product_image)
        ImageView productImageView;

        @BindView(R.id.trolley_list_product_price)
        TextView trolley_list_product_price;

        @BindView(R.id.trolley_list_item_delete)
        ImageView trolley_list_item_delete;

        private BasketProduct product;

        ProductViewHolder(@NonNull View itemView, final BasketItemActionListener trolleyItemActionListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            trolley_list_item_delete.setOnClickListener(v -> {
                if (this.product != null && trolleyItemActionListener != null) {
                    trolleyItemActionListener.deleteProduct(product);
                }
            });

        }

        void bind(BasketPageViewModel basketPageViewModel,BasketProduct product) {
            this.product = product;
            productNameTextView.setText(product.getName());
            trolley_list_product_price.setText(basketPageViewModel.getContext().getResources().getString(R.string.price_place_holder,String.valueOf(product.getPrice())));
            Picasso.get().load(product.getImageUrl()).into(productImageView);
        }
    }
}
