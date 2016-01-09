package me.honge.demo05_mvp.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import me.honge.demo05_mvp.data.DataManger;
import me.honge.demo05_mvp.data.remote.APIService;
import me.honge.demo05_mvp.injection.ApplicationContext;
import me.honge.demo05_mvp.injection.module.ApplicationModule;

/**
 * 使用了Singleton注解，使其保证唯一性。
 * Components从根本上来说就是一个注入器，也可以说是Inject和Module的桥梁，它的主要作用就是连接这两个部分
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context contetxt();

    Application application();

    APIService apiService();

    DataManger dataManger();
}
