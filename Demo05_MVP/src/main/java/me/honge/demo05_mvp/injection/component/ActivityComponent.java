package me.honge.demo05_mvp.injection.component;

import dagger.Component;
import me.honge.demo05_mvp.injection.PerActivity;
import me.honge.demo05_mvp.injection.module.ActivityModule;
import me.honge.demo05_mvp.ui.goods.GoodsListView;
import me.honge.demo05_mvp.ui.main.MainActivity;
import me.honge.demo05_mvp.ui.welfare.WelfareDetailActivity;
import me.honge.demo05_mvp.ui.welfare.WelfareView;

/**
 * Created by hong on 16/1/4.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //将依赖注入到需要的对象中
    void inject(MainActivity mainActivity);
    void inject(GoodsListView goodsListView);
    void inject(WelfareView welFareView);
}
