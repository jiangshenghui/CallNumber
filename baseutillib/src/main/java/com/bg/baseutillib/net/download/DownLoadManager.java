package com.bg.baseutillib.net.download;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by linboliu on 2016/11/18.
 */

public class DownLoadManager {
    private static final String TAG = "DownLoadManager";

    public DownLoadManager(){

    }

    /**
     *
     * @param body
     * @param path 文件下载的sd卡路径
     * @return
     */
    public boolean writeResponseBodyToDisk(ResponseBody body, String path) {
        Log.d(TAG, "contentType:>>>>" + body.contentType().toString());
        // 其他同上 自己判断加入
        Log.d(TAG, "path:>>>>" + path);
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);

                    long vb = fileSize / fileSizeDownloaded;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
