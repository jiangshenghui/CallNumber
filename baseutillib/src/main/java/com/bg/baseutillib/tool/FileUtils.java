package com.bg.baseutillib.tool;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.bg.baseutillib.BgApplication;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 文件管理相关类
 * 建议文件路径创建方式(Environment.getExternalStorageDirectory() + File.separator + "bgTeam" + File.separator
 * + "QRImage" + File.separator + "share.jpg")
 * <p>
 * <p>
 * 获取文件路径的方法总结
 * <p>
 * 1、通过Environment获取的
 * Environment.getDataDirectory() = /data                          获得根目录/data (内部存储路径)
 * Environment.getDownloadCacheDirectory() = /cache                获得缓存目录/cache
 * Environment.getExternalStorageDirectory() = /mnt/sdcard         获得SD卡目录/mnt/sdcard（获取的是手机外置sd卡的路径）
 * Environment.getRootDirectory() = /system                        获得系统目录/system
 * <p>
 * 2、通过Context获取的
 * Context.getDatabasePath()                   返回通过Context.openOrCreateDatabase 创建的数据库文件
 * Context.getCacheDir().getPath()             用于获取APP的cache目录 /data/data/<application package>/cache目录
 * Context.getExternalCacheDir().getPath()     用于获取APP的在SD卡中的cache目录/mnt/sdcard/Android/data/<application package>/cache
 * Context.getFilesDir().getPath()             用于获取APP的files目录 /data/data/<application package>/files
 * Context.getObbDir().getPath()               用于获取APP SDK中的obb目录 /mnt/sdcard/Android/obb/<application package>
 * Context.getPackageName()                    用于获取APP的所在包目录
 * Context.getPackageCodePath()                来获得当前应用程序对应的 apk 文件的路径
 * Context.getPackageResourcePath()            获取该程序的安装包路径
 */
public class FileUtils {

    public static final File dataDirectory = Environment.getDataDirectory();
    public static final File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
    public static final File externalStorageDirectory = Environment.getExternalStorageDirectory();
    public static final File rootDirectory = Environment.getRootDirectory();

    public FileUtils() {

    }

    /**
     * 删除指定路径文件
     *
     * @param root 根目录
     * @param path 文件地址
     */
    public static void deleteFileForPath(File root, String path) {
        File file = new File(root, path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除整个文件夹里面的文件
     *
     * @param file 要删除的文件夹的所在位置
     */
    public static void deleteAllFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteAllFile(f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 创建指定路径文件夹
     *
     * @param root 根目录
     * @param path 文件夹地址
     */
    public static void createFileForPath(File root, String path) {
        File file = new File(root, path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 创建指定路径文件
     *
     * @param root 根目录
     * @param path 文件夹地址
     * @param name 文件名
     */
    public static void createFileNameForPath(File root, String path, String name) {
        createFileForPath(root, path);
        File fileName = new File(root, path + File.separator + name);
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从路径中提取文件名
     *
     * @param path 文件地址
     * @return
     */
    public static String getFileNameForPath(String path) {

        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);
        } else {
            return null;
        }
    }

    /**
     * 从路径中提取后缀名
     *
     * @param path 文件地址
     * @return
     */
    public static String getFileTypeForPath(String path) {
        File file = new File(path);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    /**
     * 获取文件真实路径(7.0适配，需PictureSelector图片选择与裁剪框架配合(Github: https://github.com/LuckSiege/PictureSelector))
     * 注：如有其它7.0图片框架 - 更换“com.luck.picture.lib.provider” 即可
     * <p>
     * 判断协议是不是content://开头，有的话就用ContentResolver去query查询文件真实位置
     * 判断协议是不是file://开头，如果是，那么uri.getPath()就是文件的真实路径
     *
     * @param root 根目录
     * @param path 文件路径 String
     * @return
     * @throws URISyntaxException
     */
    public static String getTruePath(File root, String path) {
        File file = new File(root, path);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(BgApplication.getContext(),
                    "com.luck.picture.lib.provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = BgApplication.getContext().getContentResolver()
                        .query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

}
