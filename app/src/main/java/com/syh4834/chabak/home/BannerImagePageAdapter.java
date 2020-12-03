package com.syh4834.chabak.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.syh4834.chabak.R;

public class BannerImagePageAdapter extends PagerAdapter {
    private int [] bannerImages = {R.drawable.home_banner_image_01, R.drawable.home_banner_image_02, R.drawable.home_banner_image_03};
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

        // 배너 슬라이드 클릭 이벤트 리스너
        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BannerDetailActivity.class);
                intent.putExtra("position", position);
                view.getContext().startActivity(intent);
            }
        });

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}