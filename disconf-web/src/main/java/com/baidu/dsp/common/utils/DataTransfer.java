package com.baidu.dsp.common.utils;

/**
 * 数据转接接口
 */
public interface DataTransfer<ENTITYFROM, ENTITYTO> {

    /**
     * 转换规则定义
     */
    ENTITYTO transfer(ENTITYFROM input);
}
