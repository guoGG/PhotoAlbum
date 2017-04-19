package com.example.gjw.albumlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gjw.albumlibrary.album.OnRecyclerViewItemClickListener;
import com.example.gjw.albumlibrary.entity.Images;
import com.example.gjw.albumlibrary.util.GlideUtil;
import com.example.gjw.photoalbum.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by guojiawei on 2017/3/7.
 */

public class PhotoAdapter extends RecyclerView.Adapter {
    private final int CAMERA = 1;
    private final int IMAGE = 2;
    private List<Images> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<Images> checkeds = new ArrayList<>();

    public PhotoAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public List<Images> getmDatas() {
        return mDatas;
    }

    public void addItems(List<Images> datas) {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
        mDatas.addAll(datas);
        notifyItemRangeInserted(1, getmDatas().size() + 1);
    }

    public List<Images> getCheckedDatas() {
        checkeds.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).isChecked()) {
                checkeds.add(mDatas.get(i));
            }
        }
        return checkeds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int w = getWindowWidth();
        View itemCamera = inflater.inflate(R.layout.item_photo_album_camera, parent, false);
        View itemImage = inflater.inflate(R.layout.item_photo_album_img, parent, false);
        itemCamera.setLayoutParams(new LinearLayout.LayoutParams(w, w));
        itemImage.setLayoutParams(new LinearLayout.LayoutParams(w, w));
        itemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        if (viewType == CAMERA) {
            return new CameraViewHolder(itemCamera);
        }
        if (viewType == IMAGE) {
            return new ImageViewHolder(itemImage);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CameraViewHolder) {
            holder.itemView.setTag(position);
            ViewGroup.LayoutParams params = ((CameraViewHolder) holder).photoAlbumItemCameraBtn.getLayoutParams();
            params.width = getWindowWidth();
            params.height = getWindowWidth();
            ((CameraViewHolder) holder).photoAlbumItemCameraBtn.setLayoutParams(params);
        }
        if (holder instanceof ImageViewHolder) {
            final int index = position - 1;
            holder.itemView.setTag(position);
            if (mDatas.get(index).isChecked()) {
                ((ImageViewHolder) holder).photoAlbumItemImgCheck.setChecked(true);
            } else {
                ((ImageViewHolder) holder).photoAlbumItemImgCheck.setChecked(false);
            }
            ((ImageViewHolder) holder).photoAlbumItemImgCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((ImageViewHolder) holder).photoAlbumItemImgCheck.isChecked()) {
                        mDatas.get(index).setChecked(true);
                    } else {
                        mDatas.get(index).setChecked(false);
                    }
                }
            });
            GlideUtil.loadUrlImage(mContext, mDatas.get(index).getPath(), ((ImageViewHolder) holder).photoAlbumItemImg);

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CAMERA;
        } else {
            return IMAGE;
        }
    }

    private int getWindowWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width / 3;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    static class CameraViewHolder extends RecyclerView.ViewHolder {
        ImageView photoAlbumItemCameraBtn;

        CameraViewHolder(View view) {
            super(view);
            photoAlbumItemCameraBtn = (ImageView) view.findViewById(R.id.photo_album_item_camera_btn);
        }
    }

    final static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView photoAlbumItemImg;
        AppCompatCheckBox photoAlbumItemImgCheck;

        ImageViewHolder(View view) {
            super(view);
            photoAlbumItemImg = (ImageView) view.findViewById(R.id.photo_album_item_img);
            photoAlbumItemImgCheck = (AppCompatCheckBox) view.findViewById(R.id.photo_album_item_img_check);
        }
    }
}
