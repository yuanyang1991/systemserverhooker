package com.yuanyang.systemserverhooker;

import android.util.Log;

import com.yuanyang.lib.ServiceBinderInterceptor;

import java.lang.reflect.Method;

/**
 * 说明：
 * 作者：yuany
 * 时间：2020/4/3 11:10
 */
public class TeleHandler implements ServiceBinderInterceptor.BinderInvocationHandler {


    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        String methodNmae = method.getName();
        if ("getDeviceId".equals(methodNmae) || "getSubscriberId".equals(methodNmae)) {
            Log.i("HookServiceManager", methodNmae);
            Throwable throwable = new Throwable();
            throwable.printStackTrace();
        }
        return method.invoke(target, args);
    }
}
