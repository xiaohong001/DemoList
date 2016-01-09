package me.honge.demo05_mvp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.socks.library.KLog;

import me.honge.demo05_mvp.utils.commons.Constans;
import me.honge.demo05_mvp.injection.component.ApplicationComponent;
import me.honge.demo05_mvp.injection.component.DaggerApplicationComponent;
import me.honge.demo05_mvp.injection.module.ApplicationModule;

/**
 * Created by hong on 16/1/4.
 */
public class App extends Application {
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            KLog.init(true);
        } else {
            KLog.init(false);
        }
        initConstans();
    }

    private void initConstans() {
        Resources resources = getResources();
        Constans.TAG_ANDROID = resources.getString(R.string.tag_android);
        Constans.TAG_IOS = resources.getString(R.string.tag_ios);
        Constans.TAG_WEALFARE = resources.getString(R.string.tag_wealfare);
        Constans.STR_GANK = resources.getString(R.string.str_gank);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
