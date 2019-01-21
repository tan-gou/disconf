package com.baidu.disconf.web.service.config.service;


public interface ConfigHistoryMgr {

    void createOne(Long configId, String oldValue, String newValue);
}
