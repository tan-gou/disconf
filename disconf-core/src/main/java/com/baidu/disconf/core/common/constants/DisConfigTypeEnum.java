package com.baidu.disconf.core.common.constants;


public enum DisConfigTypeEnum {

    FILE(0, "配置文件"),
    ITEM(1, "配置项"),
    ;

    private int type = 0;
    private String modelName = null;

    DisConfigTypeEnum(int type, String modelName) {
        this.type = type;
        this.modelName = modelName;
    }

    public static DisConfigTypeEnum getByType(int type) {
        for (DisConfigTypeEnum disConfigTypeEnum : DisConfigTypeEnum.values()) {
            if (type == disConfigTypeEnum.getType()) {
                return disConfigTypeEnum;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
