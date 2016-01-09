// IBinderPool.aidl
package me.honge.demo03_ipcdemo.binderpool;
//线程池
interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
