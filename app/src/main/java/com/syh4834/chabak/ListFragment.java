package com.syh4834.chabak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceListData;
import com.syh4834.chabak.api.response.ResponsePlaceList;
import com.syh4834.chabak.placeDetail.PlaceDetailActivity;
import com.syh4834.chabak.review.recycler.RecyclerReviewUploadImgAdapter;

import java.util.ArrayList;

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
    public ArrayList<Integer> placeIdxList = new ArrayList<>();
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
    private int regionSelector=-1;


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

    ImageView regionTotalImage;
    ImageView regionjJImage;
    ImageView regionSlImage;
    ImageView regionBsImage;
    ImageView regionGgImage;
    ImageView regionGwImage;
    ImageView regionCcImage;
    ImageView regionJlImage;
    ImageView regionGsImage;

    Animation orderUpAnim;
    Animation orderDownAnim;

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
    int placeIdx;

    public ListFragment() {
        // Required empty public constructor
    }
    // 새로운 생성자
    public ListFragment(int placeCategoryIdx){
        this.regionSelector=placeCategoryIdx;
        Log.e("인덱스",String.valueOf(this.regionSelector));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // 서버로부터 요청할 변수
        regionList.clear();
        regionList.add(0); // 전국으로 초기화

        // sharedPreferences 값으로 사용자의 토큰을 얻어온다.
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("chabak", getContext().MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
        //Log.e("토큰",token);

        //아래 변수들은 버튼들
        btnFilter = (Button) view.findViewById(R.id.btn_fillter);
        btnOption = (Button) view.findViewById(R.id.btn_option);
        btnFilterAdapter = (Button) view.findViewById(R.id.btn_filter_adapter);
//        btnOptionBack = (Button) view.findViewById(R.id.btn_back);
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

        regionTotalImage = (ImageView) view.findViewById(R.id.region_total_image);
        regionjJImage = (ImageView) view.findViewById(R.id.region_jj_image);
        regionSlImage = (ImageView) view.findViewById(R.id.region_sl_image);
        regionBsImage = (ImageView) view.findViewById(R.id.region_bs_image);
        regionGgImage = (ImageView) view.findViewById(R.id.region_gg_image);
        regionGwImage = (ImageView) view.findViewById(R.id.region_gw_image);
        regionCcImage = (ImageView) view.findViewById(R.id.region_cc_image);
        regionJlImage = (ImageView) view.findViewById(R.id.region_jl_image);
        regionGsImage = (ImageView) view.findViewById(R.id.region_gs_image);

        chbOptionTotal.setChecked(true);
        filterRateHighSort.setChecked(true);
        // 홈화면에서 받은 정보
        if(regionSelector==-1){
            DataInit(view);
        }
        else{
            regionList.clear();
            regionList.add(regionSelector);
            checkRegion(regionSelector);
            btnOption.setText(optionRegion());
            DataInit(view);
            adapter.notifyDataSetChanged();
        }
        // 클릭시 전환되는 화면
        adapter.setOnItemClickListener(new RecyclerReviewUploadImgAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                int pos = placeIdxList.get(position);
                //Log.e("포지션",String.valueOf(pos));
                Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
                intent.putExtra("PlaceIdx", pos); // position부분을 수정해야 함
                startActivityForResult(intent,0);
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

        orderUpAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_up);
        orderDownAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_down);

        orderUpAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFilterPageOpen) {
                    isFilterPageOpen = false;
                } else {
                    slidingPanel.setVisibility(View.VISIBLE);
                    isFilterPageOpen = true;
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        orderDownAnim.setAnimationListener(new Animation.AnimationListener() {
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


        // 버튼 클릭시 전환되는 화면
        slidingPanel = (ConstraintLayout) view.findViewById(R.id.activity_filter);
        backgroundView = (ConstraintLayout) view.findViewById(R.id.list_main);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFilterPageOpen) {
                    slidingPanel.startAnimation(orderUpAnim);
                    slidingPanel.setVisibility(View.VISIBLE);
                    //backgroundView.setAlpha((float) 0.2);
                }
                if (isFilterPageOpen) {
                    slidingPanel.startAnimation(orderDownAnim);
                    slidingPanel.setVisibility(View.GONE);
                    //backgroundView.setAlpha((float) 0.2);
                }
            }
        });
        // 옵션 필터
        slidingdownPanel = (ConstraintLayout) view.findViewById(R.id.option_select);

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    btnOption.setText("여행지 △");
                } else {
                    slidingdownPanel.startAnimation(translateUpAnim);
                    btnOption.setText("여행지 ▽");
                }
            }
        });
        btnOptionAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateUpAnim);

                    if(optionRegion().equals("전국")||optionRegion().equals("")){
                        optionCheck();
                        regionList.clear();
                        regionList.add(0);
                        getPlaceListData(regionList); // 데이터 받기
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
                            getPlaceListData(setRegionList()); //데이터 받기
                            btnOption.setText(optionRegion());
                            adapter.notifyDataSetChanged();
                        }
                        else if(btnOptionCounter()==0){
                            optionCheck();
                            getPlaceListData(setRegionList());
                            btnOption.setText("여행지 선택");
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            optionCheck();
                            getPlaceListData(setRegionList());
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
                    slidingPanel.startAnimation(orderDownAnim);
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
    private void checkRegion(int region){
        chbOptionTotal.setChecked(false);
        if(region==1){
            chbOptionJj.setChecked(true);
        }
        else if(region==2){
            chbOptionSl.setChecked(true);
        }
        else if(region==3){
            chbOptionBs.setChecked(true);
        }
        else if(region==4){
            chbOptionGg.setChecked(true);
        }
        else if(region==5){
            chbOptionGw.setChecked(true);
        }
        else if(region==6){
            chbOptionCc.setChecked(true);
        }
        else if(region==7){
            chbOptionJl.setChecked(true);
        }
        else if(region==8){
            chbOptionGs.setChecked(true);
        }
    }
    private String optionRegion(){
        String region = "";
        int cnt=0;
        if(chbOptionTotal.isChecked()){
            region = "전국";
            cnt++;
        }
        else if(chbOptionJj.isChecked()){
            region = "제주도";
            cnt++;
        }
        else if(chbOptionSl.isChecked()){
            region = "서울특별시";
            cnt++;
        }
        else if(chbOptionBs.isChecked()){
            region = "부산광역시";
            cnt++;
        }
        else if(chbOptionGg.isChecked()){
            region = "경기도";
            cnt++;
        }
        else if(chbOptionGw.isChecked()){
            region = "강원도";
            cnt++;
        }
        else if(chbOptionCc.isChecked()){
            region = "충청도";
            cnt++;
        }
        else if(chbOptionJl.isChecked()){
            region = "전라도";
            cnt++;
        }
        else if(chbOptionGs.isChecked()) {
            region = "경상도";
            cnt++;
        }
        if(cnt==0){
            region = "전국";
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
            regionJjIdx=1;
            regionList.add(regionJjIdx);
        }
        if(chbOptionSl.isChecked()){
            regionSlIdx=2;
            regionList.add(regionSlIdx);
        }
        if(chbOptionBs.isChecked()){
            regionBsIdx=3;
            regionList.add(regionBsIdx);
        }
        if(chbOptionGg.isChecked()){
            regionGgIdx=4;
            regionList.add(regionGgIdx);
        }
        if(chbOptionGw.isChecked()){
            regionGwIdx=5;
            regionList.add(regionGwIdx);
        }
        if(chbOptionCc.isChecked()){
            regionCcIdx=6;
            regionList.add(regionCcIdx);
        }
        if(chbOptionJl.isChecked()){
            regionJlIdx=7;
            regionList.add(regionJlIdx);
        }
        if(chbOptionGs.isChecked()) {
            regionGsIdx=8;
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
        adapter.setToken(token);
        chabakService.getPlaceList(token,order,regionList,toiletIdx,cookingIdx,storeIdx).enqueue(new Callback<ResponsePlaceList>() {
            @Override
            public void onResponse(Call<ResponsePlaceList> call, Response<ResponsePlaceList> response) {
                if(response.body().getSuccess()) {
                    Log.e("성공!","성공!");
                    placeListData = response.body().getData();
                    setCategoryImage(placeListData,view);
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

    private void setCategoryImage(PlaceListData placeListData, View view){
        Glide.with(view).load(placeListData.getPlaceCategoryData()[0].getPlaceCategoryImg()).into(regionjJImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[1].getPlaceCategoryImg()).into(regionSlImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[2].getPlaceCategoryImg()).into(regionBsImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[3].getPlaceCategoryImg()).into(regionGgImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[4].getPlaceCategoryImg()).into(regionGwImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[5].getPlaceCategoryImg()).into(regionCcImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[6].getPlaceCategoryImg()).into(regionJlImage);
        Glide.with(view).load(placeListData.getPlaceCategoryData()[7].getPlaceCategoryImg()).into(regionGsImage);
    }
    // 아래는 정렬할때 하는 것
    private  void getPlaceListData(ArrayList<Integer> regionList){
        adapter.listData.clear();
        chabakService.getPlaceList(token,order,regionList,toiletIdx,cookingIdx,storeIdx).enqueue(new Callback<ResponsePlaceList>() {
            @Override
            public void onResponse(Call<ResponsePlaceList> call, Response<ResponsePlaceList> response) {
                if(response.body().getSuccess()) {
                    Log.e("인덱스!", "성공!");
                    placeListData = response.body().getData();
                    getData(placeListData);
                }
            }
            @Override
            public void onFailure(Call<ResponsePlaceList> call, Throwable t) {
                Log.e("fail", "fail");
            }
        });
    }

    private void getData(PlaceListData placeListData) {
        placeIdxList.clear();

        int rateImageView = R.drawable.star;
        for (int i = 0; i < placeListData.getPlaceList().length; i++) {
            RecyclerListData data = new RecyclerListData();
            if(placeListData.getPlaceList()[i].getUserLike() == true) {
                data.setUserLike(true);
            }
            else{
                data.setUserLike(false);
            }
            data.setTitle(placeListData.getPlaceList()[i].getPlaceTitle());
            data.setContent(placeListData.getPlaceList()[i].getPlaceAddress());
            data.setGetRateText(placeListData.getPlaceList()[i].getPlaceAvgStar());
            data.setRateImageView(rateImageView);
            data.setContentImageView(placeListData.getPlaceList()[i].getPlaceThumbnail());
            data.setPlaceIdx(placeListData.getPlaceList()[i].getPlaceIdx());
            placeIdxList.add(placeListData.getPlaceList()[i].getPlaceIdx());
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if (resultCode == 0) {
                getPlaceListData(regionList);
            }
        }
    }

}
