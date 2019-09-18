package com.e.cleanmvvmarch.ui.shoppingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cleanmvvmarch.R;
import com.e.cleanmvvmarch.data.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListAdapter  extends RecyclerView.Adapter<ShoppingListAdapter.ProductViewHolder> {
    private final List<Product> data = new ArrayList<>();
    private ProductSelectionListener productSelectionListener;
    public ShoppingListViewModel mShoppingListViewModel;

    ShoppingListAdapter(ShoppingListViewModel shoppingListViewModel, LifecycleOwner lifecycleOwner, ProductSelectionListener productSelectionListener){
        this.mShoppingListViewModel = shoppingListViewModel;
        this.productSelectionListener = productSelectionListener;
        shoppingListViewModel.getProductList().observe(lifecycleOwner, product -> {
            data.clear();;
            if(product != null){
                data.addAll(product);
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shopping_list_adapter, parent, false);
        return new ProductViewHolder(view, productSelectionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(mShoppingListViewModel, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shopping_list_productname)
        TextView productNameTextView;

        @BindView(R.id.shopping_list_product_image)
        ImageView productImageView;

        @BindView(R.id.shopping_list_product_price)
        TextView productPrice;

        private Product product;

        ProductViewHolder(@NonNull View itemView, final ProductSelectionListener productSelectionListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if(this.product != null && productSelectionListener != null) {
                    productSelectionListener.onProductSelected(this.product);
                }
            });

        }

        void bind(ShoppingListViewModel shoppingListViewModel,Product product) {
            this.product = product;
            productNameTextView.setText(product.getName());
            productPrice.setText(shoppingListViewModel.getContext().getResources().getString(R.string.price_place_holder,product.getPrice()));
            Picasso.get().load(product.getImage_url()).into(productImageView);
        }
    }
}
