package me.honge.demo03_ipcdemo.binderpool;

import android.os.RemoteException;

/**
 * Created by hong on 15/12/13.
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
