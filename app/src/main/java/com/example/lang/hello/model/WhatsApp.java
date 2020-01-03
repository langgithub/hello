package com.example.lang.hello.model;

import android.app.Activity;

public class WhatsApp {

    private static WhatsApp whatsApp=null;

    private Activity activity;

    private Object picker;

    private Object closeActivity;

    private String msg;

    private Object x;

    private Object view;

    public void setView(Object view) {
        this.view = view;
    }

    public Object getView() {
        return view;
    }

    public void setX(Object x) {
        this.x = x;
    }

    public Object getX() {
        return x;
    }

    public Object getPicker() {
        return picker;
    }

    public void setPicker(Object picker) {
        this.picker = picker;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCloseActivity(Object closeActivity) {
        this.closeActivity = closeActivity;
    }

    public Object getCloseActivity() {
        return closeActivity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    private WhatsApp(){}

    public static WhatsApp newInstance(){
        if (whatsApp==null){
            synchronized (WhatsApp.class){
                while (whatsApp==null) {
                    whatsApp = new WhatsApp();
                }
            }
        }
        return whatsApp;
    }
}
