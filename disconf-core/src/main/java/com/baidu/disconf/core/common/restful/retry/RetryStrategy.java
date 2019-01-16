package com.baidu.disconf.core.common.restful.retry;

import com.baidu.disconf.core.common.restful.core.UnreliableInterface;

/**
 * 重试的策略
 */
public interface RetryStrategy {

    <T> T retry(UnreliableInterface unreliableImpl, int retryTimes, int sleepSeconds) throws Exception;
}
