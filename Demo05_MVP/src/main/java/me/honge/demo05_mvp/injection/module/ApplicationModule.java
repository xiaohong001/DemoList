package me.honge.demo05_mvp.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.honge.demo05_mvp.data.remote.APIService;
import me.honge.demo05_mvp.injection.ApplicationContext;

/**
 * Module //提供依赖
 * Provides //构造对象并提供这些依赖
 */
@Module
public class ApplicationModule   {
    protected final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application provideAppliction(){
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    APIService provideApiService(){
        return APIService.Creator.newAPIService();
    }
}
