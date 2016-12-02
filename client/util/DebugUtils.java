package com.kozhurov.project294.util;

import com.kozhurov.project294.model.ConditionModel;
import com.kozhurov.project294.model.FanModel;
import com.kozhurov.project294.model.GeneralModel;

import java.util.UUID;

public final class DebugUtils {

    public static GeneralModel generateFanModel(){
        GeneralModel model = new FanModel();
        model.setUuid(UUID.randomUUID().toString());
        model.setName("My fan");
        model.setDescription("Test descr");
        model.setState(GeneralModel.FAN_OFF);
        model.setType(GeneralModel.TYPE_ELEMENTARY_FAN);
        return model;
    }

    public static GeneralModel generateConditionModel(){
        GeneralModel model = new ConditionModel();
        model.setUuid(UUID.randomUUID().toString());
        model.setName("My condition");
        model.setDescription("ConditionModel descr");
        model.setType(GeneralModel.TYPE_CONDITIONER);
        return model;
    }
}
