package com.example.lang.hello;

import android.app.Application;
import android.content.Context;

import com.example.lang.hello.handler.BeiBeiHandler;
import com.example.lang.hello.model.BeiBei;
import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.TikTok;
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

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class BeiBeiXposed implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try {
            if("com.husor.beibei".equals(lpparam.packageName)) {
                //在主进程里面启动服务
                final SekiroClient sekiroClient = SekiroClient.start("123.57.36.150",11000 , UUID.randomUUID().toString(), "duotou");
                sekiroClient.registerHandler("beibei", new BeiBeiHandler());

                XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ClassLoader cl = ((Context) param.args[0]).getClassLoader();

                        BeiBei beiBei = BeiBei.newInstance();
                        if (beiBei.getClassLoader() == null){
                            XposedBridge.log("保存类加载器");
                            beiBei.setClassLoader(cl);
                        }


                        final Class<?> SecurityUtils = XposedHelpers.findClass("com.husor.beibei.utils.SecurityUtils", cl);
                        findAndHookMethod(SecurityUtils, "a", Byte[].class, new XC_MethodHook() {

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                BeiBei beiBei = BeiBei.newInstance();
                                SekiroResponse sekiroResponse = Store.requestTaskMap.remove(beiBei);
                                if(sekiroResponse!=null){
                                    XposedBridge.log("sekiroResponse >>>>"+param.getResult());
                                    sekiroResponse.success(param.getResult());
                                }
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
