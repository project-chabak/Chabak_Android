package com.syh4834.chabak;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.Category;
import com.syh4834.chabak.api.data.PlaceList;
import com.syh4834.chabak.api.response.ResponseHome;
import com.syh4834.chabak.home.BannerImagePageAdapter;
import com.syh4834.chabak.home.RecyclerLikeAdapter;
import com.syh4834.chabak.home.RecyclerRegionAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

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

    // retrofit 객체 생성
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    private Context context;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        // sharedPreferences 값으로 사용자의 토큰을 얻어온다.
        SharedPreferences sharedPreferences = context.getSharedPreferences("chabak", MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        bannerInit(); // 배너 초기화
        rvRegionInit(); // 지역별 여행지 초기화
        rvLikeInit(); // 추천 여행지 초기화
        DataInit(mView); // 데이터 파싱

        return mView;
    }


    private void  DataInit(View mView) {
        chabakService.getHome(token).enqueue(new Callback<ResponseHome>() {
            @Override
            public void onResponse(Call<ResponseHome> call, Response<ResponseHome> response) {

                ResponseHome test = response.body();
                if(response.body().getSuccess()) {
                    if(response.body().getData()==null){
                    }
                    else{
                        // 데이터 세팅
                        getData(response.body().getData().getPlaceCategoryData(), response.body().getData().getPlaceList());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseHome> call, Throwable t) {
                Log.e("fail", "fail");
            }
        });
    }



    // 데이터 세팅 함수
    private void getData(Category[] placeCategoryData, PlaceList[] placeList) {
        // 지역별 여행지 데이터 설정
        for(int i=0; i<placeCategoryData.length; i++) {
            recyclerRegionAdapter.addItem(placeCategoryData[i]);
        }

        // RecyclerRegionAdapter 새로고침
        recyclerRegionAdapter.notifyDataSetChanged();

        // 추천 여행지 데이터 설정
        for(int i=0; i<placeList.length; i++) {
            recyclerLikeAdapter.addItem(placeList[i]);
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
        recyclerRegionAdapter = new RecyclerRegionAdapter(context);

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