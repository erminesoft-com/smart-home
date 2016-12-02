package com.kozhurov.model;

import java.io.Serializable;

public final class GeneralTransport implements Serializable {

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
}
