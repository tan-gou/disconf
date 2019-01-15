package com.baidu.disconf.client.store.processor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.model.DisConfCommonModel;
import com.baidu.disconf.client.common.model.DisconfCenterBaseModel;
import com.baidu.disconf.client.common.model.DisconfCenterItem;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.baidu.disconf.client.store.DisconfStoreProcessor;
import com.baidu.disconf.client.store.inner.DisconfCenterStore;
import com.baidu.disconf.client.store.processor.model.DisconfValue;

/**
 * 配置项仓库算子实现器
 */
public class DisconfStoreItemProcessorImpl implements DisconfStoreProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfStoreItemProcessorImpl.class);

    /**
     * 增加 配置更新时 的回调函数
     */
    @Override
    public void addUpdateCallbackList(String keyName, List<IDisconfUpdate> iDisconfUpdateList) {

        if (DisconfCenterStore.getInstance().getConfItemMap().containsKey(keyName)) {
            DisconfCenterStore.getInstance().getConfItemMap().get(keyName).getDisconfCommonCallbackModel()
                    .getDisconfConfUpdates().addAll(iDisconfUpdateList);
        }
    }

    /**
     * 获取 配置更新时 的回调函数
     */
    @Override
    public List<IDisconfUpdate> getUpdateCallbackList(String keyName) {

        if (DisconfCenterStore.getInstance().getConfItemMap().containsKey(keyName)) {
            return DisconfCenterStore.getInstance().getConfItemMap().get(keyName).getDisconfCommonCallbackModel()
                    .getDisconfConfUpdates();
        }
        return new ArrayList<IDisconfUpdate>();
    }


    @Override
    public DisConfCommonModel getCommonModel(String keyName) {

        DisconfCenterItem disconfCenterItem = DisconfCenterStore.getInstance().getConfItemMap().get(keyName);
        if (disconfCenterItem == null) {
            LOGGER.error("cannot find " + keyName + " in store....");
            return null;
        }

        return disconfCenterItem.getDisConfCommonModel();
    }

    /**
     * 是否有这个配置
     */
    @Override
    public boolean hasThisConf(String keyName) {
        return DisconfCenterStore.getInstance().getConfItemMap().containsKey(keyName);
    }

    /**
     * 为 类的属性 注入值，
     */
    @Override
    public void inject2Instance(Object object, String key) {

        DisconfCenterItem disconfCenterItem = DisconfCenterStore.getInstance().getConfItemMap().get(key);
        if (disconfCenterItem == null) {
            LOGGER.error("cannot find " + key + " in store....");
            return;
        }

        // 非静态类
        if (object != null) {
            disconfCenterItem.setObject(object);
        }

        // 根据类型设置值，注入实体
        try {
            if (object != null) {
                LOGGER.debug(disconfCenterItem.getKey() + " is a non-static field. ");
                if (disconfCenterItem.getValue() == null) {
                    // 如果仓库值为空，则实例 直接使用默认值
                    Object defaultValue = disconfCenterItem.getFieldDefaultValue(object);
                    disconfCenterItem.setValue(defaultValue);
                } else {
                    // 如果仓库里的值为非空，则实例使用仓库里的值
                    disconfCenterItem.setValue4FileItem(object, disconfCenterItem.getValue());
                }
            } else {
                // 静态类
                if (disconfCenterItem.isStatic()) {
                    LOGGER.debug(disconfCenterItem.getKey() + " is a static field. ");
                    disconfCenterItem.setValue4StaticFileItem(disconfCenterItem.getValue());
                }
            }
        } catch (Exception e) {
            LOGGER.error("inject2Instance key: " + key + " " + e.toString(), e);
        }
    }


    /**
     * 更改配置项数据
     */
    @Override
    public void inject2Store(String key, DisconfValue disconfValue) {

        DisconfCenterItem disconfCenterItem = DisconfCenterStore.getInstance().getConfItemMap().get(key);
        if (disconfCenterItem == null) {
            LOGGER.error("cannot find " + key + " in store....");
            return;
        }

        if (disconfValue == null || disconfValue.getValue() == null) {
            return;
        }

        // 根据类型设置值；注入仓库
        try {
            Object newValue = disconfCenterItem.getFieldValueByType(disconfValue.getValue());
            disconfCenterItem.setValue(newValue);
        } catch (Exception e) {
            LOGGER.error("key: " + key + " " + e.toString(), e);
        }
    }

    /** 批量添加配置 */
    @Override
    public void transformScanData(List<DisconfCenterBaseModel> disconfCenterBaseModelList) {
        for (DisconfCenterBaseModel disconfCenterItem : disconfCenterBaseModelList) {
            transformScanData(disconfCenterItem);
        }
    }

    /** 添加单个配置 */
    @Override
    public void transformScanData(DisconfCenterBaseModel disconfCenterBaseModel) {
        DisconfCenterStore.getInstance().storeOneItem(disconfCenterBaseModel);
    }

    /** 删除配置项 */
    @Override
    public void exclude(Set<String> keySet) {
        for (String key : keySet) {
            DisconfCenterStore.getInstance().excludeOneItem(key);
        }
    }

    /** 获取配置数据 */
    @Override
    public DisconfCenterBaseModel getConfData(String key) {
        if (DisconfCenterStore.getInstance().getConfItemMap().containsKey(key)) {
            return DisconfCenterStore.getInstance().getConfItemMap().get(key);
        } else {
            return null;
        }
    }

    /** 获取配置项的值 */
    @Override
    public Object getConfig(String fileName, String keyName) {

        DisconfCenterItem disconfCenterItem = DisconfCenterStore.getInstance().getConfItemMap().get(keyName);
        if (disconfCenterItem == null) {
            LOGGER.debug("cannot find " + keyName + " in store....");
            return null;
        }
        return disconfCenterItem.getValue();
    }


    @Override
    public Set<String> getConfKeySet() {
        return DisconfCenterStore.getInstance().getConfItemMap().keySet();
    }


    @Override
    public String confToString() {

        StringBuilder sBuffer = new StringBuilder();
        sBuffer.append("\n");
        Map<String, DisconfCenterItem> disMap = DisconfCenterStore.getInstance().getConfItemMap();
        for (String key : disMap.keySet()) {
            sBuffer.append("disItem:\t" + key + "\t");
            if (LOGGER.isDebugEnabled()) {
                sBuffer.append(disMap.get(key).toString());
            } else {
                sBuffer.append(disMap.get(key).infoString());
            }
            sBuffer.append("\n");
        }

        return sBuffer.toString();
    }
}
