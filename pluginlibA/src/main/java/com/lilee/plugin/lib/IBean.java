package com.lilee.plugin.lib;

public interface IBean {
    void setName(String name);
    String getName();

    void registerCallBack(ICallback iCallback);
}
