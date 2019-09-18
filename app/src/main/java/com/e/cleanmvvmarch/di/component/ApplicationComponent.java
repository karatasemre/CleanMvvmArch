package com.e.cleanmvvmarch.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    interface Builder {
        @BindsInstance
        Builder application(Application application);


        ApplicationComponent build();
    }

}
