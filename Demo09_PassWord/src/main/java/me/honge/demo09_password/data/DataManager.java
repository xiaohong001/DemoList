package me.honge.demo09_password.data;

import android.content.ContentValues;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import me.honge.demo09_password.App;
import me.honge.demo09_password.data.config.Constans;
import me.honge.demo09_password.data.local.DBHelper;
import me.honge.demo09_password.data.model.Group;
import me.honge.demo09_password.data.model.Password;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hong on 16/2/24.
 */
public class DataManager {
    private static DataManager instance;
    private DBHelper dbHelper;
    private SqlBrite sqlBrite;
    private BriteDatabase db;

    private DataManager() {
        dbHelper = new DBHelper(App.getApplication());
        sqlBrite = SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                if (Constans.DEBUG) {
                    Log.e("DataBase", message);
                }
            }
        });
        db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        db.setLoggingEnabled(true);
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Observable<List<Group>> getGroupList() {
        return db.createQuery(Group.FIELD_TABLENAME,
                "SELECT * FROM " + Group.FIELD_TABLENAME)
                .mapToList(Group.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public int deleteGroup(long id) {
        return db.delete(Group.FIELD_TABLENAME, "_id=?", id + "");
    }

    public long insertGroup(ContentValues values) {
        return db.insert(Group.FIELD_TABLENAME, values);
    }

    public int updateGroup(long id, ContentValues values) {
        return db.update(Group.FIELD_TABLENAME, values, "_id=?", id + "");
    }


    public Observable<List<Password>> getPasswordList(int pageIndex, int pageCount) {
        return db.createQuery(Password.FIELD_TABLENAME,
                "SELECT * FROM " + Password.FIELD_TABLENAME + " LIMIT ?,?", ((pageIndex - 1) * pageCount) + "", pageCount + "")
                .mapToList(Password.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public int deletePassword(long id) {
        return db.delete(Password.FIELD_TABLENAME, "_id=?", id + "");
    }

    public long insertPassword(ContentValues values) {
        return db.insert(Password.FIELD_TABLENAME, values);
    }

    public int updatePassword(long id, ContentValues values) {
        return db.update(Password.FIELD_TABLENAME, values, "_id=?", id + "");
    }
}
