package com.lilee.pluginlib;


import com.lilee.testref.AMN;
import com.lilee.testref.ClassB2Interface;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.ShadowLog;

import java.lang.reflect.Field;

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

        ShadowLog.d(TAG, "mInstanceFiled.getName() : " + mInstanceFiled.getName());

        Class<?> amnClass = Class.forName("com.lilee.testref.AMN");

        Field gDefaultFiled = amnClass.getDeclaredField("gDefault");

        ShadowLog.d(TAG, "gDefaultFiled.getName() : " + gDefaultFiled.getName());

        gDefaultFiled.setAccessible(true);

        AMN.InnerSingleton gDefault = (AMN.InnerSingleton) gDefaultFiled.get(null);

        Object o = mInstanceFiled.get(gDefault);
        ShadowLog.e(TAG, "o == null?" + (o == null));

        ClassB2Interface classB2Interface = gDefault.get();

        ShadowLog.d(TAG, "gDefault.getClass() : " + gDefault.getClass() + " , gDefault == null ? " + (gDefault == null));
        ShadowLog.d(TAG, "classB2Interface.getClass() : " + classB2Interface.getClass() + " , classB2Interface == null ? " + (classB2Interface == null));

        ShadowLog.d(TAG, "classB2Interface.getId() : " + classB2Interface.getId());
    }
}
