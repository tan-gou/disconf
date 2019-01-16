package com.baidu.disconf.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZooUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ZooUtils.class);

    private ZooUtils() {
    }

    public static String getIp() {
        try {
            return MachineInfo.getHostIp();
        } catch (Exception e) {
            LOGGER.error("cannot get host info", e);
            return "";
        }
    }

}
