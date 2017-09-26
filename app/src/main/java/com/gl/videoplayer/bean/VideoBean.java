package com.gl.videoplayer.bean;

/**
 * Created by gl152 on 2017/9/25.
 */

public class VideoBean {
    private String description;
    private String videoUrl;

    public VideoBean(String description, String videoUrl) {
        this.description = description;
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
