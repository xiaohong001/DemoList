package me.honge.demo05_mvp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hong on 16/1/5.
 */
public class GoodsModel implements Parcelable{
    /**
     * "who": "andyiac",
     * "publishedAt": "2016-01-05T05:47:06.978Z",
     * "desc": "优化android studio编译效率的方法\n",
     * "type": "Android",
     * "url": "https://github.com/bboyfeiyu/android-tech-frontier/blob/master/issue-13/%E4%BC%98%E5%8C%96android-studio%E7%BC%96%E8%AF%91%E6%95%88%E7%8E%87%E7%9A%84%E6%96%B9%E6%B3%95.md",
     * "used": true,
     * "objectId": "568b327bcbc2e8a30103c8bb",
     * "createdAt": "2016-01-05T03:03:23.234Z",
     * "updatedAt": "2016-01-05T05:47:08.719Z"
     */
    public String who;
    public String publishedAt;
    public String desc;
    public String type;
    public String url;
    public boolean used;
    public String objectId;
    public String createdAt;
    public String updatedAt;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.who);
        dest.writeString(this.publishedAt);
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(used ? (byte) 1 : (byte) 0);
        dest.writeString(this.objectId);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public GoodsModel() {
    }

    protected GoodsModel(Parcel in) {
        this.who = in.readString();
        this.publishedAt = in.readString();
        this.desc = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.objectId = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<GoodsModel> CREATOR = new Creator<GoodsModel>() {
        public GoodsModel createFromParcel(Parcel source) {
            return new GoodsModel(source);
        }

        public GoodsModel[] newArray(int size) {
            return new GoodsModel[size];
        }
    };
}
