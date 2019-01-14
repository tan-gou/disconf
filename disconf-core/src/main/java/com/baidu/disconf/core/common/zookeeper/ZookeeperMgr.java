package com.baidu.disconf.core.common.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.core.common.utils.ZooUtils;
import com.baidu.disconf.core.common.zookeeper.inner.ResilientActiveKeyValueStore;

/**
 * Zookeeper 统一管理器
 */
public class ZookeeperMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperMgr.class);

    private ResilientActiveKeyValueStore store;

    private String curHost = "";
    private String curDefaultPrefixString = "";

    // 构造函数 私有
    private ZookeeperMgr() {
    }

    /**
     * 静态初始化器，由JVM来保证线程安全，实现了延迟加载。
     */
    private static class SingletonHolder {
        private static ZookeeperMgr instance = new ZookeeperMgr();
    }

    public static ZookeeperMgr getInstance() {
        return SingletonHolder.instance;
    }

    public void init(String host, String defaultPrefixString, boolean debug) throws Exception {
        try {
            initInternal(host, defaultPrefixString, debug);
            LOGGER.debug("ZookeeperMgr init.");
        } catch (Exception e) {
            throw new Exception("zookeeper init failed. ", e);
        }
    }


    public void reconnect() {
        store.reconnect();
    }


    private void initInternal(String hosts, String defaultPrefixString, boolean debug)
            throws IOException, InterruptedException {

        curHost = hosts;
        curDefaultPrefixString = defaultPrefixString;

        store = new ResilientActiveKeyValueStore(debug);
        store.connect(hosts);

        LOGGER.info("zoo prefix: " + defaultPrefixString);

        // 新建父目录
        makeDir(defaultPrefixString, ZooUtils.getIp());
    }

    /**
     * Zoo的新建目录
     */
    public void makeDir(String dir, String data) {
        try {
            boolean deafult_path_exist = store.exists(dir);
            if (!deafult_path_exist) {
                LOGGER.info("create: " + dir);
                this.writePersistentUrl(dir, data);
            }
        } catch (KeeperException e) {
            LOGGER.error("cannot create path: " + dir, e);
        } catch (Exception e) {
            LOGGER.error("cannot create path: " + dir, e);
        }
    }

    /**
     * 应用程序必须调用它来释放zookeeper资源
     */
    public void release() throws InterruptedException {
        store.close();
    }

    /**
     * 获取子节点 列表
     */
    public List<String> getRootChildren() {
        return store.getRootChildren();
    }

    public void writePersistentUrl(String url, String value) throws Exception {
        store.write(url, value);
    }

    /**
     * 读结点数据
     */
    public String readUrl(String url, Watcher watcher) throws Exception {
        return store.read(url, watcher, null);
    }

    public boolean exists(String path) throws Exception {
        return store.exists(path);
    }

    /**
     * 带状态信息的读取数据
     */
    public String read(String path, Watcher watcher, Stat stat) throws InterruptedException, KeeperException {
        return store.read(path, watcher, stat);
    }

    /**
     * 创建临时节点
     */
    public String createEphemeralNode(String path, String value, CreateMode createMode) throws Exception {
        return store.createEphemeralNode(path, value, createMode);
    }

    /**
     * 删除结点
     */
    public void deleteNode(String path) {
        store.deleteNode(path);
    }

    public ZooKeeper getZk() {
        return store.getZk();
    }
}
