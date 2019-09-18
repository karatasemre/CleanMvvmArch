package com.e.cleanmvvmarch.di.module;

import android.content.Context;

import androidx.room.Room;

import com.e.cleanmvvmarch.db.BasketDatabase;
import com.e.cleanmvvmarch.db.basket.BasketDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BasketDatabaseModule {
    @Singleton
    @Provides
    public BasketDatabase provideTvMazeDatabase(Context context){
        return Room.databaseBuilder(context, BasketDatabase.class, BasketDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    public BasketDao provideShowDao(BasketDatabase basketDatabase){
        return basketDatabase.getBasketDao();
    }
}
