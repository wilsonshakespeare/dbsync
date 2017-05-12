package com.research.dbsync.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import javadz.beans.BeanInfo;
import javadz.beans.IntrospectionException;
import javadz.beans.Introspector;
import javadz.beans.PropertyDescriptor;

/**
 * Created by iFreedom87 on 8/31/16.
 */

public final class ReflectionUtils {

    public static Object getPropertyValue(Object bean, String property)
            throws IntrospectionException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Class<?> beanClass = bean.getClass();
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(
                beanClass, property);
        if (propertyDescriptor == null) {
            throw new IllegalArgumentException("No such property " + property
                    + " for " + beanClass + " exists");
        }

        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            throw new IllegalStateException("No getter available for property "
                    + property + " on " + beanClass);
        }
        return readMethod.invoke(bean);
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass,
                                                           String propertyname) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor = null;
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor currentPropertyDescriptor = propertyDescriptors[i];
            if (currentPropertyDescriptor.getName().equals(propertyname)) {
                propertyDescriptor = currentPropertyDescriptor;
            }

        }
        return propertyDescriptor;
    }

    public static Class<?> getGenericType(Class<?> currentClass, String fieldName){
        try {
            Field field = currentClass.getDeclaredField(fieldName);
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class<?> aClass = (Class<?>) type.getActualTypeArguments()[0];
            return aClass;
        }catch (Exception exp){
            return null;
        }
    }

    public static Class<?>[] getGenericTypes(Class<?> currentClass, String fieldName){
        try {
            Field field = currentClass.getDeclaredField(fieldName);
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class<?> classes[] = (Class<?>[]) type.getActualTypeArguments();
            return classes;
        }catch (Exception exp){
            return null;
        }
    }
}
