package com.example.lang.hello;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by yuanlang on 2019/5/7.
 */

public class LogTest implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {
            if("com.hengchang.client".equals(lpparam.packageName)) {
                XposedBridge.log("com.hengchang.client hook--------------01111111111111111");
                findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                        Class<?> hookclass = null;
                        Class<?> hookclass2 = null;
                        Class<?> hookclass3 = null;
                        Class<?> hookclass4 = null;

                        try {
                            hookclass = cl.loadClass("com.hengchang.baselibrary.utils.LogUtils$Config");
                            hookclass2 = cl.loadClass("com.hengchang.baselibrary.utils.AESCryptUtils");
                            hookclass3 = cl.loadClass("com.hengchang.baselibrary.utils.AESCryptDataUtils");
                            hookclass4 = cl.loadClass("com.hengchang.baselibrary.utils.LogUtils");

                        } catch (Exception e) {
                            return;
                        }
                        XposedBridge.log("99999999999999999999");

                        findAndHookConstructor(hookclass, Context.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                XposedBridge.log("config beforeHookedMethod");
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                XposedBridge.log("config afterHookedMethod");
                            }
                        });

                        findAndHookMethod(hookclass, "setLogSwitch", boolean.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
//                                XposedBridge.log("setLogSwitch beforeHookedMethod");
//                                boolean getB= (boolean) param.args[0];
//                                XposedBridge.log(String.valueOf(getB));
                                param.args[0]=true;
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
//                                XposedBridge.log("setLogSwitch afterHookedMethod");
                            }
                        });

                        findAndHookMethod(hookclass4, "d", Object.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Object o=param.args[0];
                                if(o.toString().contains("校验手机号请求")){
                                    throw new Exception("---------------哈哈哈哈哈哈-------------------");
                                }
                                XposedBridge.log(o.toString());
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
//                                XposedBridge.log("setLogSwitch afterHookedMethod");
                            }
                        });

                        findAndHookMethod(hookclass, "setConsoleSwitch", boolean.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                XposedBridge.log("setConsoleSwitch beforeHookedMethod");
                                boolean getB= (boolean) param.args[0];
                                XposedBridge.log(String.valueOf(getB));
                                param.args[0]=true;
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                XposedBridge.log("setConsoleSwitch afterHookedMethod");
                            }
                        });

                        findAndHookMethod(hookclass2, "encryptBasedDes", String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                XposedBridge.log("hookclass2 encryptBasedDes beforeHookedMethod");
                                String arg0= (String) param.args[0];
                                XposedBridge.log("arg0:"+arg0);
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                XposedBridge.log("hookclass2 encryptBasedDes afterHookedMethod");
                            }
                        });

                        findAndHookMethod(hookclass3, "encryptBasedDes", String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                XposedBridge.log("hookclass3 encryptBasedDes beforeHookedMethod");
                                String arg0= (String) param.args[0];
                                XposedBridge.log("arg0:"+arg0);
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                XposedBridge.log("hookclass3 encryptBasedDes afterHookedMethod");
                            }
                        });

                    }
                });  // end of findAndHookMethod
            }

        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }
    }
}
