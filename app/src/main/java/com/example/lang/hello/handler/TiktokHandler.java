package com.example.lang.hello.handler;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.TikTok;
import com.virjar.sekiro.api.SekiroRequest;
import com.virjar.sekiro.api.SekiroRequestHandler;
import com.virjar.sekiro.api.SekiroResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.support.v4.content.ContextCompat.startActivity;

public class TiktokHandler implements SekiroRequestHandler {

    @Override
    public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
        // TODO
        // 获取request参数
        // 绑定与xposed通讯

        Log.d("Xposed","01111111111111111");
        TikTok tikTok = TikTok.newInstance();
        Store.requestTaskMap.put(tikTok, sekiroResponse);

        String url="https://api.tiktokv.com/passport/mobile/sms_login/?os_api=22&device_type=MX4&ssmix=a&manifest_version_code=256&dpi=480&carrier_region=&region=US&app_name=trill&version_name=2.5.6&timezone_offset=28800&is_my_cn=0&fp=a_fake_fp&ac=wifi&update_version_code=2560&channel=googleplay&_rticket=1577173465355&device_platform=android&iid=6773472769088702210&build_number=2.5.6&version_code=256&timezone_name=Asia%2FShanghai&openudid=26332057d592614a&device_id=6773471792155084290&sys_region=CN&app_language=en&resolution=1152*1920&os_version=5.1&device_brand=Meizu&language=zh&aid=1180&mcc_mnc=";
        Method a = tikTok.getMethod();
        a.setAccessible(true);
        Log.d("Xposed","01111111111111111");
        // xposed调用函数
        try {
            String abc=(String) a.invoke(tikTok.getPointer(),url,null,false);
            Log.e("url>>>>>>>>>>",abc);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        Uri uri = Uri.parse("whatsapp://send?phone=8618800008888");
//        startActivity(new Intent(Intent.ACTION_VIEW,uri));




    }
}
