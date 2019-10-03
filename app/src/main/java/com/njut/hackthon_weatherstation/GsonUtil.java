package com.njut.hackthon_weatherstation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtil {


    public static <T> T parseJsonWithGson(String json, Class<T> type) {

        T result  = new Gson().fromJson(json, type);
        return result;
    }


    public static <T> List<T> parseArrayJsonWithGson(String json, Class<T> type) {
        List<T> list = new Gson().fromJson(json, new TypeToken<List<T>>(){}.getType());
        return list;
    }
}