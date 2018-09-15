package com.lilee.pluginone;

import com.lilee.plugin.lib.IBean;
import com.lilee.plugin.lib.ICallback;

public class Bean implements IBean{

    private String name = "pluginOne_apk_move_success";

    public String getName() {
        return name;
    }

    @Override
    public void registerCallBack(ICallback iCallback) {
        iCallback.sendResult(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
