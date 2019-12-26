package com.example.lang.hello.model;

import java.lang.reflect.Method;

public class TikTok {

    private static TikTok tikTok=null;

    private Object pointer;
    private Method method;
    private String url;

    public void setPointer(Object pointer){
        this.pointer=pointer;
    }

    public Object getPointer() {
        return pointer;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    private TikTok(){}

    public static TikTok newInstance(){
        if (tikTok==null){
            synchronized (TikTok.class){
                while (tikTok==null) {
                    tikTok = new TikTok();
                }
            }
        }
        return tikTok;
    }

}
