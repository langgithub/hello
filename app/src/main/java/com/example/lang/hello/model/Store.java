package com.example.lang.hello.model;

import com.virjar.sekiro.api.SekiroResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    public static Map<Object, SekiroResponse> requestTaskMap = new ConcurrentHashMap<>();
}
