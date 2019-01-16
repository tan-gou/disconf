package com.baidu.disconf.core.common.restful;

import com.baidu.disconf.core.common.restful.impl.RestfulMgrImpl;
import com.baidu.disconf.core.common.restful.retry.impl.RetryStrategyRoundBin;


public class RestfulFactory {

    /**
     * 获取一个默认的抓取器
     */
    public static RestfulMgr getRestfulMgrNomal() {
        RestfulMgr restfulMgr = new RestfulMgrImpl(new RetryStrategyRoundBin());
        return restfulMgr;
    }
}
