package com.lilee.hook.ams;

import android.content.ComponentName;
import android.content.Intent;


import com.lilee.stub.StubActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MockActivityManagerClass implements InvocationHandler {

    private Object mBase;

    public MockActivityManagerClass(Object mBase) {
        this.mBase = mBase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            Intent raw;
            int index = 0;

            for (int i = 0 ; i < args.length ; i ++){
                if (args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }

            raw = (Intent) args[index];

            //传给底层要启动的假的intent
            Intent newIntent = new Intent();

            String stubPackage = "com.lilee.hook2act";
            ComponentName componentName = new ComponentName(stubPackage, StubActivity.class.getName());
            newIntent.setComponent(componentName);

            //将要启动的intent存起来。
            newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT,raw);

            args[index] = newIntent;

            return method.invoke(mBase,args);

        }
        return method.invoke(mBase, args);
    }
}
