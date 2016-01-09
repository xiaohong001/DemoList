package me.honge.demo03_ipcdemo.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by hong on 15/12/13.
 */
public class BinderPoolService extends Service {

    private Binder binderPool = new BinderPool.BinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binderPool;
    }


}
