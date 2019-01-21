package com.baidu.unbiz.common.genericdao.operator;

/**
 * 排序
 */
public class Order implements Pair {

    private String column;
    private boolean asc;

    public Order(String column, boolean asc) {
        this.column = column;
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return asc;
    }

}
