package com.zld.myutil.factory;

import com.zld.myutil.util.BeanUtils;

public class ClassInstanceFactory {
    private ClassInstanceFactory() {
    }

    /**
     * 实例化创建对象方法，该对象可以根据传入的字符串结构“属性:内容|属性:内容”
     *
     * @param clazz 要进行反射实例化的Class对象，有Class就可以反射实例化对象
     * @param value 要设置给对象的属性内容
     * @param <T>   方法的返回值类型，通过参数clazz获取
     * @return 一个已经配置好属性内容的Java类对象
     */
    public static <T> T create(Class<?> clazz, String value) {
        try {   //如果要想采用反射进行简单Java类对象属性设置的时候，类中必须要有无参构造
            Object obj = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.setValue(obj,value);
            return (T) obj; //返回对象
        } catch (Exception e) {
            e.printStackTrace();    //如果此时真的出现了错误，本质上抛出异常也没用
            return null;
        }
    }
}
