package com.example.lang.hello.handler;

import android.util.Log;

import com.example.lang.hello.model.BeiBei;
import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.TikTok;
import com.lang.sekiro.api.SekiroRequest;
import com.lang.sekiro.api.SekiroRequestHandler;
import com.lang.sekiro.api.SekiroResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedHelpers;

public class BeiBeiHandler implements SekiroRequestHandler {

    @Override
    public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
        Log.d("Xposed","handleRequest");
        BeiBei beiBei = BeiBei.newInstance();
        Store.requestTaskMap.put(beiBei, sekiroResponse);

        String str1= "01\\nPOST\\ne4a139db02ab93c96816cb5ef58dd93c\\n/gateway/route?client_info={\"bd\":\"huaweit\",\"abd\":\"01db21bbb7\",\"package\":\"show\",\"os\":\"6.0.1\",\"screen\":\"1440x2392\",\"dn\":\"Nexus 6P\",\"version\":\"9.41.00\",\"platform\":\"Android\",\"network\":\"WiFi\",\"app_name\":\"beibei\",\"model\":\"Nexus 6P\",\"udid\":\"a0bb111b5414d9a1\"}&method=beibei.user.code.send&timestamp=1591781075\\n1591781075";
        if(beiBei.getClassLoader() != null){
            XposedHelpers.callMethod(XposedHelpers.findClass("",beiBei.getClassLoader()),"a", str1.getBytes());
        }

//        String url="https://api.tiktokv.com/passport/mobile/sms_login/?os_api=22&device_type=MX4&ssmix=a&manifest_version_code=256&dpi=480&carrier_region=&region=US&app_name=trill&version_name=2.5.6&timezone_offset=28800&is_my_cn=0&fp=a_fake_fp&ac=wifi&update_version_code=2560&channel=googleplay&_rticket=1577173465355&device_platform=android&iid=6773472769088702210&build_number=2.5.6&version_code=256&timezone_name=Asia%2FShanghai&openudid=26332057d592614a&device_id=6773471792155084290&sys_region=CN&app_language=en&resolution=1152*1920&os_version=5.1&device_brand=Meizu&language=zh&aid=1180&mcc_mnc=";
//        Method a = tikTok.getMethod();
//        a.setAccessible(true);
//        Log.d("Xposed","01111111111111111");
//        // xposed调用函数
//        try {
//            String abc=(String) a.invoke(tikTok.getPointer(),url,null,false);
//            Log.e("url>>>>>>>>>>",abc);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }
}
