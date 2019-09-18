package com.e.cleanmvvmarch.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.e.cleanmvvmarch.R;
import com.e.cleanmvvmarch.base.BaseActivity;
import com.e.cleanmvvmarch.ui.shoppingList.ShoppingListFragment;
public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new ShoppingListFragment()).commit();
    }

}

