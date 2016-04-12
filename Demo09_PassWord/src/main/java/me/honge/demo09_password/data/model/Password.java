package me.honge.demo09_password.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import me.honge.demo09_password.data.local.DBHelper;
import rx.functions.Func1;

/**
 * Created by hong on 16/2/24.
 */
public class Password extends DBModel {
    public static final String FIELD_TABLENAME = "hpassword";
    public static final String FIELD_GROUP_ID = "group_id";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_DATA1 = "data1";
    public static final String FIELD_DATA2 = "data2";
    public static final String FIELD_DATA3 = "data3";
    public static final String FIELD_DATA4 = "data4";
    public static final String FIELD_DATA5 = "data5";
    public static final String FIELD_DATA6 = "data6";

    public long groupId;
    public String password;// 账号密码
    public String reMark;// 备注
    public String data1;// 备用数据
    public String data2;
    public String data3;
    public String data4;
    public String data5;
    public String data6;

    public static final Func1<Cursor, Password> MAPPER = new Func1<Cursor, Password>() {
        @Override
        public Password call(Cursor cursor) {
            Password password = new Password();
            password.mId = DBHelper.getLong(cursor, FIELD_ID);
            password.mName = DBHelper.getString(cursor, FIELD_NAME);
            password.mAccountId = DBHelper.getLong(cursor, FIELD_ACCOUNT_ID);
            password.mCreateDate = DBHelper.getLong(cursor, FIELD_CREATE_DATE);
            password.mUpdateDate = DBHelper.getLong(cursor, FIELD_UPDATE_DATE);
            password.groupId = DBHelper.getLong(cursor, FIELD_GROUP_ID);
            password.password = DBHelper.getString(cursor, FIELD_PASSWORD);
            password.reMark = DBHelper.getString(cursor, FIELD_REMARK);
            password.data1 = DBHelper.getString(cursor, FIELD_DATA1);
            password.data2 = DBHelper.getString(cursor, FIELD_DATA2);
            password.data3 = DBHelper.getString(cursor, FIELD_DATA3);
            password.data4 = DBHelper.getString(cursor, FIELD_DATA4);
            password.data5 = DBHelper.getString(cursor, FIELD_DATA5);
            password.data6 = DBHelper.getString(cursor, FIELD_DATA6);
            return password;
        }
    };

    public static final Func1<Cursor,List<Password>> MAP = new Func1<Cursor, List<Password>>() {
        @Override
        public List<Password> call(Cursor cursor) {
            try {
                ArrayList<Password> passwords = new ArrayList<>();
                while (cursor.moveToNext()){
                    Password password = new Password();
                    password.mId = DBHelper.getLong(cursor, FIELD_ID);
                    password.mName = DBHelper.getString(cursor, FIELD_NAME);
                    password.mAccountId = DBHelper.getLong(cursor, FIELD_ACCOUNT_ID);
                    password.mCreateDate = DBHelper.getLong(cursor, FIELD_CREATE_DATE);
                    password.mUpdateDate = DBHelper.getLong(cursor, FIELD_UPDATE_DATE);
                    password.groupId = DBHelper.getLong(cursor, FIELD_GROUP_ID);
                    password.password = DBHelper.getString(cursor, FIELD_PASSWORD);
                    password.reMark = DBHelper.getString(cursor, FIELD_REMARK);
                    password.data1 = DBHelper.getString(cursor, FIELD_DATA1);
                    password.data2 = DBHelper.getString(cursor, FIELD_DATA2);
                    password.data3 = DBHelper.getString(cursor, FIELD_DATA3);
                    password.data4 = DBHelper.getString(cursor, FIELD_DATA4);
                    password.data5 = DBHelper.getString(cursor, FIELD_DATA5);
                    password.data6 = DBHelper.getString(cursor, FIELD_DATA6);
                    passwords.add(password);
                }
                return passwords;
            }finally {
                cursor.close();
            }
        }
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(FIELD_ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(FIELD_NAME, name);
            return this;
        }

        public Builder createDate(long createDate) {
            values.put(FIELD_CREATE_DATE, createDate);
            return this;
        }

        public Builder updateDate(long updateDate) {
            values.put(FIELD_UPDATE_DATE, updateDate);
            return this;
        }

        public Builder accountId(long accountId) {
            values.put(FIELD_ACCOUNT_ID, accountId);
            return this;
        }

        public Builder groupId(long groupId) {
            values.put(FIELD_GROUP_ID, groupId);
            return this;
        }

        public Builder password(String password) {
            values.put(FIELD_PASSWORD, password);
            return this;
        }

        public Builder reMark(String reMark) {
            values.put(FIELD_REMARK, reMark);
            return this;
        }

        public Builder data1(String data1) {
            values.put(FIELD_DATA1, data1);
            return this;
        }

        public Builder data2(String data2) {
            values.put(FIELD_DATA1, data2);
            return this;
        }

        public Builder data3(String data3) {
            values.put(FIELD_DATA1, data3);
            return this;
        }

        public Builder data4(String data4) {
            values.put(FIELD_DATA1, data4);
            return this;
        }

        public Builder data5(String data5) {
            values.put(FIELD_DATA1, data5);
            return this;
        }

        public Builder data6(String data6) {
            values.put(FIELD_DATA1, data6);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
