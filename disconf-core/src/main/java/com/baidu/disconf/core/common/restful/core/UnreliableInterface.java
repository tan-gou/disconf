package com.baidu.disconf.core.common.restful.core;

/**
 * 一个可重试可执行方法
 */
public interface UnreliableInterface {

    <T> T call() throws Exception;
}
