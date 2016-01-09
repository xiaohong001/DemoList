package me.honge.demo03_ipcdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hong on 15/12/12.
 */
public class User implements Parcelable{
    public int userId;
    public String userName;
    public Book book;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeParcelable(this.book, 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.book = in.readParcelable(Book.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
