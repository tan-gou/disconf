package com.baidu.disconf.core.common.restful.type;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.core.common.restful.core.UnreliableInterface;
import com.baidu.disconf.core.common.utils.OsUtil;

/**
 * 下载配置文件
 */
public class FetchConfFile implements UnreliableInterface {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FetchConfFile.class);

    // 远程地址
    private URL  remoteUrl;
    // 本地文件
    private File localTmpFile;

    public FetchConfFile(URL remoteUrl, File localTmpFile) {
        this.remoteUrl = remoteUrl;
        this.localTmpFile = localTmpFile;
    }

    /**
     * 进行下载
     */
    @Override
    public <T> T call() throws Exception {

        // 删除临时文件
        if (localTmpFile.exists()) {
            localTmpFile.delete();
        }
        LOGGER.debug("start to download. From: " + remoteUrl + " , TO: " + localTmpFile.getAbsolutePath());

        FileUtils.copyURLToFile(remoteUrl, localTmpFile);

        if (!OsUtil.isFileExist(localTmpFile.getAbsolutePath())) {
            throw new Exception("download is ok, but cannot find downloaded file." + localTmpFile);
        }

        LOGGER.debug("download success!  " + localTmpFile.getAbsolutePath());
        return null;
    }

}
