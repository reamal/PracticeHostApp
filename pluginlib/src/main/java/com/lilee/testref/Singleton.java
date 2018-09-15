package com.lilee.testref;

public abstract class Singleton<T> {

    private T mInstance;

    protected abstract T create();

    public final T get() {
        if (mInstance == null) {
            synchronized (this) {
                mInstance = create();
            }
        }
        return mInstance;
    }
}
