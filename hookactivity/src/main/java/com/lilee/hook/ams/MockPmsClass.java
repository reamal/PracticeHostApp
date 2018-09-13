package com.lilee.hook.ams;

import android.content.pm.PackageInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MockPmsClass implements InvocationHandler{

    private Object sPackageManager;

    public MockPmsClass(Object sPackageManager) {
        this.sPackageManager = sPackageManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getPackageInfo")) {
            return new PackageInfo();
        }
        return method.invoke(sPackageManager, args);
    }
}
