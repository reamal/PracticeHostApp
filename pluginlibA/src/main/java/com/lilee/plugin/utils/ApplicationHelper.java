package com.lilee.plugin.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.File;

public final class ApplicationHelper {

    private static final String TAG = "ApplicationHelper";

    /**
     * 解析apk自定义的application名字
     * @param context
     * @param apkFile
     * @return
     */
    public static String loadApplication(Context context, File apkFile) {
        // 首先调用parsePackage获取到apk对象对应的Package对象
        Object packageParser = RefInvoke.createObject("android.content.pm.PackageParser");
        Class[] p1 = {File.class, int.class};
        Object[] v1 = {apkFile, PackageManager.GET_RECEIVERS};
        Object packageObj = RefInvoke.invokeInstanceMethod(packageParser, "parsePackage", p1, v1);

        Object obj = RefInvoke.getFieldObject(packageObj, "applicationInfo");
        ApplicationInfo applicationInfo = (ApplicationInfo)obj;
        return applicationInfo.className;
    }
}
