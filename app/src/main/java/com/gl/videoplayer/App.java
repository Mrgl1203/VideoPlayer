package com.gl.videoplayer;

import android.app.Application;

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

/**
 * Created by gl152 on 2017/9/25.
 */

public class App extends Application {
    private HttpProxyCacheServer proxy;
    static File dataDir;
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        dataDir = this.getExternalFilesDir("VideoCache");// /storage/emulated/0/Android/data/com.gl.videoplayer/files/VideoCache


    }

    public HttpProxyCacheServer getProxy() {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }


    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(dataDir)//指定缓存路径
                .build();

    }


}
