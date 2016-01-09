package me.honge.demo03_ipcdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import me.honge.demo03_ipcdemo.binderpool.BinderPool;
import me.honge.demo03_ipcdemo.binderpool.ComputeImpl;
import me.honge.demo03_ipcdemo.binderpool.ICompute;
import me.honge.demo03_ipcdemo.binderpool.ISecurityCenter;
import me.honge.demo03_ipcdemo.binderpool.SecurityCenterImpl;
import me.honge.demo03_ipcdemo.messenger.MessengerService;

public class MainActivity extends AppCompatActivity {
//    private Messenger mService;
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mService = new Messenger(service);
//            Message message = Message.obtain(null, MessengerService.MESSAGE_FROM_CLIENT);
//            Bundle bundle = new Bundle();
//            bundle.putString("msg","hello,this isclient");
//            message.setData(bundle);
//            //将接收的Messenger设置
//            message.replyTo = hanflerMessenger;
//            try {
//                mService.send(message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//    };
//
//    private Messenger hanflerMessenger = new Messenger(new MessengerHandler());
//    private static class MessengerHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == MessengerService.MESSAGE_FROM_SERVER) {
//                Log.e("handleMessage",msg.getData().getString("msg"));
//                return;
//            }
//            super.handleMessage(msg);
//        }
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this,MessengerService.class);
//        bindService(intent,connection,BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onDestroy() {
//        unbindService(connection);
//        super.onDestroy();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                testBinderPool();
            }
        }).start();
    }


    private void testBinderPool(){
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityCenterBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityCenterBinder);
        try {
            String password = securityCenter.encrypt("你好呀！");
            Log.e("MainActivity", password);
            String content = securityCenter.decrypt(password);
            Log.e("MainActivity", content);

        } catch (RemoteException e) {
            e.printStackTrace();
        }


        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute compute = ComputeImpl.asInterface(computeBinder);
        try {
            Log.e("MainActivity", "testBinderPool: "+compute.add(3,6));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
