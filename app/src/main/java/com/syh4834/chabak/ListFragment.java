package com.syh4834.chabak;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceDetailData;
import com.syh4834.chabak.api.data.PlaceListData;
import com.syh4834.chabak.api.response.ResponsePlaceDetail;
import com.syh4834.chabak.api.response.ResponsePlaceLike;
import com.syh4834.chabak.api.response.ResponsePlaceList;
import com.syh4834.chabak.api.response.ResponsePlaceReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {
    private PlaceListData placeListData;
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<Integer> regionList = new ArrayList<>();
    private String order="star";
    private int regionTotalIdx=0;
    private int regionJjIdx=0;
    private int regionSlIdx=0;
    private int regionBsIdx=0;
    private int regionGgIdx=0;
    private int regionGwIdx=0;
    private int regionCcIdx=0;
    private int regionJlIdx=0;
    private int regionGsIdx=0;
    private int toiletIdx=0;
    private int cookingIdx=0;
    private int storeIdx=0;


    Button btnFilter;
    Button btnOption;
    Button btnFilterBack;
    Button btnFilterAdapter;
    Button btnOptionBack;
    Button btnOptionAdapter;

    RadioButton filterTopSort; // 1
    RadioButton filterDateSort; // 2
    RadioButton filterRateHighSort; // 3
    RadioButton filterBasicSort; //5

    CheckBox chbOptionTotal;
    CheckBox chbOptionJj;
    CheckBox chbOptionSl;
    CheckBox chbOptionBs;
    CheckBox chbOptionGg;
    CheckBox chbOptionGw;
    CheckBox chbOptionCc;
    CheckBox chbOptionJl;
    CheckBox chbOptionGs;

    CheckBox chbOptionToilet;
    CheckBox chbOptionStore;
    CheckBox chbOptionCooking;


    Animation translateLeftAnim;
    Animation translateRightAnim;
    ConstraintLayout slidingPanel;
    ConstraintLayout backgroundView;

    ConstraintLayout slidingdownPanel;
    Animation translateUpAnim;
    Animation translateDownAnim;

    boolean isFilterPageOpen=false;
    boolean isOptionPageOpen=false;

    // retrofit 객체 생성
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    String token;

    public ListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // 서버로부터 요청할 변수
        regionList.add(0); // 전국으로 초기화


        // sharedPreferences 값으로 사용자의 토큰을 얻어온다.
//        SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
//        token = sharedPreferences.getString("token", null);
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJpZCI6ImlkIiwibmlja25hbWUiOiIxMjMiLCJpYXQiOjE2MDQ5NzMxMDN9.80OjSRBho8176t0BgYu5tuEZ5pJGBh_tCjVn_Nsic_I"; // 임시 토큰

        //아래 변수들은 버튼들
        btnFilter = (Button) view.findViewById(R.id.btn_fillter);
        btnOption = (Button) view.findViewById(R.id.btn_option);
        btnFilterBack = (Button) view.findViewById(R.id.btn_close);
        btnFilterAdapter = (Button) view.findViewById(R.id.btn_filter_adapter);
        btnOptionBack = (Button) view.findViewById(R.id.btn_back);
        btnOptionAdapter = (Button) view.findViewById(R.id.btn_option_adapter);

        // 정렬버튼
        filterTopSort = (RadioButton) view.findViewById(R.id.rg_filter_top);
        filterDateSort = (RadioButton) view.findViewById(R.id.rg_filter_date);
        filterRateHighSort = (RadioButton) view.findViewById(R.id.rg_filter_highrate);
        filterBasicSort = (RadioButton) view.findViewById(R.id.rg_filter_basic);

        // 전국 8도버튼
        chbOptionTotal = (CheckBox) view.findViewById(R.id.chb_region_total);
        chbOptionJj = (CheckBox) view.findViewById(R.id.chb_region_jj);
        chbOptionSl = (CheckBox) view.findViewById(R.id.chb_region_sl);
        chbOptionBs = (CheckBox) view.findViewById(R.id.chb_region_bs);
        chbOptionGg = (CheckBox) view.findViewById(R.id.chb_region_gg);
        chbOptionGw = (CheckBox) view.findViewById(R.id.chb_region_gw);
        chbOptionCc = (CheckBox) view.findViewById(R.id.chb_region_cc);
        chbOptionJl = (CheckBox) view.findViewById(R.id.chb_region_jl);
        chbOptionGs = (CheckBox) view.findViewById(R.id.chb_region_gs);

        chbOptionToilet = (CheckBox) view.findViewById(R.id.chb_option_toilet);
        chbOptionStore = (CheckBox) view.findViewById(R.id.chb_region_mk);
        chbOptionCooking = (CheckBox) view.findViewById(R.id.chb_region_cook);

        DataInit(view);
        //아래는 필터 애니메이션
        translateLeftAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.left);
        translateRightAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.right);

        translateLeftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFilterPageOpen) {
                    slidingPanel.setVisibility(View.INVISIBLE);
                    isFilterPageOpen = false;
                } else {
                    isFilterPageOpen = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        translateRightAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFilterPageOpen) {
                    slidingPanel.setVisibility(View.INVISIBLE);
                    isFilterPageOpen = false;
                } else {

                    isFilterPageOpen = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        // 아래는 옵션 애니메이션
        translateUpAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.up);
        translateDownAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.down);

        translateUpAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isOptionPageOpen) {
                    slidingdownPanel.setVisibility(View.INVISIBLE);
                    isOptionPageOpen = false;
                } else {
                    isOptionPageOpen = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        translateDownAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isOptionPageOpen) {
                    isOptionPageOpen = false;
                } else {
                    slidingdownPanel.setVisibility(View.VISIBLE);
                    isOptionPageOpen = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // 버튼 클릭시 전환되는 화면
        slidingPanel = (ConstraintLayout) view.findViewById(R.id.activity_filter);
        backgroundView = (ConstraintLayout) view.findViewById(R.id.list_main);
        btnFilterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilterPageOpen) {
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                    backgroundView.setAlpha((float) 1.0);
                }
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFilterPageOpen) {
                    slidingPanel.startAnimation(translateLeftAnim);
                    slidingPanel.setVisibility(View.VISIBLE);
                    backgroundView.setAlpha((float) 0.2);
                }
            }
        });
        // 옵션 필터
        slidingdownPanel = (ConstraintLayout) view.findViewById(R.id.option_select);
        btnOptionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateUpAnim);
                }
            }
        });
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                }
            }
        });
        btnOptionAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateUpAnim);
                    if(optionRegion().equals("전국")){
                        optionCheck();
                        regionList.clear();
                        regionList.add(0);
                        //getPlaceListData(regionList); // 데이터 받기
                        btnOption.setText(optionRegion());
                        if(chbOptionJj.isChecked()){
                            chbOptionJj.setChecked(false);
                        }
                        if(chbOptionSl.isChecked()){
                            chbOptionSl.setChecked(false);
                        }
                        if(chbOptionBs.isChecked()){
                            chbOptionBs.setChecked(false);
                        }
                        if(chbOptionGg.isChecked()){
                            chbOptionGg.setChecked(false);
                        }
                        if(chbOptionGw.isChecked()){
                            chbOptionGw.setChecked(false);
                        }
                        if(chbOptionCc.isChecked()){
                            chbOptionCc.setChecked(false);
                        }
                        if(chbOptionJl.isChecked()){
                            chbOptionJl.setChecked(false);
                        }
                        if(chbOptionGs.isChecked()){
                            chbOptionGs.setChecked(false);
                        }

                        adapter.notifyDataSetChanged();
                    }
                    else{
                        if(btnOptionCounter()==1){
                            optionCheck();
                            //getPlaceListData(setRegionList()); //데이터 받기
                            btnOption.setText(optionRegion());
                            adapter.notifyDataSetChanged();
                        }
                        else if(btnOptionCounter()==0){
                            optionCheck();
                            //getPlaceListData(setRegionList());
                            btnOption.setText("여행지 선택");
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            optionCheck();
                            //getPlaceListData(setRegionList());
                            btnOption.setText(optionRegion()+" 외 "+String.valueOf(btnOptionCounter()-1));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        // 필터정렬
        filterTopSort.setOnClickListener(l -> {
            order = "like";
        });
        filterDateSort.setOnClickListener(l -> {
            order = "new";
        });
        filterRateHighSort.setOnClickListener(l -> {
            order = "star";
        });
        filterBasicSort.setOnClickListener(l -> {
            order = "review";
        });
        // 정렬버튼
        btnFilterAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilterPageOpen) {
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                    btnFilter.setVisibility(View.VISIBLE);
                    backgroundView.setAlpha((float) 1.0);
                    getPlaceListData(regionList);
                }
            }
        });
        return view;
    }
    private void optionCheck(){
        if(chbOptionToilet.isChecked()){
            toiletIdx=1;
        }
        else{
            toiletIdx=0;
        }
        if(chbOptionCooking.isChecked()){
            cookingIdx=1;
        }
        else{
            cookingIdx=0;
        }
        if(chbOptionStore.isChecked()){
            storeIdx=1;
        }
        else{
            storeIdx=0;
        }
    }
    private String optionRegion(){
        String region = "";
        if(chbOptionTotal.isChecked()){
            region = "전국";
        }
        else if(chbOptionJj.isChecked()){
            region = "제주도";
        }
        else if(chbOptionSl.isChecked()){
            region = "서울특별시";
        }
        else if(chbOptionBs.isChecked()){
            region = "부산광역시";
        }
        else if(chbOptionGg.isChecked()){
            region = "경기도";
        }
        else if(chbOptionGw.isChecked()){
            region = "강원도";
        }
        else if(chbOptionCc.isChecked()){
            region = "충청도";
        }
        else if(chbOptionJl.isChecked()){
            region = "전라도";
        }
        else if(chbOptionGs.isChecked()) {
            region = "경상도";
        }
        return region;
    }

    private ArrayList<Integer> setRegionList(){
        regionList.clear();
        if(chbOptionTotal.isChecked()){
            regionTotalIdx=0;
            regionList.add(regionTotalIdx);
        }
        if(chbOptionJj.isChecked()){
            regionJjIdx=8;
            regionList.add(regionJjIdx);
        }
        if(chbOptionSl.isChecked()){
            regionSlIdx=6;
            regionList.add(regionSlIdx);
        }
        if(chbOptionBs.isChecked()){
            regionBsIdx=7;
            regionList.add(regionBsIdx);
        }
        if(chbOptionGg.isChecked()){
            regionGgIdx=1;
            regionList.add(regionGgIdx);
        }
        if(chbOptionGw.isChecked()){
            regionGwIdx=2;
            regionList.add(regionGwIdx);
        }
        if(chbOptionCc.isChecked()){
            regionCcIdx=3;
            regionList.add(regionCcIdx);
        }
        if(chbOptionJl.isChecked()){
            regionJlIdx=4;
            regionList.add(regionJlIdx);
        }
        if(chbOptionGs.isChecked()) {
            regionGsIdx=5;
            regionList.add(regionGsIdx);
        }
        return regionList;
    }
    private int btnOptionCounter(){
        int cnt=0;
        if(chbOptionTotal.isChecked()){
            cnt++;
        }
        if(chbOptionJj.isChecked()){
            cnt++;
        }
        if(chbOptionSl.isChecked()){
            cnt++;
        }
        if(chbOptionBs.isChecked()){
            cnt++;
        }
        if(chbOptionGg.isChecked()){
            cnt++;
        }
        if(chbOptionGw.isChecked()){
            cnt++;
        }
        if(chbOptionCc.isChecked()){
            cnt++;
        }
        if(chbOptionJl.isChecked()){
            cnt++;
        }
        if(chbOptionGs.isChecked()) {
            cnt++;
        }
        return cnt;
    }
    // 리사이클러뷰 초기화
    private void DataInit(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerListAdapter();
        chabakService.getPlaceList(token,order,regionList,toiletIdx,cookingIdx,storeIdx).enqueue(new Callback<ResponsePlaceList>() {
            @Override
            public void onResponse(Call<ResponsePlaceList> call, Response<ResponsePlaceList> response) {
                if(response.body().getSuccess()) {
                    Log.e("성공!","성공!");
                    PlaceListData[] placeListData = response.body().getData();
                    getData(placeListData);
                }
            }
            @Override
            public void onFailure(Call<ResponsePlaceList> call, Throwable t) {
                Log.e("fail", "fail");
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    // 아래는 정렬할때 하는 것
    private  void getPlaceListData(ArrayList<Integer> regionList){
        adapter.listData.clear();
        chabakService.getPlaceList(token,order,regionList,toiletIdx,cookingIdx,storeIdx).enqueue(new Callback<ResponsePlaceList>() {
            @Override
            public void onResponse(Call<ResponsePlaceList> call, Response<ResponsePlaceList> response) {
                if(response.body().getSuccess()) {
                    PlaceListData[] placeListData = response.body().getData();
                    getData(placeListData);
                }
            }
            @Override
            public void onFailure(Call<ResponsePlaceList> call, Throwable t) {
                Log.e("fail", "fail");
            }
        });
    }

    private void getData(PlaceListData[] placeListData) {

        int rateImageView = R.drawable.star;
        for (int i = 0; i < placeListData.length; i++) {
            RecyclerListData data = new RecyclerListData();
            data.setTitle(placeListData[i].getPlaceTitle());
            data.setContent(placeListData[i].getPlaceAddress());
            data.setGetRateText(placeListData[i].getPlaceAvgStar());
            data.setRateImageView(rateImageView);
            data.setContentImageView(placeListData[i].getPlaceThumbnail());
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
}
