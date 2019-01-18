package com.baidu.disconf.client.watch.inner;

import com.baidu.disconf.client.common.update.IDisconfSysUpdate;
import com.baidu.disconf.client.core.processor.DisconfCoreProcessor;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;

/**
 * 当配置更新时，系统会自动 调用此回调函数
 */
public class DisconfSysUpdateCallback implements IDisconfSysUpdate {

    @Override
    public void reload(DisconfCoreProcessor disconfCoreProcessor, DisConfigTypeEnum disConfigTypeEnum,
                       String keyName) throws Exception {

        // 更新配置数据仓库 && 调用用户的回调函数列表
        disconfCoreProcessor.updateOneConfAndCallback(keyName);
    }
}
