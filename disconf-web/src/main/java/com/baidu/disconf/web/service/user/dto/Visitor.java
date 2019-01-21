package com.baidu.disconf.web.service.user.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.baidu.disconf.web.service.user.constant.UserConstant;
import com.github.knightliao.apollo.db.bo.BaseObject;
import com.github.knightliao.apollo.utils.common.StringUtil;


public class Visitor extends BaseObject<Long> implements Serializable {

    protected static final Logger LOG = LoggerFactory.getLogger(Visitor.class);

    private static final long serialVersionUID = 5621993194031128338L;

    private String loginUserName;

    private int roleId;

    // app list
    private Set<Long> appIds;


    public Long getLoginUserId() {
        return getId();
    }
    public void setLoginUserId(Long loginUserId) {
        setId(loginUserId);
    }

    public String getLoginUserName() {
        return loginUserName;
    }
    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Set<Long> getAppIds() {
        return appIds;
    }
    public void setAppIds(Set<Long> appIds) {
        this.appIds = appIds;
    }


    @Override
    public String toString() {
        return "Visitor [loginUserName=" + loginUserName + ", roleId=" + roleId + ", appIds=" + appIds + "]";
    }

    public void setAppIds(String appIds) {

        if (!StringUtils.isEmpty(appIds)) {
            try {
                List<Long> ids = StringUtil.parseStringToLongList(appIds, UserConstant.USER_APP_SEP);
                setAppIds(new HashSet<Long>(ids));
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
    }
}
