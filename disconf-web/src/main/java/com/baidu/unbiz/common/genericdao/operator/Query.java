package com.baidu.unbiz.common.genericdao.operator;

import lombok.Getter;

import java.util.List;

/**
 * 封装一个sql操作
 */
public class Query {

    @Getter
    private String sql;

    @Getter
    private List<Object> params;

    public Query(String sql, List<Object> params) {
        super();
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
