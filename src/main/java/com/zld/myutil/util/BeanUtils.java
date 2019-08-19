package com.zld.myutil.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanUtils {
    private BeanUtils() {
    }

    /**
     * 实现指定对象的属性设置
     *
     * @param obj   要进行反射操作的实例化对象
     * @param value 包含有指定内容的字符串，格式“属性:内容|属性:内容”
     */
    public static void setValue(Object obj, String value) {
        String results[] = value.split("\\|");//按照“|”进行每一组属性的拆分
        for (int i = 0; i < results.length; i++) {//循环设置属性内容
            //attval[0]保存的是属性名称，attval[1]保存的是属性内容
            String[] attval = results[i].split(":");//获取“属性名称”与内容
            try {
                if (attval[0].contains(".")) {//多级配置
                    String[] temp = attval[0].split("\\.");
                    Object currentObject = obj;
                    //最后一位肯定是制定类中的属性名称，所以不在本次实例化处理的范畴之内
                    for (int j = 0; j < temp.length - 1; j++) {//实例化
                        //调用相应的getter方法，如果getter方法返回了null表示该对象未实例化
                        Method getMethod = currentObject.getClass().getDeclaredMethod("get" + StringUtils.initcap(temp[j]));
                        if (getMethod.invoke(currentObject) == null) {//未实例化
                            Field field = currentObject.getClass().getDeclaredField(temp[j]);//获取成员
                            Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtils.initcap(temp[j]), field.getType());
                            setMethod.invoke(currentObject, field.getType().getConstructor().newInstance());//调用setter方法设置属性内容
                        }
                        currentObject = getMethod.invoke(currentObject);
                    }
                    //进行属性内容的设置
                    Field field = currentObject.getClass().getDeclaredField(temp[temp.length-1]);//获取成员
                    Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtils.initcap(temp[temp.length-1]), field.getType());
                    Object convertVal = BeanUtils.convertAttributeValue(field.getType().getName(), attval[1]);
                    setMethod.invoke(currentObject, convertVal);//调用setter方法设置属性内容
                } else {
                    Field field = obj.getClass().getDeclaredField(attval[0]);//获取成员
                    Method setMethod = obj.getClass().getDeclaredMethod("set" + StringUtils.initcap(attval[0]), field.getType());
                    Object convertVal = BeanUtils.convertAttributeValue(field.getType().getName(), attval[1]);
                    setMethod.invoke(obj, convertVal);//调用setter方法设置属性内容
                }
            } catch (Exception e) {
            }
        }
    }

    public static Object convertAttributeValue(String type, String value) {
        if ("long".equals(type) || "java.lang.Long".equals(type)) {
            return Long.parseLong(value);
        } else if ("int".equals(type) || "java.lang.Integer".equals(type)) {
            return Integer.parseInt(value);
        } else if ("double".equals(type) || "java.lang.Double".equals(type)) {
            return Double.parseDouble(value);
        } else if ("java.util.Date".equals(type)) {
            SimpleDateFormat sdf;
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else if (value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else {
                return new Date();
            }

            try {
                return sdf.parse(value);
            } catch (ParseException e) {
                return new Date();
            }
        } else {
            return value;
        }
    }
}
