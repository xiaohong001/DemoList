package me.honge.demo02_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by hong on 15/12/9.
 */
public class IntentServiceDemo extends IntentService {
    private static final String TAG = IntentServiceDemo.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServiceDemo() {
        super("IntentServiceDemo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String op = intent.getStringExtra("op");
        if (TextUtils.equals(op, "op1")) {
            Log.e(TAG, "op" + op);
        } else if (TextUtils.equals(op, "op2")) {
            Log.e(TAG, "op" + op);
        }

        SystemClock.sleep(2000);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        Log.e(TAG, "setIntentRedelivery: " + enabled);
        super.setIntentRedelivery(enabled);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "onStart: " + startId);
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return super.onBind(intent);
    }
}
