package com.lilee.pluginone;

import android.content.Context;

import com.lilee.plugin.lib.IDynamicRes;

public class DynamicRes implements IDynamicRes {
    @Override
    public String getStringForResId(Context context) {
        return context.getResources().getString(R.string.pluginOne_string_hello_plugin);
    }
}
