package com.example.lang.hello.handler;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.example.lang.hello.model.Store;
import com.example.lang.hello.model.WhatsApp;
import com.virjar.sekiro.api.SekiroRequest;
import com.virjar.sekiro.api.SekiroRequestHandler;
import com.virjar.sekiro.api.SekiroResponse;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


public class WhatsAppHandler implements SekiroRequestHandler {

    @Override
    public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
        // 获取request参数
        String phone = sekiroRequest.getString("phone");
        WhatsApp whatsApp = WhatsApp.newInstance();
        if (whatsApp.getX()!=null && whatsApp.getView()!=null){
            XposedHelpers.callMethod(whatsApp.getX(),"onClick",whatsApp.getView());
            whatsApp.setX(null);
            whatsApp.setView(null);
        }

        // 绑定与xposed通讯
        Store.requestTaskMap.put(whatsApp, sekiroResponse);
        if (whatsApp.getActivity()!=null){
            Uri uri = Uri.parse("whatsapp://send?phone="+phone);
            whatsApp.getActivity().startActivity(new Intent(Intent.ACTION_VIEW,uri));
        }
    }
}

