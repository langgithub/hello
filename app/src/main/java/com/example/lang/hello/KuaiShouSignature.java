package com.example.lang.hello;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.lang.hello.handler.WhatsAppHandler;
import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.WhatsApp;
import com.virjar.sekiro.api.SekiroClient;
import com.virjar.sekiro.api.SekiroResponse;

import java.util.UUID;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class KuaiShouSignature implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {
            if("com.smile.gifmaker".equals(lpparam.packageName)) {
                //在主进程里面启动服务
                XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                        XposedBridge.log("hook start");

                        Class<?> aClass = XposedHelpers.findClass("com.yxcorp.gifshow.activity.GifshowActivity", cl);
                        XposedBridge.log(aClass.getName());
                        // 启动服务hook当前 mainActivity
                        XposedHelpers.findAndHookMethod(aClass, "onCreate", Bundle.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                XposedBridge.log("hook signatures");
                                Context context= (Context) param.thisObject;
                                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                                        context.getPackageName(), PackageManager.GET_SIGNATURES);
                                if (packageInfo.signatures != null) {
                                    System.out.println(packageInfo.signatures[0].toCharsString());
                                    XposedBridge.log(packageInfo.signatures[0].toCharsString());
                                    Log.d("", "sig:"+packageInfo.signatures[0].toCharsString()+
                                            "hashcode:"+packageInfo.signatures[0].hashCode());
                                }
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                XposedBridge.log("hook signatures");
                            }
                        });
                    }
                });
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
            XposedBridge.log(e.getMessage());
        }
    }
}