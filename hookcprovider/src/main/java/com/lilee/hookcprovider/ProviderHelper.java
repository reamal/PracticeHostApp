package com.lilee.hookcprovider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProviderHelper {
    public static void installProviders(Context context, File apkFiles) throws Exception {
        List<ProviderInfo> providers = parseProviders(apkFiles);

        for (ProviderInfo providerInfo:providers) {
            providerInfo.applicationInfo.packageName = context.getPackageName();
        }

        Object sCurrentActivityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        Class[] p1 = {Context.class, List.class};
        Object[] v1 = {context, providers};

        RefInvoke.invokeInstanceMethod(sCurrentActivityThread,"installContentProviders",p1,v1);
    }

    private static List<ProviderInfo> parseProviders(File apkFile) throws Exception {

            Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
            Object packageParser = packageParserClass.newInstance();

            //调用packageParser获取apk对象中的package对象
            Class[] p1 = {File.class, int.class};
            Object[] v1 = {apkFile, PackageManager.GET_PROVIDERS};
            Object packageObj = RefInvoke.invokeInstanceMethod(packageParser, "parsePackage", p1, v1);

            //读取Package对象里面的providers字段
            List providers = (List) RefInvoke.getFieldObject(packageObj, "providers");

            //准备generateProviderInfo方法所需要的参数
            Class<?> packageParser$ProviderClass = Class.forName("android.content.pm.PackageParser$Provider");
            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            Object defaultUserState = packageUserStateClass.newInstance();
            int userId = (Integer) RefInvoke.invokeStaticMethod("android.os.UserHandle", "getCallingUserId");
            Class[] p2 = {packageParser$ProviderClass, int.class, packageUserStateClass, int.class};

            List<ProviderInfo> ret = new ArrayList<>();
            for (Object provider :providers) {
                Object[] v2 = {provider,0,defaultUserState,userId};
                ProviderInfo providerInfo = (ProviderInfo) RefInvoke.invokeInstanceMethod(packageParser,"generateProviderInfo",p2,v2);
                ret.add(providerInfo);
            }

            return ret;
    }
}
