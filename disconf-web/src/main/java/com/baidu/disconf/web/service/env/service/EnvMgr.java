package com.baidu.disconf.web.service.env.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.disconf.web.service.env.vo.EnvListVo;


public interface EnvMgr {

    Env getByName(String name);

    List<Env> getList();

    List<EnvListVo> getVoList();

    Map<Long, Env> getByIds(Set<Long> ids);

    Env getById(Long id);
}
