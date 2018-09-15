package com.lilee.hookservice.amshook;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.lilee.hookservice.HookApp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MockIamClass implements InvocationHandler {

    private static final String TAG = "liTag";
    Object mBase;
    String stubPackage = "com.lilee.hookservice";

    public MockIamClass(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("startService".equals(method.getName())) {
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始StubService启动别的Service偷梁换柱

            // 找到参数里面的第一个Intent 对象
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            //get StubService form UPFApplication.pluginServices
            Intent rawIntent = (Intent) args[index];
            String rawServiceName = rawIntent.getComponent().getClassName();

            String stubServiceName = HookApp.pluginServices.get(rawServiceName);

            // replace Plugin Service of StubService
            ComponentName componentName = new ComponentName(stubPackage, stubServiceName);
            Intent newIntent = new Intent();
            newIntent.setComponent(componentName);

            // Replace Intent, cheat AMS
            args[index] = newIntent;

            Log.d(TAG, "hook success");
            return method.invoke(mBase, args);
        } else if ("stopService".equals(method.getName())) {
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始StubService启动别的Service偷梁换柱

            // 找到参数里面的第一个Intent 对象
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            //get StubService form UPFApplication.pluginServices
            Intent rawIntent = (Intent) args[index];
            String rawServiceName = rawIntent.getComponent().getClassName();
            String stubServiceName = HookApp.pluginServices.get(rawServiceName);

            // replace Plugin Service of StubService
            ComponentName componentName = new ComponentName(stubPackage, stubServiceName);
            Intent newIntent = new Intent();
            newIntent.setComponent(componentName);

            // Replace Intent, cheat AMS
            args[index] = newIntent;

            Log.d(TAG, "hook success");
            return method.invoke(mBase, args);
        }

        return method.invoke(mBase,args);
    }
}
