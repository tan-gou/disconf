package com.baidu.disconf.client.core.processor;

/**
 * 处理算子
 */
public interface DisconfCoreProcessor {

    /**
     * 处理所有配置
     */
    void processAllItems();

    /**
     * 处理one配置 （下载远程配置文件，将配置存储当配置仓库map中，设置watch）
     */
    void processOneItem(String key);

    /**
     * 更新指定的配置并进行回调
     */
    void updateOneConfAndCallback(String key) throws Exception;

    /**
     * 将配置注入类实体中
     */
    void inject2Conf();
}
