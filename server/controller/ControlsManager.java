package com.kozhurov.controller;

import com.google.gson.Gson;
import com.kozhurov.gate.MessageListener;
import com.kozhurov.model.GeneralModel;
import com.kozhurov.model.GeneralTransport;
import com.kozhurov.gate.GateManager;

import java.util.concurrent.Executor;

public class ControlsManager {

    private final Executor executor;
    private final GateManager gateManager;

    private final ConditionController conditionController;
    private final ElementaryFanController elementaryFanController;

    public ControlsManager(Executor executor) {
        System.out.println("Control start");

        this.executor = executor;
        gateManager = new GateManager(executor, new MessagesListener());

        elementaryFanController = new ElementaryFanController();
        conditionController = new ConditionController();
   
        System.out.println("Control started");
    }

    public void setNewConditionState(GeneralModel conditionModel) {
        conditionController.setNewConditionState(gateManager, conditionModel);
    }

    public void setNewEFanState(GeneralModel newEFanState) {
        elementaryFanController.setNewFanState(gateManager, newEFanState);
    }

    private void processTransport(GeneralTransport generalTransport) {

        if (generalTransport.getConditionModel() != null) {
            setNewConditionState(generalTransport.getConditionModel());
            return;
        }

        if (generalTransport.getElementaryFanModel() != null) {
            setNewEFanState(generalTransport.getElementaryFanModel());
            return;

        }
    }

    private final class MessagesListener implements MessageListener {

        @Override
        public void onMessageReceive(String serializedData) {
            try {
                GeneralTransport transport = new Gson().fromJson(serializedData, GeneralTransport.class);
                processTransport(transport);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
