package me.honge.demo09_password.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import me.honge.demo09_password.data.local.DBHelper;
import rx.functions.Func1;

/**
 * Created by hong on 16/2/24.
 */
public class Account extends DBModel {
    public static final String FIELD_TABLENAME = "haccount";
    public static final Func1<Cursor, Account> MAPPER = new Func1<Cursor, Account>() {
        @Override
        public Account call(Cursor cursor) {
            Account account = new Account();
            account.mId = DBHelper.getLong(cursor, FIELD_ID);
            account.mName = DBHelper.getString(cursor, FIELD_NAME);
            account.mCreateDate = DBHelper.getLong(cursor, FIELD_CREATE_DATE);
            account.mUpdateDate = DBHelper.getLong(cursor, FIELD_UPDATE_DATE);
            return account;
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

        public ContentValues build() {
            return values;
        }
    }
}
