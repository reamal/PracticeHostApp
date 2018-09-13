package com.lilee.hook.ams;

import android.os.Handler;

import com.lilee.hook.utils.RefInvoke;

import java.lang.reflect.Proxy;

public class AMSHookHelper {

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookAMN() throws ClassNotFoundException {

        // 获取AMN的gDefault单例gDefault，gDefault是final静态的
        Object gDefault = RefInvoke.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");

        // gDefault是一个 android.util.Singleton<T>对象; 我们取出这个单例里面的mInstance字段
        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", gDefault, "mInstance");

        Class<?> classIAM = Class.forName("android.app.IActivityManager");

        Object proxy = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader()
                , new Class[]{classIAM}
                , new MockActivityManagerClass(mInstance));

        RefInvoke.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxy);
    }

    /**
     *
     */
    public static void hookActivityThread() {
        Object sCurrentActivityThread = RefInvoke.getStaticFieldObject(
                "android.app.ActivityThread", "sCurrentActivityThread");

        Handler mH = (Handler) RefInvoke.getFieldObject(sCurrentActivityThread,"mH");

        RefInvoke.setFieldObject(Handler.class,mH,"mCallBack",new MockHCallBackClass(mH));
    }
}
