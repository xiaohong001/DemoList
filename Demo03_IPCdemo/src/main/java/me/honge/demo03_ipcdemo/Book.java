package me.honge.demo03_ipcdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hong on 15/12/12.
 */
public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    //返回当前对象的内容描述
    @Override
    public int describeContents() {
        return 0;
    }

    //将当前对象写入序列化结构中
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeString(this.bookName);
    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    //从序列化后的对象中创建原始对象
    protected Book(Parcel in) {
        this.bookId = in.readInt();
        this.bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        //从序列化后的对象中创建原始对象
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }
        //创建指定长度的原始对象数组
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
