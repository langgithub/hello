package com.example.lang.hello;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.example.lang.hello.handler.TiktokHandler;
import com.example.lang.hello.handler.WhatsAppHandler;
import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.TikTok;
import com.example.lang.hello.model.WhatsApp;
import com.lang.sekiro.api.SekiroResponse;
import com.lang.sekiro.netty.client.SekiroClient;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WhatsAppXposed implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {
            if("com.whatsapp".equals(lpparam.packageName)) {
                //在主进程里面启动服务
                final SekiroClient sekiroClient = SekiroClient.start("sekiro.virjar.com", UUID.randomUUID().toString(), "weishi-demo");
//                final SekiroClient sekiroClient = SekiroClient.start("47.103.175.85",7007, UUID.randomUUID().toString(), "weishi-demo");
                sekiroClient.registerHandler("whatsAppHandler", new WhatsAppHandler());

                XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                        XposedBridge.log("hook start");

                        // 启动服务hook当前 mainActivity
                        XposedHelpers.findAndHookMethod("com.whatsapp.Main", cl, "onCreate", Bundle.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            WhatsApp whatsApp=WhatsApp.newInstance();
                            if (whatsApp.getActivity()==null){
                                whatsApp.setActivity((Activity) param.thisObject);
                                XposedBridge.log("hook success");
                            }
                            }
                        });

                        // 判断是否注册必调用类
                        Class<?> xClass = XposedHelpers.findClass("X.1HR", cl);
                        XposedHelpers.findAndHookMethod("X.01X", cl, "A0R", CharSequence.class,Context.class, Paint.class,xClass, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("hook message>>>>"+param.args[0].toString());
                            WhatsApp whatsApp = WhatsApp.newInstance();
                            if (!"WhatsApp".equals(param.args[0].toString()) && whatsApp.getActivity() != null) {
                                SekiroResponse sekiroResponse = Store.requestTaskMap.remove(whatsApp);
                                if(sekiroResponse!=null){
                                    XposedBridge.log("return  sekiroResponse>>>>"+param.args[0].toString());
                                    whatsApp.setMsg(param.args[0].toString());
                                    sekiroResponse.success(param.args[0].toString());
                                }
                            }
                            }
                        });

                        //解决频繁调用 Android组件重叠问题,释放资源
                        Class<?> dialog = XposedHelpers.findClass("com.whatsapp.DialogToastActivity$MessageDialogFragment", cl);
                        XposedHelpers.findAndHookConstructor("X.0cs", cl, dialog, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("点击dialog 确定按钮");
                                XposedHelpers.callMethod(param.args[0],"A0n");
                            }
                        });
                        Class<?> x = XposedHelpers.findClass("X.1XF", cl);
                        XposedHelpers.findAndHookConstructor("X.03u", cl, x, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("X.03u findAndHookConstructor");
                                WhatsApp whatsApp = WhatsApp.newInstance();
                                if(whatsApp.getX()==null){
                                    whatsApp.setX(param.thisObject);
                                }
                            }
                        });

                        Class<?> Context = XposedHelpers.findClass("android.content.Context", cl);
                        Class<?> AttributeSet = XposedHelpers.findClass("android.util.AttributeSet", cl);
                        XposedHelpers.findAndHookConstructor("X.1Wo", cl,Context,AttributeSet,int.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("X.1Wo findAndHookConstructor");
                                WhatsApp whatsApp = WhatsApp.newInstance();
                                if(whatsApp.getView()==null){
                                    whatsApp.setView(param.thisObject);
                                }
                            }
                        });

                        //解决频繁调用 Android组件重叠问题,释放资源
                        Class<?> Activity = XposedHelpers.findClass("android.app.Activity", cl);
                        XposedHelpers.findAndHookConstructor("X.16T", cl,Activity,new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("X.16T findAndHookConstructor");
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new MyThread(param.args[0]));
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }
    }
}

class MyThread implements Runnable {

    private Object object;

    public MyThread(Object object){
        this.object=object;
    }
    public void run() {
        XposedHelpers.callMethod(object,"onBackPressed");
    }
}