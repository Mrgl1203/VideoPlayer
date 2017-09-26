package com.gl.videoplayer.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by gl152 on 2017/9/25.
 */

public class VideoPlayer extends VideoView {
    private onStatueListener onStatueListener;

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * onMeasure传入的widthMeasureSpec和heightMeasureSpec不是一般的尺寸数值，而是将模式和尺寸组合在一起的数值。我们需要通过int mode = MeasureSpec.getMode(widthMeasureSpec)得到模式，用int size = MeasureSpec.getSize(widthMeasureSpec)得到尺寸。
     * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, MeasureSpec.AT_MOST。
     * MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
     * MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
     * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int high = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, high);//video画面全屏
    }

    @Override
    public void start() {
        super.start();
        if (onStatueListener != null) {
            onStatueListener.onStart();
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (onStatueListener != null) {
            onStatueListener.onPause();
        }
    }

    public void setOnStatueListener(onStatueListener onStatueListener) {
        this.onStatueListener = onStatueListener;
    }

    //videoview没有专门的监听去获取video开始和停止状态，自定义个接口实现
    public interface onStatueListener {
        void onStart();

        void onPause();
    }


}
