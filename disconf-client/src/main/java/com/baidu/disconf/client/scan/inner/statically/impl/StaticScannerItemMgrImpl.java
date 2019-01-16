package com.baidu.disconf.client.scan.inner.statically.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.annotations.DisconfItem;
import com.baidu.disconf.client.common.model.DisConfCommonModel;
import com.baidu.disconf.client.common.model.DisconfCenterBaseModel;
import com.baidu.disconf.client.common.model.DisconfCenterItem;
import com.baidu.disconf.client.config.DisClientSysConfig;
import com.baidu.disconf.client.scan.inner.statically.StaticScannerMgr;
import com.baidu.disconf.client.scan.inner.statically.model.ScanStaticModel;
import com.baidu.disconf.client.store.DisconfStoreProcessorFactory;
import com.baidu.disconf.client.support.utils.MethodUtils;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.path.DisconfWebPathMgr;

/**
 * 配置项的静态扫描 
 */
public class StaticScannerItemMgrImpl extends StaticScannerMgrImplBase implements StaticScannerMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StaticScannerItemMgrImpl.class);

    @Override
    public void scanData2Store(ScanStaticModel scanModel) {
        // 转换配置项
        List<DisconfCenterBaseModel> disconfCenterItems = getDisconfItemList(scanModel);
        DisconfStoreProcessorFactory.getDisconfStoreItemProcessor().transformScanData(disconfCenterItems);

    }

    @Override
    public void exclude(Set<String> keySet) {
        DisconfStoreProcessorFactory.getDisconfStoreItemProcessor().exclude(keySet);
    }


    /**
     * 转换配置项
     */
    private static List<DisconfCenterBaseModel> getDisconfItemList(ScanStaticModel scanModel) {

        List<DisconfCenterBaseModel> disconfCenterItemList = new ArrayList<DisconfCenterBaseModel>();

        Set<Method> methods = scanModel.getDisconfItemMethodSet();
        for (Method method : methods) {
            DisconfCenterItem disconfCenterItem = transformScanItem(method);
            if (disconfCenterItem != null) {
                disconfCenterItemList.add(disconfCenterItem);
            }
        }

        return disconfCenterItemList;
    }

    /**
     * 转换配置项
     */
    private static DisconfCenterItem transformScanItem(Method method) {

        DisconfCenterItem disconfCenterItem = new DisconfCenterItem();

        Class<?> cls = method.getDeclaringClass();
        Field[] expectedFields = cls.getDeclaredFields();

        /** 获取对应的 属性 */
        Field field = MethodUtils.getFieldFromMethod(method, expectedFields, DisConfigTypeEnum.ITEM);
        if (field == null) {
            return null;
        }

        DisconfItem disconfItem = method.getAnnotation(DisconfItem.class);
        String key = disconfItem.key().replace(" ", "");

        Method setterMethod = MethodUtils.getSetterMethodFromField(cls, field);
        disconfCenterItem.setSetMethod(setterMethod);

        disconfCenterItem.setField(field);
        disconfCenterItem.setKey(key);

        field.setAccessible(true);
        disconfCenterItem.setObject(null);

        if (Modifier.isStatic(field.getModifiers())) {
            try {
                disconfCenterItem.setValue(field.get(null));
            } catch (Exception e) {
                LOGGER.error(e.toString());
                disconfCenterItem.setValue(null);
            }
        } else {
            disconfCenterItem.setValue(null);
        }

        DisConfCommonModel disConfCommonModel = makeDisConfCommonModel(disconfItem.app(), disconfItem.env(), disconfItem.version());
        disconfCenterItem.setDisConfCommonModel(disConfCommonModel);

        // Disconf-web url
        String url = DisconfWebPathMgr.getRemoteUrlParameter(DisClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
                disConfCommonModel.getApp(), disConfCommonModel.getVersion(), disConfCommonModel.getEnv(),
                key,
                DisConfigTypeEnum.ITEM);
        disconfCenterItem.setRemoteServerUrl(url);

        return disconfCenterItem;
    }
}
