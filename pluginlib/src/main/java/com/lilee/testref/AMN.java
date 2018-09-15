package com.lilee.testref;

public class AMN {

//    private static final Singleton<ClassB2Interface> gDefault = new Singleton<ClassB2Interface>() {
//        @Override
//        protected ClassB2Interface create() {
//            ClassB2 classB2 = new ClassB2();
//            classB2.id = 112;
//            return classB2;
//        }
//    };

    private static final InnerSingleton gDefault = new InnerSingleton();

    public static class InnerSingleton extends Singleton<ClassB2Interface> {
        @Override
        protected ClassB2Interface create() {
            ClassB2 classB2 = new ClassB2();
            classB2.id = 112;
            return classB2;
        }
    }


    static public ClassB2Interface getDefault() {
        return gDefault.get();
    }
}
