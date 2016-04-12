package me.honge.demo09_password.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import me.honge.demo09_password.data.local.DBHelper;
import rx.functions.Func1;

/**
 * Created by hong on 16/2/24.
 */
public class Group extends DBModel {
    public static final String FIELD_TABLENAME = "hgroup";
    public static final Func1<Cursor, Group> MAPPER = new Func1<Cursor, Group>() {
        @Override
        public Group call(Cursor cursor) {
            Group group = new Group();
            group.mId = DBHelper.getLong(cursor, FIELD_ID);
            group.mName = DBHelper.getString(cursor, FIELD_NAME);
            group.mAccountId = DBHelper.getLong(cursor, FIELD_ACCOUNT_ID);
            group.mCreateDate = DBHelper.getLong(cursor, FIELD_CREATE_DATE);
            group.mUpdateDate = DBHelper.getLong(cursor, FIELD_UPDATE_DATE);
            return group;
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

        public Builder accountId(long accountId) {
            values.put(FIELD_ACCOUNT_ID, accountId);
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
