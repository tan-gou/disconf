package com.baidu.disconf.client.common.update;

/**
 * 通用型的配置更新接口。当配置更新 时，用户可以实现此接口，用以来实现回调函数.
 */
public interface IDisconfUpdatePipeline {

    /**
     * 配置文件
     */
    void reloadDisconfFile(String key, String filePath) throws Exception;

    /**
     * 配置项
     */
    void reloadDisconfItem(String key, Object content) throws Exception;
}
