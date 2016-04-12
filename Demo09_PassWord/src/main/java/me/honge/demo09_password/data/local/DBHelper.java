package me.honge.demo09_password.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.honge.demo09_password.data.model.Account;
import me.honge.demo09_password.data.model.Group;
import me.honge.demo09_password.data.model.Password;

/**
 * Created by hong on 16/2/24.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pw.db";
    private static final int VERSION = 1;
    private static final String CREATE_ACCOUNT = "CREATE TABLE "
            + Account.FIELD_TABLENAME + " ( "
            + Account.FIELD_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + Account.FIELD_NAME + " TEXT NOT NULL,"
            + Account.FIELD_CREATE_DATE + " INTEGER NOT NULL,"
            + Account.FIELD_UPDATE_DATE + " INTEGER NOT NULL"
            + ")";
    private static final String CREATE_GROUP = "CREATE TABLE "
            + Group.FIELD_TABLENAME + " ( "
            + Group.FIELD_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + Group.FIELD_NAME + " TEXT NOT NULL,"
            + Group.FIELD_ACCOUNT_ID + " INTEGER NOT NULL,"
            + Group.FIELD_CREATE_DATE + " INTEGER NOT NULL,"
            + Group.FIELD_UPDATE_DATE + " INTEGER NOT NULL"
            + ")";
    private static final String CREATE_PASS_WORD = "CREATE TABLE "
            + Password.FIELD_TABLENAME + " ( "
            + Password.FIELD_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + Password.FIELD_NAME + " TEXT NOT NULL,"
            + Password.FIELD_ACCOUNT_ID + " INTEGER NOT NULL,"
            + Password.FIELD_GROUP_ID + " INTEGER NOT NULL,"
            + Password.FIELD_PASSWORD + " TEXT NOT NULL,"
            + Password.FIELD_REMARK + " TEXT,"
            + Password.FIELD_DATA1 + " TEXT,"
            + Password.FIELD_DATA2 + " TEXT,"
            + Password.FIELD_DATA3 + " TEXT,"
            + Password.FIELD_DATA4 + " TEXT,"
            + Password.FIELD_DATA5 + " TEXT,"
            + Password.FIELD_DATA6 + " TEXT,"
            + Password.FIELD_CREATE_DATE + " INTEGER NOT NULL,"
            + Password.FIELD_UPDATE_DATE + " INTEGER NOT NULL"
            + ")";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_GROUP);
        db.execSQL(CREATE_PASS_WORD);

        long accountId = db.insert(Account.FIELD_TABLENAME, null, new Account.Builder()
                .name("honge")
                .createDate(System.currentTimeMillis())
                .updateDate(System.currentTimeMillis())
                .build());
        long groupId = db.insert(Group.FIELD_TABLENAME, null, new Group.Builder()
                .name("group")
                .accountId(accountId)
                .createDate(System.currentTimeMillis())
                .updateDate(System.currentTimeMillis())
                .build());
        for (int i = 0; i < 10; i++) {
            db.insert(Password.FIELD_TABLENAME, null, new Password.Builder()
                    .name("hello" + i)
                    .accountId(accountId)
                    .groupId(groupId)
                    .password("password")
                    .createDate(System.currentTimeMillis())
                    .updateDate(System.currentTimeMillis())
                    .build());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /*-------------------------------------------------
    *--------------------数据库辅助---------------------
    ------------------------------------------------- */

    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
}
