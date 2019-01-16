package com.baidu.disconf.client.scan;

import com.baidu.disconf.client.scan.impl.ScanMgrImpl;
import com.baidu.disconf.client.support.registry.Registry;

/**
 * 扫描器工厂
 */
public class ScanFactory {

    public static ScanMgr getScanMgr(Registry registry) {
        ScanMgr scanMgr = new ScanMgrImpl(registry);
        return scanMgr;
    }
}
