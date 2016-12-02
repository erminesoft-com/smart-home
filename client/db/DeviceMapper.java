package com.kozhurov.project294.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.kozhurov.project294.model.ConditionModel;
import com.kozhurov.project294.model.FanModel;
import com.kozhurov.project294.model.GeneralModel;

import java.util.ArrayList;
import java.util.List;


final class DeviceMapper {

    static ContentValues modelToCv(GeneralModel generalModel) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.DEVICE_UUID, generalModel.getUuid());
        cv.put(DataBaseHelper.DEVICE_NAME, generalModel.getName());
        cv.put(DataBaseHelper.DEVICE_DESCR, generalModel.getDescription());
        cv.put(DataBaseHelper.DEVICE_STATE, generalModel.getState());
        cv.put(DataBaseHelper.DEVICE_TYPE, generalModel.getType());
        cv.put(DataBaseHelper.DEVICE_PIC_URL, generalModel.getPicUrl());
        return cv;
    }

    static GeneralModel cursorToModel(Cursor cursor) {
        int typeIndex = cursor.getColumnIndex(DataBaseHelper.DEVICE_TYPE);
        int type = cursor.getInt(typeIndex);

        GeneralModel model = null;
        switch (type) {
            case GeneralModel.TYPE_ELEMENTARY_FAN:
                model = unpackFanModel(cursor, type);
                break;

            case GeneralModel.TYPE_CONDITIONER:
                model = unpackFanModel(cursor, type);
                break;
        }

        return model;
    }

    static List<GeneralModel> cursorToModelList(Cursor cursor) {
        List<GeneralModel> models = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            models.add(cursorToModel(cursor));
        }

        return models;
    }

    private static GeneralModel unpackFanModel(Cursor cursor, int type) {
        FanModel fanModel = new FanModel();

        int uuidIndex = cursor.getColumnIndex(DataBaseHelper.DEVICE_UUID);
        int nameIndex = cursor.getColumnIndex(DataBaseHelper.DEVICE_NAME);
        int descrIndex = cursor.getColumnIndex(DataBaseHelper.DEVICE_DESCR);
        int stateIndex = cursor.getColumnIndex(DataBaseHelper.DEVICE_STATE);
        int picUrlIndex = cursor.getColumnIndex(DataBaseHelper.DEVICE_PIC_URL);

        fanModel.setUuid(cursor.getString(uuidIndex));
        fanModel.setName(cursor.getString(nameIndex));
        fanModel.setDescription(cursor.getString(descrIndex));
        fanModel.setState(cursor.getInt(stateIndex));
        fanModel.setType(type);
        fanModel.setPicUrl(cursor.getString(picUrlIndex));

        return fanModel;
    }

    private static GeneralModel unpackConditionModel(Cursor cursor, int type) {
        return new ConditionModel();

    }
}

