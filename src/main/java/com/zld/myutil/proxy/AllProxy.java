package com.zld.myutil.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AllProxy implements InvocationHandler {
    private Object target; //保存真实业务对象

    /**
     * 进行真实业务对象与代理业务对象之间的绑定处理
     *
     * @param target 真实业务对象
     * @return Proxy生成的代理业务对象
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    public boolean connect(){
        System.out.println("【消息代理】进行消息发送通道的连接");
        return true;
    }

    public void close(){
        System.out.println("【消息代理】关闭消息通道");
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("***********【执行方法】"+method);
        Object returnData = null;
        if(this.connect()){
            returnData = method.invoke(this.target, args);
            this.close();
        }
        return returnData;
    }
}
