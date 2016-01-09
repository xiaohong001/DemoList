package me.honge.demo05_mvp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hong on 16/1/5.
 */
public class GoodsBase implements Parcelable{
    public boolean error;

    public GoodsBase() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(error ? (byte) 1 : (byte) 0);
    }

    protected GoodsBase(Parcel in) {
        this.error = in.readByte() != 0;
    }

    public static final Creator<GoodsBase> CREATOR = new Creator<GoodsBase>() {
        public GoodsBase createFromParcel(Parcel source) {
            return new GoodsBase(source);
        }

        public GoodsBase[] newArray(int size) {
            return new GoodsBase[size];
        }
    };
}
