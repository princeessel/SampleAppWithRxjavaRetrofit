package com.example.rxjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

public class PhotosAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String> photoUrls;

    LayoutInflater inflater;

    @BindView(R.id.thumbnails)
    ImageView thumbnails;

    public PhotosAdapter(Context context, ArrayList<String> photoUrls) {
        this.context = context;
        this.photoUrls = photoUrls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return photoUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return photoUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            thumbnails = new ImageView(context);
            thumbnails.setLayoutParams(new GridView.LayoutParams(480, 480));
            thumbnails.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbnails.setPadding(8, 8, 8, 8);
        } else {
            thumbnails = (ImageView) convertView;
        }
        String url = (String) getItem(position);
        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop().into(thumbnails);
        return thumbnails;
    }
}
