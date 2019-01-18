package com.baidu.disconf.client.scan.inner.statically;

import java.util.Set;

import com.baidu.disconf.client.scan.inner.statically.model.ScanStaticModel;


public interface StaticScannerMgr {

    void scanData2Store(ScanStaticModel scanModel);

    void exclude(Set<String> keySet);
}
