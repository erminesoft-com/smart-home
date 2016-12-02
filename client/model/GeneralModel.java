package com.kozhurov.project294.model;

public abstract class GeneralModel {

    public static final int TYPE_ELEMENTARY_FAN = 1;
    public static final int TYPE_CONDITIONER = 2;

    public static final int FAN_ON = 0;
    public static final int FAN_OFF = 1;

    private String uuid;

    private String name;
    private String picUrl;
    private String description;
    private int state;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
