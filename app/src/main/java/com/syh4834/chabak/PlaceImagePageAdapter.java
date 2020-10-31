package com.syh4834.chabak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class PlaceImagePageAdapter extends PagerAdapter {
    private int [] placeImages = {R.drawable.place_image_01, R.drawable.place_image_02};
    private LayoutInflater inflater;
    private Context context;

    public PlaceImagePageAdapter(Context context) {
        this.context = context;
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
        TextView tvImageNum = (TextView)v.findViewById(R.id.tv_image_num);

        imgPlace.setImageResource(placeImages[position]);

//        String text = (position + 1) + " / n";
//
//        tvImageNum.setText(text);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
