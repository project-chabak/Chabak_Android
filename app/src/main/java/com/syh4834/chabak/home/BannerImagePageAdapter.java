package com.syh4834.chabak.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.syh4834.chabak.R;

public class BannerImagePageAdapter extends PagerAdapter {
    private int [] bannerImages = {R.drawable.place_image_01, R.drawable.place_image_01};
    private LayoutInflater inflater;
    private Context context;

    public BannerImagePageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return bannerImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider_banner_image, container, false);
        ImageView imgBanner = (ImageView)v.findViewById(R.id.img_banner);

        imgBanner.setImageResource(bannerImages[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}