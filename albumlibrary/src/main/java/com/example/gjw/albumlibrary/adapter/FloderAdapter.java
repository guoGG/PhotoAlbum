package com.example.gjw.albumlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gjw.albumlibrary.album.OnRecyclerViewItemClickListener;
import com.example.gjw.albumlibrary.entity.Images;
import com.example.gjw.albumlibrary.util.GlideUtil;
import com.example.gjw.photoalbum.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by guojiawei on 2017/4/14.
 */

public class FloderAdapter extends RecyclerView.Adapter {
    private Map<String, List<Images>> mDatas = new WeakHashMap<>();
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private List<String> floderNames = new ArrayList<>();
    private Context mContext;

    public FloderAdapter(Context context) {
        this.mContext = context;
    }

    public void addItems(Map<String, List<Images>> datas) {
        this.mDatas.clear();
        this.mDatas.putAll(datas);
        floderNames.addAll(mDatas.keySet());
        Collections.reverse(floderNames);
        notifyDataSetChanged();
    }

    public List<String> getFloderNames() {
        return floderNames;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_album_floder, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            h.itemView.setTag(position);
            String floderName = floderNames.get(position);
            h.floderName.setText(floderName);
            GlideUtil.loadUrlImage(mContext, mDatas.get(floderName).get(0).getPath(), h.previewImg);
        }
    }

    @Override
    public int getItemCount() {
        return floderNames.size();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView previewImg;
        TextView floderName;

        public ViewHolder(View itemView) {
            super(itemView);
            previewImg = (ImageView) itemView.findViewById(R.id.item_floder_preview_img);
            floderName = (TextView) itemView.findViewById(R.id.item_floder_floder_name);
        }
    }
}
