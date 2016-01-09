package me.honge.demo05_mvp.data.model;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by hong on 16/1/5.
 */
public class GoodsResult extends GoodsBase{
    public ArrayList<GoodsModel> results;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(results);
    }

    public GoodsResult() {
    }

    protected GoodsResult(Parcel in) {
        super(in);
        this.results = in.createTypedArrayList(GoodsModel.CREATOR);
    }

    public static final Creator<GoodsResult> CREATOR = new Creator<GoodsResult>() {
        public GoodsResult createFromParcel(Parcel source) {
            return new GoodsResult(source);
        }

        public GoodsResult[] newArray(int size) {
            return new GoodsResult[size];
        }
    };
}
