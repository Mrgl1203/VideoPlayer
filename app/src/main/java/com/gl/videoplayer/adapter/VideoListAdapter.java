package com.gl.videoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gl.videoplayer.R;
import com.gl.videoplayer.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gl152 on 2017/9/25.
 */

public class VideoListAdapter extends BaseAdapter {
    private List<VideoBean> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public VideoListAdapter(Context context) {
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    public void addData(List<VideoBean> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public VideoBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.item_video,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.tvVideo.setText(mData.get(position).getDescription());
        return convertView;
    }

    class ViewHolder{
        TextView tvVideo;

        public ViewHolder(View view) {
            tvVideo= (TextView) view.findViewById(R.id.tvVideo);
        }
    }
}
