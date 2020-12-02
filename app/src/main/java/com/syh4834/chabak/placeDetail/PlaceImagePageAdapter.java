package com.syh4834.chabak.placeDetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.syh4834.chabak.R;

public class PlaceImagePageAdapter extends PagerAdapter {
    private String[] placeImages;
    private LayoutInflater inflater;
    private Context context;

    public PlaceImagePageAdapter(Context context, String[] placeImages) {
        this.context = context;
        this.placeImages = placeImages;
    }

    @Override
    public int getCount() {
        return placeImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider_place_image, container, false);
        ImageView imgPlace = (ImageView)v.findViewById(R.id.img_place);

        Glide.with(container).load(placeImages[position]).into(imgPlace);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

    public String getThumbnail() {
        return placeImages[0];
    }
}
