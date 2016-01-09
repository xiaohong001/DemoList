package me.honge.demo03_ipcdemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by hong on 15/12/12.
 */
public class MessengerService extends Service {

    public static final int MESSAGE_FROM_CLIENT = 0x123;
    public static final int MESSAGE_FROM_SERVER = 0x456;
    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_FROM_CLIENT) {
                Log.e("handlerMessage", "client message:" + msg.getData().getString("msg"));
                Messenger messenger = msg.replyTo;
                Message message = Message.obtain(null,MESSAGE_FROM_SERVER);
                Bundle bundle = new Bundle();
                bundle.putString("msg","你好，我是服务器");
                message.setData(bundle);
                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return;
            }
            super.handleMessage(msg);
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("me.honge.ipc.permission.ACTION_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED){
            return null;
        }
        return messenger.getBinder();
    }
}
