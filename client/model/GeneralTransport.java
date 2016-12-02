package com.kozhurov.project294.model;

import com.google.gson.Gson;

import java.io.Serializable;

public final class GeneralTransport extends ServiceTransport implements Serializable {

    private GeneralModel conditionModel;
    private GeneralModel elementaryFanModel;

    public GeneralModel getConditionModel() {
        return conditionModel;
    }

    public void setConditionModel(GeneralModel conditionModel) {
        this.conditionModel = conditionModel;
    }

    public GeneralModel getElementaryFanModel() {
        return elementaryFanModel;
    }

    public void setElementaryFanModel(GeneralModel elementaryFanModel) {
        this.elementaryFanModel = elementaryFanModel;
    }

    public String convertSelfToJson(){
        return new Gson().toJson(this);
    }

}
