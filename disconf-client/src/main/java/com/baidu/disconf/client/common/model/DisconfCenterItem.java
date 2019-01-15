package com.baidu.disconf.client.common.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.baidu.disconf.client.support.utils.ClassUtils;

/**
 * 配置项表示
 */
public class DisconfCenterItem extends DisconfCenterBaseModel {

    // 文件项的KEY
    private String key;
    private Object value;

    // Field
    private Field field;
    private Method setMethod;


    /**
     * 是否是静态域
     */
    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    public Class<?> getDeclareClass() {
        return field.getDeclaringClass();
    }

    /** fieldValue 转成对应的类型 */
    public Object getFieldValueByType(Object fieldValue) throws Exception {
        return ClassUtils.getValeByType(field.getType(), fieldValue);
    }

    public Object getFieldDefaultValue(Object object) throws Exception {
        return field.get(object);
    }

    /**
     * 给静态属性设置 value
     */
    public Object setValue4StaticFileItem(Object value) throws Exception {

        if (setMethod != null) {
            setMethod.invoke(null, value);
        } else {
            field.set(null, value);
        }

        return value;
    }
    /**
     * 给普通属性设置 value
     */
    public Object setValue4FileItem(Object object, Object value) throws Exception {

        if (setMethod != null) {
            setMethod.invoke(object, value);
        } else {
            field.set(object, value);
        }

        return value;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    @Override
    public String toString() {
        return "\n\tDisconfCenterItem [\n\tkey=" + key + "\n\tvalue=" + value + "\n\tfield=" + field +
                super.toString() + "]";
    }

    @Override
    public String infoString() {
        return "\n\tDisconfCenterItem [\n\tvalue=" + value + "\n\tfield=" + field + super.infoString() + "]";
    }
}
