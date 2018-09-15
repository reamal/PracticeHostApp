package com.lilee.dhostapp.bean;

import android.content.pm.PackageInfo;

import dalvik.system.DexClassLoader;

public class PluginInfo {
    private String dexPath;
    private DexClassLoader classLoader;
    private PackageInfo packageInfo;


    public PluginInfo(String dexPath, DexClassLoader classLoader, PackageInfo packageInfo) {
        this.dexPath = dexPath;
        this.classLoader = classLoader;
        this.packageInfo = packageInfo;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getDexPath() {
        return dexPath;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}