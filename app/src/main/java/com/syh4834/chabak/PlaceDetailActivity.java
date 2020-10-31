package com.syh4834.chabak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PlaceDetailActivity extends AppCompatActivity {
    PlaceImagePageAdapter placeImagePageAdapter;
    ViewPager vpPlaceImage;
    TextView tvImageNum;
    String pageNum;
    ConstraintLayout clToolbar;
    ScrollView svPlaceDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        vpPlaceImage = (ViewPager) findViewById(R.id.vp_place_image);
        svPlaceDetail = findViewById(R.id.sv_place_detail);
        tvImageNum = findViewById(R.id.tv_image_num);
        clToolbar = findViewById(R.id.cl_toolbar);
        placeImagePageAdapter = new PlaceImagePageAdapter(this);
        vpPlaceImage.setAdapter(placeImagePageAdapter);
        int totalPage = placeImagePageAdapter.getCount();

        pageNum = 1 + " / " + totalPage;
        tvImageNum.setText(pageNum);

        svPlaceDetail.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = svPlaceDetail.getScrollY();
                if(scrollY > 120) {
                    clToolbar.setVisibility(View.VISIBLE);
                } else {
                    clToolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        vpPlaceImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageNum = position+1 + " / " + totalPage;
                tvImageNum.setText(pageNum);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
