package com.e.cleanmvvmarch.di.component;

import android.app.Application;

import com.e.cleanmvvmarch.db.BasketDatabase;
import com.e.cleanmvvmarch.di.module.ActivityBindingModule;
import com.e.cleanmvvmarch.di.module.ApplicationModule;
import com.e.cleanmvvmarch.di.module.BasketDatabaseModule;
import com.e.cleanmvvmarch.di.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBindingModule.class, ApplicationModule.class, ContextModule.class, BasketDatabaseModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);


        ApplicationComponent build();
    }

}
