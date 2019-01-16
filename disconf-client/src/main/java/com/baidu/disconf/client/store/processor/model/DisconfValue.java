package com.baidu.disconf.client.store.processor.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 配置的值，配置文件是properties, 配置项是string，这个类是为了做兼容
 */
public class DisconfValue {

    // 配置项使用
    private String value;

    // 配置文件使用
    private Map<String, Object> fileData = Maps.newHashMap();

    public DisconfValue(String value, Map<String, Object> fileData) {
        super();
        this.value = value;
        this.fileData = fileData;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Map<String, Object> getFileData() {
        return fileData;
    }

    public void setFileData(Map<String, Object> fileData) {
        this.fileData = fileData;
    }
}
