package com.syh4834.chabak;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.syh4834.chabak.home.BannerImagePageAdapter;
import com.syh4834.chabak.home.RecyclerLikeAdapter;
import com.syh4834.chabak.home.RecyclerRegionAdapter;
import com.syh4834.chabak.home.RecyclerRegionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    // HomeFragment inflater
    View mView;

    // 배너 ViewPager
    private ViewPager vpBannerImage;
    // 배너 ViewPager custom Adapter
    private BannerImagePageAdapter bannerImagePageAdapter;
    // 배너 현재 위치 표현 TextView
    private TextView tvImageNum;

    // 지역별 여행지 RecyclerView
    private RecyclerView rvRegion;
    // 지역별 여행지 custom Adapter
    private RecyclerRegionAdapter recyclerRegionAdapter;

    // 추천 여행지 RecyclerView
    private RecyclerView rvLike;
    // 추천 여행지 custom Adapter
    private RecyclerLikeAdapter recyclerLikeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        bannerInit(); // 배너 초기화
        rvRegionInit(); // 지역별 여행지 초기화
        rvLikeInit(); // 추천 여행지 초기화
        getData(); // 데이터 세팅

        return mView;
    }

    // 데이터 세팅 함수
    private void getData() {
        // 지역별 여행지 데이터 설정
        List<String> listWriter = Arrays.asList("경기", "강원", "충북", "충남", "전북", "전남", "인천", "대전", "세종", "부산", "울산", "경북", "경남", "대구", "광주", "제주");
        for(int i = 0; i < listWriter.size(); i++) {
            RecyclerRegionData recyclerReviewData = new RecyclerRegionData();
            recyclerReviewData.setRegionName(listWriter.get(i));

            recyclerRegionAdapter.addItem(recyclerReviewData);
        }

        // RecyclerRegionAdapter 새로고침
        recyclerRegionAdapter.notifyDataSetChanged();


        // 추천 여행지 데이터 설정
        List<String> listLike =  new ArrayList<>();
        listLike.add("제목1");
        listLike.add("제목2");
        listLike.add("제목3");
        listLike.add("제목4");
        listLike.add("제목5");
        listLike.add("제목6");
        listLike.add("제목7");
        listLike.add("제목8");
        listLike.add("제목9");
        listLike.add("제목10");

        for(int i = 0; i < listLike.size(); i++) {
            recyclerLikeAdapter.addItem(listLike.get(i));
        }

        // RecyclerRegionAdapter 새로고침
        recyclerLikeAdapter.notifyDataSetChanged();
    }

    // 배너 초기화 함수
    private void bannerInit() {
        // 객체 초기화
        vpBannerImage = (ViewPager) mView.findViewById(R.id.vp_banner_image);
        bannerImagePageAdapter = new BannerImagePageAdapter(getContext());
        tvImageNum = mView.findViewById(R.id.tv_image_num);

        // Adapter 설정
        vpBannerImage.setAdapter(bannerImagePageAdapter);

        // 배너 현재 위치 초기화
        tvImageNum.setText(1 + " / " + bannerImagePageAdapter.getCount());

        // 배너 슬라이드 이벤트 리스너
        vpBannerImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 배너 현재 위치 TextView 표현
                tvImageNum.setText(position+1 + " / " + bannerImagePageAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // 지역별 여행지 초기화 함수
    private void rvRegionInit() {
        // 객체 초기화
        rvRegion = mView.findViewById(R.id.rv_region);
        recyclerRegionAdapter = new RecyclerRegionAdapter();

        // 뷰배치 객체 생성 및 초기화
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvRegion.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvRegion.setLayoutManager(linearLayoutManager);

        // Adapter 설정
        rvRegion.setAdapter(recyclerRegionAdapter);
    }

    // 추천 여행지 초기화 함수
    private void rvLikeInit() {
        // 객체 초기화
        rvLike = mView.findViewById(R.id.rv_like);
        recyclerLikeAdapter = new RecyclerLikeAdapter();

        // 뷰배치 객체 생성 및 초기화
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rvLike.setLayoutManager(linearLayoutManager);

        // Adapter 설정
        rvLike.setAdapter(recyclerLikeAdapter);
    }
}
