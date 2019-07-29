package com.example.lang.hello;

import android.app.Application;
import android.content.Context;

import java.util.logging.Logger;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by yuanlang on 2019/5/7.
 */

public class MiaoPai implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {
            XposedBridge.log("bbbbbbb");
            if("com.vcredit.j1000".equals(lpparam.packageName)) {
                XposedBridge.log("01111111111111111");
                findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                        Class<?> hookclass = null;
                        try {
                            hookclass = cl.loadClass("com.vcredit.cp.main.common.BaseLoginActivity");
                        } catch (Exception e) {
                            return;
                        }
                        XposedBridge.log("99999999999999999999");


                        findAndHookMethod(hookclass, "a", new XC_MethodHook() {

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                param.setResult(true);
                                XposedBridge.log(param.getResult().toString());

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
