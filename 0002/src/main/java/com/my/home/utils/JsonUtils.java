package com.my.home.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 */
public class JsonUtils
{
    public static <V> V getObject(String json, Class<V> cls)
    {
        Gson gson = new Gson();
        V out = gson.fromJson(json, cls);
        return out;
    }
    public static <V> String getJson(V object)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(object);
        return json;
    }
}
