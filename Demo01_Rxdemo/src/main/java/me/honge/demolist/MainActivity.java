package me.honge.demolist;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import me.honge.demolist.widget.MyImageView;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test();
//        testImage();
        testImages();
    }

    private void testImages() {
        final MyImageView ivMyView = (MyImageView) findViewById(R.id.ivMyView);
        final Integer[] drawableIds = {
                R.mipmap.ic_launcher, R.mipmap.ic1, R.mipmap.ic2,
                R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5,
                R.mipmap.ic6, R.mipmap.ic_launcher, R.mipmap.ic1,
                R.mipmap.ic2, R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5,
                R.mipmap.ic6, R.mipmap.ic_launcher, R.mipmap.ic1,
                R.mipmap.ic2, R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5, R.mipmap.ic6,
                R.mipmap.ic_launcher, R.mipmap.ic1, R.mipmap.ic2,
                R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5,
                R.mipmap.ic6,R.mipmap.ic_launcher, R.mipmap.ic1, R.mipmap.ic2,
                R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5,
                R.mipmap.ic6,R.mipmap.ic_launcher, R.mipmap.ic1, R.mipmap.ic2,
                R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5,
                R.mipmap.ic6,R.mipmap.ic_launcher, R.mipmap.ic1, R.mipmap.ic2,
                R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5,
                R.mipmap.ic6,
        };

        Observable.from(drawableIds)
                .map(new Func1<Integer, Drawable>() {
                    @Override
                    public Drawable call(Integer integer) {
                        Log.e(TAG, "call: "+integer );
                        return getResources().getDrawable(integer);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                        ivMyView.postInvalidate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err",e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        Log.e(TAG, "onNext: "+drawable );
                        ivMyView.setDrawable(drawable);
                    }
                });
    }

    private void testImage() {
        final ImageView image = (ImageView) findViewById(R.id.ivImg);
        final int drawbleres = R.mipmap.ic_launcher;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawbleres);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())//指定subscribe发生在io线程中
                .observeOn(AndroidSchedulers.mainThread())//指定observe发生在main线程中
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        image.setImageDrawable(drawable);
                    }
                });
    }

    private void test() {
        //观察者
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext: " + o);
            }
        };

        String[] arr = {"Hello", "Hi", "Rx"};

        //被观察者，将arr数组内容拆分发送
        Observable observable = Observable.from(arr);

        //
//                Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello");
//                subscriber.onNext("Hi");
//                subscriber.onNext("Rx");
//                subscriber.onCompleted();
//            }
//        });

        //被观察者订阅观察者，然后依次向观察者发送信息
        observable.subscribe(observer);

        //打印字符串数组
        Observable.from(arr).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        });


    }
}
