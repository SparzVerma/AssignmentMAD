package com.example.photoapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import com.bumptech.glide.Glide;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final List<Uri> imageUris;

    public ImageAdapter(Context context, List<Uri> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @Override
    public int getCount() {
        return imageUris.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        } else {
            view = convertView;
        }

        ImageView imageView = view.findViewById(R.id.imageView);
        Uri imageUri = imageUris.get(position);

        try {
            Glide.with(context).load(imageUri).into(imageView);
        } catch (Exception e) {
            imageView.setImageURI(imageUri); // Fallback in case Glide fails
        }

        return view;
    }
}