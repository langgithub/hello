package com.example.lang.hello.model;


public class BeiBei {
    private static BeiBei beiBei=null;

    private ClassLoader classLoader;

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    private BeiBei(){}

    public static BeiBei newInstance(){
        if (beiBei==null){
            synchronized (BeiBei.class){
                while (beiBei==null) {
                    beiBei = new BeiBei();
                }
            }
        }
        return beiBei;
    }

}
