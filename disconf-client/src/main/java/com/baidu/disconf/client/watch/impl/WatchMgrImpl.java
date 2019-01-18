package com.baidu.disconf.client.watch.impl;

import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.model.DisConfCommonModel;
import com.baidu.disconf.client.config.inner.DisClientComConfig;
import com.baidu.disconf.client.core.processor.DisconfCoreProcessor;
import com.baidu.disconf.client.watch.WatchMgr;
import com.baidu.disconf.client.watch.inner.DisconfSysUpdateCallback;
import com.baidu.disconf.client.watch.inner.NodeWatcher;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.path.ZooPathMgr;
import com.baidu.disconf.core.common.utils.ZooUtils;
import com.baidu.disconf.core.common.zookeeper.ZookeeperMgr;

public class WatchMgrImpl implements WatchMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WatchMgrImpl.class);

    private String zooUrlPrefix;
    private boolean debug;


    public void init(String hosts, String zooUrlPrefix, boolean debug) throws Exception {
        this.zooUrlPrefix = zooUrlPrefix;
        this.debug = debug;
        // init zookeeper
        ZookeeperMgr.getInstance().init(hosts, zooUrlPrefix, debug);
    }


    /**
     * 监控路径,监控前会事先创建路径,并且会新建一个自己的Temp子结点
     */
    public void watchPath(DisconfCoreProcessor disconfCoreMgr, DisConfCommonModel disConfCommonModel, String keyName,
                          DisConfigTypeEnum disConfigTypeEnum, String value) throws Exception {

        // monitorPath 为 urlPrefix/app_version_env/item 或者 urlPrefix/app_version_env/file
        // 并在 monitorPath 下创建临时节点， monitorPath/fingerPrint，临时节点数据为当前配置项的值或配置文件的内容
        String monitorPath = makeMonitorPath(disConfigTypeEnum, disConfCommonModel, keyName, value);

        // 进行监控
        NodeWatcher nodeWatcher = new NodeWatcher(disconfCoreMgr, monitorPath,
                keyName, disConfigTypeEnum, new DisconfSysUpdateCallback(), debug);
        nodeWatcher.monitorMaster();
    }


    /**
     * 新建监听的 zookeeper 的路径
     */
    private String makeMonitorPath(DisConfigTypeEnum disConfigTypeEnum,
                                   DisConfCommonModel disConfCommonModel,
                                   String key, String value) throws Exception {

        // urlPrefix/app_version_env
        String clientRootZooPath = ZooPathMgr.getZooBaseUrl(zooUrlPrefix, disConfCommonModel.getApp(),
                disConfCommonModel.getEnv(), disConfCommonModel.getVersion());
        ZookeeperMgr.getInstance().makeDir(clientRootZooPath, ZooUtils.getIp());

        // 监控路径
        String monitorPath;
        if (disConfigTypeEnum.equals(DisConfigTypeEnum.FILE)) {
            // 新建Zoo Store目录  urlPrefix/app_version_env/file
            String clientDisconfFileZooPath = ZooPathMgr.getFileZooPath(clientRootZooPath);
            makePath(clientDisconfFileZooPath, ZooUtils.getIp());
            // urlPrefix/app_version_env/file/key
            monitorPath = ZooPathMgr.joinPath(clientDisconfFileZooPath, key);
        } else {
            // 新建Zoo Store目录  urlPrefix/app_version_env/item
            String clientDisconfItemZooPath = ZooPathMgr.getItemZooPath(clientRootZooPath);
            makePath(clientDisconfItemZooPath, ZooUtils.getIp());
            // urlPrefix/app_version_env/item/key
            monitorPath = ZooPathMgr.joinPath(clientDisconfItemZooPath, key);
        }

        // urlPrefix/app_version_env/item 或者 urlPrefix/app_version_env/file
        makePath(monitorPath, "");
        // 新建一个代表自己的临时结点  urlPrefix/app_version_env/item/fingerPrint
        makeTempChildPath(monitorPath, value);

        return monitorPath;
    }

    /**
     * 创建路径：持久节点
     */
    private void makePath(String path, String value) {
        ZookeeperMgr.getInstance().makeDir(path, value);
    }

    /**
     * 在指定路径下创建一个临时结点
     */
    private void makeTempChildPath(String path, String value) {
        String fingerPrint = DisClientComConfig.getInstance().getInstanceFingerprint();
        String mainTypeFullStr = path + "/" + fingerPrint;
        try {
            ZookeeperMgr.getInstance().createEphemeralNode(mainTypeFullStr, value, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            LOGGER.error("cannot create: " + mainTypeFullStr + "\t" + e.toString());
        }
    }

    /**
     * 关闭 zookeeper 连接
     */
    @Override
    public void release() {
        try {
            ZookeeperMgr.getInstance().release();
        } catch (InterruptedException e) {
            LOGGER.error(e.toString());
        }
    }

}
