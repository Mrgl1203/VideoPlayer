# VideoPlayer
### videoview播放视频，边放变缓存
* 使用了AndroidVideoCache框架进行缓存策略：该框架十分解耦，轻松实现自定义<br>
使用步骤在application中初始化
```
 public HttpProxyCacheServer getProxy() {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }


    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(dataDir)//指定缓存路径
                .build();

    }
```
播放使用HttpProxyCacheServer转换原始url
```
        //实现缓存加载
        HttpProxyCacheServer proxy = getApp().getProxy();
        String proxyUrl = proxy.getProxyUrl(videoUrl);
        videoPlayer.setVideoPath(proxyUrl);
```

* 视频播放使用原生videoview控件，定制seekbar替换系统mediacontroller，详细请看ShowVideoActivity中的源码
