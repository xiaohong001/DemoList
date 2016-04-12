package me.honge.demo09_password.data.model;

import java.io.Serializable;
/**
 * Created by hong on 16/2/24.
 */
public class DBModel extends BaseModel implements Serializable {
    public static final String FIELD_ID = "_id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ACCOUNT_ID = "account_id";
    public static final String FIELD_CREATE_DATE = "create_date";
    public static final String FIELD_UPDATE_DATE = "update_date";
    public long mId;
    public String mName;
    public long mAccountId;
    public long mCreateDate;
    public long mUpdateDate;

}
