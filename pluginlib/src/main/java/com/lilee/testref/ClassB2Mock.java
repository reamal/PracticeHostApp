package com.lilee.testref;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ClassB2Mock implements InvocationHandler {

    private Object mBase;

    public ClassB2Mock(Object mBase) {
        this.mBase = mBase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getId")){
            return 777;
        }
        return method.invoke(mBase,args);
    }
}
