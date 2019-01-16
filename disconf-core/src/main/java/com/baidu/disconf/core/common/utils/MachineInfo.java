package com.baidu.disconf.core.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class MachineInfo {

    private MachineInfo() {

    }

    public static String getHostName() throws Exception {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new Exception(e);
        }
    }

    public static String getHostIp() throws Exception {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            throw new Exception(e);
        }
    }

}
