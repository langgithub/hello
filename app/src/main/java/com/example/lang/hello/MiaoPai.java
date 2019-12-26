package com.example.lang.hello;

import com.example.lang.hello.handler.TiktokHandler;
import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.TikTok;
import com.virjar.sekiro.api.SekiroClient;
import com.virjar.sekiro.api.SekiroResponse;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by yuanlang on 2019/5/7.
 */

public class MiaoPai implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        try {
            if("com.ss.android.ugc.trill".equals(lpparam.packageName)) {
                //在主进程里面启动服务
                final SekiroClient sekiroClient = SekiroClient.start("sekiro.virjar.com", UUID.randomUUID().toString(), "weishi-demo");
                sekiroClient.registerHandler("tiktokHandler", new TiktokHandler());

                final Class<?> hookclass = XposedHelpers.findClass("com.ss.android.ugc.aweme.app.a.c", lpparam.classLoader);
                final Object[] point = {null};

                findAndHookMethod(hookclass, "a", String.class, List.class, boolean.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        TikTok tikTok = TikTok.newInstance();
                        if (tikTok.getPointer() == null) {
                            XposedBridge.log("设置pointor");
                            Method method= hookclass.getDeclaredMethod("a", String.class, List.class, boolean.class);
                            tikTok.setMethod(method);
                            tikTok.setPointer(param.thisObject);
                        }else {
                            SekiroResponse sekiroResponse = Store.requestTaskMap.remove(tikTok);
                            if(sekiroResponse!=null){
                                sekiroResponse.success(param.getResult());
                            }
                        }

                    }
                });
                }
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }
    }
}
