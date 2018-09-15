package com.lilee.pluginlib;


import com.lilee.testref.AMN;
import com.lilee.testref.ClassB2Interface;
import com.lilee.testref.ClassB2Mock;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.ShadowLog;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class TestRef {


    private final static String TAG = "liTag";

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
    }

    @Test
    public void testRef() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        Class<?> singleton = Class.forName("com.lilee.testref.Singleton");
        Field mInstanceFiled = singleton.getDeclaredField("mInstance");
        mInstanceFiled.setAccessible(true);

        ShadowLog.d(TAG, "singleton.getSimpleName() : " + singleton.getSimpleName());
        ShadowLog.d(TAG, "mInstanceFiled.getName() : " + mInstanceFiled.getName());

        Class<?> amnClass = Class.forName("com.lilee.testref.AMN");

        Field gDefaultFiled = amnClass.getDeclaredField("gDefault");

        ShadowLog.d(TAG, "gDefaultFiled.getName() : " + gDefaultFiled.getName());

        gDefaultFiled.setAccessible(true);

        Object gDefaultObject = gDefaultFiled.get(null);

        //返回的o为null
        Object o = mInstanceFiled.get(gDefaultObject);
        int id = AMN.getDefault().getId();
        ShadowLog.d(TAG, "id : " + id);


        Class<?> classB2InterfaceClass = Class.forName("com.lilee.testref.ClassB2Interface");

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                , new Class[]{classB2InterfaceClass}, new ClassB2Mock(o));

        mInstanceFiled.set(gDefaultObject, proxy);

        //检验代理是否成功
        int id2 = AMN.getDefault().getId();
        ShadowLog.d(TAG, "id2 : " + id2);
    }
}
