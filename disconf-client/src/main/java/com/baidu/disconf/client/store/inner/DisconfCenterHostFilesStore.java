package com.baidu.disconf.client.store.inner;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 只是进行托管的配置文件（不会注入，只负责下载和动态推送）
 */
public class DisconfCenterHostFilesStore {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfCenterHostFilesStore.class);

    private DisconfCenterHostFilesStore() {
    }

    private static class SingletonHolder {
        private static DisconfCenterHostFilesStore instance = new DisconfCenterHostFilesStore();
    }

    public static DisconfCenterHostFilesStore getInstance() {
        return SingletonHolder.instance;
    }

    //
    // 只是进行托管的配置文件（不会注入，只负责动态推送）
    //
    private Set<String> justHostFiles = new HashSet<String>();

    public Set<String> getJustHostFiles() {
        return justHostFiles;
    }

    public void addJustHostFileSet(Set<String> justHostFiles) {
        this.justHostFiles.addAll(justHostFiles);
    }

    public void addJustHostFile(String justHostFile) {
        this.justHostFiles.add(justHostFile);
    }
}
