package com.kozhurov.project294.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

final class DataBaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Project_294";
    static final int DATABASE_VERSION = 1;

    //table income
    static final String TABLE_GENERAL_DEVICES = "general_device";
    static final String DEVICE_ID = "_id";
    static final String DEVICE_UUID = "device_uuid";
    static final String DEVICE_NAME = "device_name";
    static final String DEVICE_DESCR = "device_descr";
    static final String DEVICE_TYPE = "device_type";
    static final String DEVICE_STATE = "device_state";
    static final String DEVICE_PIC_URL = "device_pic_url";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(buildDevices());
    }

    private String buildDevices() {
        return "CREATE TABLE " + TABLE_GENERAL_DEVICES + " ( "
                + DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DEVICE_UUID + " text UNIQUE, "
                + DEVICE_NAME + " text, "
                + DEVICE_DESCR + " text, "
                + DEVICE_TYPE + " INTEGER, "
                + DEVICE_PIC_URL + " text, "
                + DEVICE_STATE + " INTEGER )";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}