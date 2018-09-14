package com.lilee;

import android.app.Application;
import android.content.Context;

public class HookActApp extends Application{

    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
