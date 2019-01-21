package com.baidu.disconf.web.service.config.service;

import java.io.File;
import java.util.List;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.web.service.config.bo.Config;
import com.baidu.disconf.web.service.config.form.ConfListForm;
import com.baidu.disconf.web.service.config.form.ConfNewItemForm;
import com.baidu.disconf.web.service.config.vo.ConfListVo;
import com.baidu.disconf.web.service.config.vo.MachineListVo;
import com.baidu.ub.common.db.DaoPageResult;


public interface ConfigMgr {

    List<String> getVersionListByAppEnv(Long appId, Long envId);

    DaoPageResult<ConfListVo> getConfigList(ConfListForm confListForm, boolean fetchZk, final boolean getErrorMessage);


    ConfListVo getConfVo(Long configId);

    MachineListVo getConfVoWithZk(Long configId);


    Config getConfigById(Long configId);

    /**
     * 更新 配置项/配置文件
     */
    String updateItemValue(Long configId, String value);

    /**
     * 获取config value
     */
    String getValue(Long configId);

    /**
     * 通知zk
     */
    void notifyZookeeper(Long configId);

    /**
     * 新建一个config
     */
    void newConfig(ConfNewItemForm confNewForm, DisConfigTypeEnum disConfigTypeEnum);


    void delete(Long configId);

    /**
     * @param confListForm
     */
    List<File> getDisconfFileList(ConfListForm confListForm);

}
