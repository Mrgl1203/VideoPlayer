package com.gl.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gl.videoplayer.adapter.VideoListAdapter;
import com.gl.videoplayer.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    List<VideoBean> videoBeanList = new ArrayList<>();
    VideoListAdapter adapter;

    String[] urls = new String[]{"http://dg6m0ddeht37z.cloudfront.net/cake_2e65262c-02be-4fbf-8f21-e822f31a0ed6.mp4"
            , "http://dg6m0ddeht37z.cloudfront.net/cake_dfbb2e82-77fc-4009-9788-56c54c773e7e.mp4"
            , "http://dg6m0ddeht37z.cloudfront.net/cake_926a1514-12eb-4b3d-b7e1-1eac82b372e5.mp4"
            , "http://dg6m0ddeht37z.cloudfront.net/cake_12a7691a-6f51-4ad8-be81-eeb837c0533e.mp4"
            , "http://dg6m0ddeht37z.cloudfront.net/cake_57a8605a-62a2-475b-8079-4150c138b006.mp4"};

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i < urls.length; i++) {
            VideoBean videoBean = new VideoBean("视频链接" + i, urls[i]);
            videoBeanList.add(videoBean);
        }
        adapter = new VideoListAdapter(this);
        adapter.addData(videoBeanList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String videoUrl = adapter.getItem(position).getVideoUrl();
                Intent intent = new Intent(MainActivity.this, ShowVideoActivity.class);
                intent.putExtra("videoUrl", videoUrl);
                startActivity(intent);
            }
        });
    }
}
