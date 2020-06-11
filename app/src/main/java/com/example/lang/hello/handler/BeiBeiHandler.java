package com.example.lang.hello.handler;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.lang.hello.model.BeiBei;
import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.TikTok;
import com.lang.sekiro.api.SekiroRequest;
import com.lang.sekiro.api.SekiroRequestHandler;
import com.lang.sekiro.api.SekiroResponse;

import org.json.JSONArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class BeiBeiHandler implements SekiroRequestHandler {

    public static String mo12118a(HashMap map) {
        StringBuilder sb = new StringBuilder();
        Object[] array = map.keySet().toArray();
        Arrays.sort(array);
        for (Object obj : array) {
            sb.append(obj);
            sb.append(map.get(obj));
        }
        return sb.toString();
    }

    @Override
    public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
        XposedBridge.log("handleRequest");
        BeiBei beiBei = BeiBei.newInstance();
//        Store.requestTaskMap.put(beiBei, sekiroResponse);
        String client_info="{\"bd\":\"huaweit\",\"abd\":\"01db21bbb7\",\"package\":\"show\",\"os\":\"6.0.1\",\"screen\":\"1440x2392\",\"dn\":\"Nexus 6P\",\"version\":\"9.41.00\",\"platform\":\"Android\",\"network\":\"WiFi\",\"app_name\":\"beibei\",\"model\":\"Nexus 6P\",\"udid\":\"a0bb111b5414d9a1\"}";
        String method = "beibei.user.code.send";
        String phone= sekiroRequest.getString("phone");
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);

        HashMap<String,String> map =new HashMap<>();
        map.put("client_info",client_info);
        map.put("method",method);
        map.put("timestamp",timestamp);
        if(beiBei.getClassLoader() != null){
            XposedBridge.log("获取加密参数");
            Class<?> classzz = XposedHelpers.findClass("com.husor.beibei.utils.SecurityUtils",beiBei.getClassLoader());
            if(classzz!=null){
                XposedBridge.log(classzz.getName());
                String body= "tel="+phone+"&key=find_password";
                Long e = Long.valueOf(timestamp);
                // 将request中body sign 加密
                String signBodey = ((String) XposedHelpers.callStaticMethod(classzz,"a", body.getBytes())).toLowerCase();
                XposedBridge.log("signBodey 参数: "+ signBodey);
                String str2="01\nPOST\n"+signBodey+"\n/gateway/route?client_info="+client_info+"&method="+method+"&timestamp="+ e +"\n"+e;

                //  MAC.HMACSHA1
                String mac = (String) XposedHelpers.callStaticMethod(classzz,"a", str2, "Sce7vsMfgMORNA1o");
                XposedBridge.log("mac 参数: "+ mac);
                // 最总_abr_
                String _abr_ = ("01" + mac + Long.toHexString(e)).toLowerCase();
                XposedBridge.log("_abr_ 参数: "+ _abr_);
                map.put("_abr_",_abr_);
                // sign
                String sign = (String) XposedHelpers.callStaticMethod(classzz,"a", BeiBeiHandler.mo12118a(map), true);
                XposedBridge.log("sign 参数: "+ sign);
                map.put("sign",sign);

                XposedBridge.log("param 参数: "+ JSON.toJSONString(map));
                if(sekiroResponse!=null){
                    sekiroResponse.success(JSON.toJSONString(map));
                }
            }else {
                XposedBridge.log("null");
                if(sekiroResponse!=null){
                    sekiroResponse.success("error");
                }
            }
        }
    }
}
