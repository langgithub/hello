package com.example.lang.hello;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposeEntry implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
//        if(loadPackageParam.packageName.equals("")){
        try{
            findAndHookMethod("android.content.res.Resources",loadPackageParam.classLoader,
                    "getColor",int.class,new my_getColor());
        }catch (Exception e){
            Log.e("Xposed",e.getMessage());
        }
//        }


    }
}
class my_getColor extends XC_MethodHook{

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//        super.beforeHookedMethod(param);
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//        super.afterHookedMethod(param);
        Log.d("Exposed Exaple","Beforce Method Hooc");
        int rel= (int) param.getResult();
        rel=rel & ~0x0000ff00 | 0x00ff0000;
        param.setResult(rel);
    }
}
