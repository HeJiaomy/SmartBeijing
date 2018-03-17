package com.smartbeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hejiao on 2018/2/4.
 */

public class PrefUtil {

    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences spf= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return spf.getBoolean(key,defValue);
    }
    public static void setBoolean(Context context,String key,boolean value){
        SharedPreferences spf= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        spf.edit().putBoolean(key,value).apply();
    }
    public static String getString(Context context,String key,String defValue){
        SharedPreferences spf= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return spf.getString(key,defValue);
    }
    public static void setString(Context context,String key,String value){
        SharedPreferences spf= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        spf.edit().putString(key,value).apply();
    }
    public static int getInt(Context context,String key,int defValue){
        SharedPreferences spf= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return spf.getInt(key,defValue);
    }
    public static void setInt(Context context,String key,int value){
        SharedPreferences spf= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        spf.edit().putInt(key,value).apply();
    }
}
