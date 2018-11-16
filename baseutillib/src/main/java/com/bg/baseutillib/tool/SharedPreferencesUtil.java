package com.bg.baseutillib.tool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;


/**
 * SharedPreferences缓存工具类
 */

public class SharedPreferencesUtil {
    public static final String SHARE_PREFERENCE_FILE_NAME = "sp_file_name";//数据保存文件
    public static final String COOKIE_KEY = "cookie-key";
    public static final String COOKIE_VALUE = "cookie-value";

    public static Context context;

    /**
     * 初始化SharedPreferences，使用该类前必须先进行初始化
     *
     * @param context
     */
    public static void init(Context context) {
        SharedPreferencesUtil.context = context;
    }

    /**
     * 缓存cookie
     *
     * @param cookieName
     * @param cookieValue
     */
    public static void writeCookie(String cookieName, String cookieValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COOKIE_KEY, cookieName);
        editor.putString(COOKIE_VALUE, cookieValue);
        editor.commit();
    }

    /**
     * 获取缓存的cookie的key
     *
     * @return
     */
    public static String readCookieName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COOKIE_KEY, "");
    }

    /**
     * 获取缓存的cookie的value
     *
     * @return
     */
    public static String readCookieVaule() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(COOKIE_VALUE, "");
    }

    /**
     * 缓存String类型数据
     *
     * @param key
     * @param value
     */
    public static void writeString(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 缓存boolean类型数据
     *
     * @param key
     * @param value
     */
    public static void writeBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取string类型数据
     *
     * @param key
     * @return
     */
    public static String readString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * 获取boolean类型数据
     *
     * @param key
     * @return
     */
    public static boolean readBoolean(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 删除String类型数据
     *
     * @param key
     */
    public static void removeString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 删除boolean类型数据
     *
     * @param key
     */
    public static void removeBoolean(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 删除所有缓存的数据
     */
    public static void removeAll() {
        removeAll(SHARE_PREFERENCE_FILE_NAME);
    }

    /**
     * 删除指定文件名的缓存数据
     *
     * @param fileName
     */
    public static void removeAll(String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Map<String, ?> all = sharedPreferences.getAll();
        Set<String> keySet = all.keySet();
        for (String key : keySet) {
            editor.remove(key);
        }
        editor.commit();
    }
}
