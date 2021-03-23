package com.springcloud.springcloudshardingjdbcnew.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 反射工具类
 *
 * @author litianyi
 * <p>
 * 2016年2月8日
 */
public class BeanTools {

    private static Map<String, BeanCopier> beanMap = Maps.newConcurrentMap();

    /**
     * 复制指定的属性集合
     *
     * @return true 复制成功
     */
    public static <T> boolean copySomeProperties(T from, T to, String[] properties) {
        try {
            for (String property : properties) {
                Object value = PropertyUtils.getProperty(from, property);
                PropertyUtils.setProperty(to, property, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制对象 将tempObject不为空字段 复制到 bean 中
     *
     * @param bean
     * @param tempObject
     * @return
     */
    public static <T> T copyNonNullProperty(T bean, Object tempObject) {

        Field[] fields = null;
        try {
            fields = tempObject.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (!"serialVersionUID".equals(fields[i].getName()) && fields[i].getModifiers() == Modifier.PRIVATE) {
                    Object value = PropertyUtils.getProperty(tempObject, fields[i].getName());
                    if (value != null) {
                        PropertyUtils.setProperty(bean, fields[i].getName(), value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bean;
        }

        return bean;
    }

    /**
     * 获取一个对象的非空属性
     *
     * @param obj
     * @param excludeProperty
     * @return
     * @author BennyTian
     * @date 2015年5月26日 上午11:41:07
     */
    public static Map<String, Object> getNonNullProperty(Object obj, String... excludeProperty) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if (obj == null) {
            return properties;
        }
        Set<String> excludeProperties = new HashSet<String>();
        if (excludeProperty != null && excludeProperty.length > 0) {
            for (String p : excludeProperty) {
                excludeProperties.add(p);
            }
        }
        Field[] fields = null;
        try {
            fields = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (!excludeProperties.contains(fields[i].getName()) && !"serialVersionUID".equals(fields[i].getName()) && fields[i].getModifiers() == Modifier.PRIVATE) {
                    Object value = PropertyUtils.getProperty(obj, fields[i].getName());
                    if (value != null) {
                        properties.put(fields[i].getName(), value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 将实体类Bean 转换成 Json 可使用的 Vo
     *
     * @param bean
     * @param voClass
     * @return
     */
    public static <K> K convertBeanToVo(Object bean, Class<K> voClass) {

        K vo = null;
        try {
            vo = voClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String key = bean.getClass().getName() + ":" + voClass.getName();
        if (!beanMap.containsKey(key)) {
            BeanCopier copier = BeanCopier.create(bean.getClass(), voClass, false);
            beanMap.put(key, copier);
        }
        BeanCopier beanCopier = beanMap.get(key);
        beanCopier.copy(bean, vo, null);

        return vo;
    }

    /**
     * 将父类集合属性copy到子类集合中
     *
     * @param parentClass
     * @param childClass
     * @param tempList
     * @return
     */
    public static <T, K> List<T> copyParentList(Class<K> parentClass, Class<T> childClass, List<K> tempList) {

        if (tempList == null) {
            return null;
        }

        BeanCopier beanCopier = BeanCopier.create(parentClass, childClass, false);

        List<T> list = new ArrayList<T>();
        try {
            for (K obj : tempList) {
                T newInstance = childClass.newInstance();
                beanCopier.copy(obj, newInstance, null);
                list.add(newInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    /**
     * 将父类属性copy到子类中
     *
     * @param parentClass
     * @param childClass
     * @param parent
     * @return
     */
    public static <T, K> T copyParentToChild(Class<K> parentClass, Class<T> childClass, K parent) {

        if (parent == null) {
            return null;
        }

        BeanCopier beanCopier = BeanCopier.create(parentClass, childClass, false);

        T newInstance = null;
        try {
            newInstance = childClass.newInstance();
            beanCopier.copy(parent, newInstance, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return newInstance;
    }
} 
