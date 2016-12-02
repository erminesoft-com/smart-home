package com.kozhurov.project294.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kozhurov.project294.model.GeneralModel;
import com.kozhurov.project294.model.GeneralTransport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DbManager {

    private DataBaseHelper baseHelper;
    private Map<Long, GeneralTransport> waitQueue;


    public DbManager(Context context) {
        baseHelper = new DataBaseHelper(context);
        waitQueue = new HashMap<>();
    }

    public List<GeneralModel> getModels() {

        SQLiteDatabase database = baseHelper.getReadableDatabase();
        Cursor cursor = database.query(DataBaseHelper.TABLE_GENERAL_DEVICES, null, null, null, null, null, null);
        return DeviceMapper.cursorToModelList(cursor);
    }

    public void setModels(List<GeneralModel> models) {

    }

    public void saveModel(GeneralModel generalModel) {
        SQLiteDatabase database = baseHelper.getWritableDatabase();
        ContentValues values = DeviceMapper.modelToCv(generalModel);
        database.insertWithOnConflict(DataBaseHelper.TABLE_GENERAL_DEVICES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public GeneralModel getModelById(String modelUUID) {
        SQLiteDatabase database = baseHelper.getReadableDatabase();
        String where = DataBaseHelper.DEVICE_UUID + " = ?";
        String[] whereArgs = new String[]{modelUUID};
        Cursor cursor = database.query(DataBaseHelper.TABLE_GENERAL_DEVICES, null, where, whereArgs, null, null, null);

        GeneralModel model = null;
        if (cursor.moveToFirst()) {
            model = DeviceMapper.cursorToModel(cursor);
        }

        return model;
    }

    public GeneralModel getModelByType(int type) {
        SQLiteDatabase database = baseHelper.getReadableDatabase();
        String where = DataBaseHelper.DEVICE_TYPE + " = ?";
        String[] whereArgs = new String[]{String.valueOf(type)};
        Cursor cursor = database.query(DataBaseHelper.TABLE_GENERAL_DEVICES, null, where, whereArgs, null, null, null);

        GeneralModel model = null;
        if (cursor.moveToFirst()) {
            model = DeviceMapper.cursorToModel(cursor);
        }

        return model;
    }
}
