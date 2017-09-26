package com.gl.videoplayer.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created by gl152 on 2017/9/25.
 */

public class Util {

    public static File getRootDir() {//获取根目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//检查sd卡是否挂载
            Log.e(TAG, "getRootDir: " + Environment.getExternalStorageDirectory());//getRootDir: /storage/emulated/0
            return Environment.getExternalStorageDirectory();//
        } else {
            Log.e(TAG, "getRootDir: null");
            return null;
        }
    }
}
