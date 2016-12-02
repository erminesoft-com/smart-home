package com.kozhurov.project294.core;

import android.content.Context;

import com.kozhurov.project294.db.DbManager;
import com.kozhurov.project294.model.FanModel;
import com.kozhurov.project294.model.GeneralModel;
import com.kozhurov.project294.model.GeneralTransport;
import com.kozhurov.project294.net.NetManager;

import java.util.concurrent.Executor;

public final class DeviceHelper {

    private DbManager dbManager;
    private NetManager netManager;
    private SharedHelper sharedHelper;
    private MultimediaHelper multimediaHelper;

    public DeviceHelper(Context context, Executor executor) {
        dbManager = new DbManager(context);
        netManager = new NetManager(executor)
    }

    public void changeFanState(FanModel fanModel) {
        dbManager.saveModel(fanModel);
        netManager.sendMessage(fanModel);
    }

    public void turnFan(boolean isEnable) {
        GeneralModel model = dbManager.getModelByType(GeneralModel.TYPE_ELEMENTARY_FAN);
        model.setState(isEnable ? GeneralModel.FAN_ON : GeneralModel.FAN_OFF);
        dbManager.saveModel(model);
        netManager.sendMessage(model);
    }

    public void switchFanState() {
        GeneralModel model = dbManager.getModelByType(GeneralModel.TYPE_ELEMENTARY_FAN);

        boolean oldState = model.getState() == 0;

        model.setState(oldState ? GeneralModel.FAN_OFF : GeneralModel.FAN_ON);
        dbManager.saveModel(model);
        netManager.sendMessage(model);
    }

    public void setWaitTransport(GeneralTransport waitTransport) {
        waitQueue.put(waitTransport.getTimestamp(), waitTransport);
    }

    public void unpackWaitAnswer(GeneralTransport transport) {
        if (transport.getElementaryFanModel() != null) {
            saveModel(transport.getElementaryFanModel());
        }

        if (transport.getConditionModel() != null) {
            saveModel(transport.getConditionModel());
        }

        waitQueue.remove(transport.getTimestamp());
    }
}
