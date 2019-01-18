package com.baidu.disconf.client.scan;

import java.util.List;


public interface ScanMgr {

    void firstScan(List<String> packageNameLit) throws Exception;

    void secondScan() throws Exception;

    /**
     * reloadable for specify files
     */
    void reloadableScan(String fileName) throws Exception;
}
