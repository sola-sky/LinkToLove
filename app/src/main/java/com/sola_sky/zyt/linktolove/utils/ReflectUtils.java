package com.sola_sky.zyt.linktolove.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Li on 2016/3/10.
 */
public class ReflectUtils {
    private ReflectUtils() {

    }

    public static String toString(Object obj) {
        StringBuilder strBuilder = new StringBuilder();
        Class cl = obj.getClass();
        while (cl != null) {
            String name = cl.getName();
            strBuilder.append("[");
            Field[] fields = cl.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String fieldName = field.getName();
                    if (!strBuilder.toString().endsWith("[")) {
                        strBuilder.append(",");
                    }
                    strBuilder.append(fieldName + "=");
                    try {
                        Object val = field.get(obj);
                        strBuilder.append(toString(val));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            strBuilder.append("]");
            cl = cl.getSuperclass();
        }
        return strBuilder.toString();
    }


    public static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            String modifies = Modifier.toString(method.getModifiers());
            if (modifies.length() > 0) {
                System.out.print(modifies + " ");
            }
            Class retType = method.getReturnType();
            String methodName = method.getName();
            System.out.print(retType.getName() + " " + methodName + "(");
            Class[] paramTypes = method.getParameterTypes();
            int length = paramTypes.length;
            for (int i = 0; i < length; i++) {
                System.out.print(paramTypes[i].getName());
                if (i != length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(");");
        }
    }
}
