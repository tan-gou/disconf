package com.baidu.disconf.core.common.path;

import com.baidu.disconf.core.common.constants.Constants;

/**
 * Zookeeper path 管理
 */
public class ZooPathMgr {

    /**
     * 获取 Zookeeper 的应用基础路径
     */
    public static String getZooBaseUrl(String urlPrefix, String app, String env, String version) {

        StringBuffer sb = new StringBuffer();
        sb.append(urlPrefix);
        sb.append(Constants.SEP_STRING);

        sb.append(app);
        sb.append("_");
        sb.append(version);
        sb.append("_");
        sb.append(env);

        return sb.toString();
    }


    public static String joinPath(String path1, String path2) {
        return path1 + Constants.SEP_STRING + path2;
    }

    /**
     * 获取 Disconf ITEM ZOO Path
     */
    public static String getItemZooPath(String baseUrl) {
        return baseUrl + Constants.SEP_STRING + Constants.STORE_ITEM_URL_KEY;
    }

    /**
     * 获取 Disconf FILE ZOO Path
     */
    public static String getFileZooPath(String baseUrl) {
        return baseUrl + Constants.SEP_STRING + Constants.STORE_FILE_URL_KEY;
    }
}
