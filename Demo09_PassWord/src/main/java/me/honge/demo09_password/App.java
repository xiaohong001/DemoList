package me.honge.demo09_password;

import android.app.Application;

/**
 * Created by hong on 16/2/24.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getApplication() {
        return instance;
    }
}
