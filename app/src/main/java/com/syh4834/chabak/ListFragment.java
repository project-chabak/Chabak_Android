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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int sortSelect=-1;
    private int optionSelect=-1;
    private int btnOptionControl=-1;

    Button btnFilter;
    Button btnOption;
    Button btnFilterBack;
    Button btnFilterAdapter;
    Button btnOptionBack;
    Button btnOptionAdapter;

    RadioButton filterTopSort; // 1
    RadioButton filterDateSort; // 2
    RadioButton filterRateHighSort; // 3
    RadioButton filterRateLowSort; // 4
    RadioButton filterBasicSort; //5

    RadioButton rbOptionTotal;
    RadioButton rbOptionJj;
    RadioButton rbOptionSl;
    RadioButton rbOptionBs;
    RadioButton rbOptionGg;
    RadioButton rbOptionGw;
    RadioButton rbOptionCc;
    RadioButton rbOptionJl;
    RadioButton rbOptionGs;

    Button btnOptionTotal; // 1
    Button btnOptionJj; // 2
    Button btnOptionSl; // 3
    Button btnOptionBs; // 4
    Button btnOptionGg; // 5
    Button btnOptionGw; // 6
    Button btnOptionCc; // 7
    Button btnOptionJl; // 8
    Button btnOptionGs; // 9

    Animation translateLeftAnim;
    Animation translateRightAnim;
    ConstraintLayout slidingPanel;
    ConstraintLayout backgroundView;

    ConstraintLayout slidingdownPanel;
    Animation translateUpAnim;
    Animation translateDownAnim;

    boolean isFilterPageOpen=false;
    boolean isOptionPageOpen=false;

    public ListFragment() {
        // Required empty public constructor
    }

    void setSortSelect(int sortSelect){
        this.sortSelect=sortSelect;
    }

    void setOptionSelect(int optionSelect){
        this.optionSelect=optionSelect;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
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
        filterRateLowSort = (RadioButton) view.findViewById(R.id.rg_filter_lowrate);
        filterBasicSort = (RadioButton) view.findViewById(R.id.rg_filter_basic);

        //전국 8도 라디오버튼
        rbOptionTotal = (RadioButton) view.findViewById(R.id.rg_region_total);
        rbOptionJj = (RadioButton) view.findViewById(R.id.rg_region_jj);
        rbOptionSl = (RadioButton) view.findViewById(R.id.rg_region_sl);
        rbOptionBs = (RadioButton) view.findViewById(R.id.rg_region_bs);
        rbOptionGg = (RadioButton) view.findViewById(R.id.rg_region_gg);
        rbOptionGw = (RadioButton) view.findViewById(R.id.rg_region_gw);
        rbOptionCc = (RadioButton) view.findViewById(R.id.rg_region_cc);
        rbOptionJl = (RadioButton) view.findViewById(R.id.rg_region_jl);
        rbOptionGs = (RadioButton) view.findViewById(R.id.rg_region_gs);

        // 전국 8도버튼
        btnOptionTotal = (Button) view.findViewById(R.id.btn_option_total);
        btnOptionJj = (Button) view.findViewById(R.id.btn_option_jj);
        btnOptionSl = (Button) view.findViewById(R.id.btn_option_sl);
        btnOptionBs = (Button) view.findViewById(R.id.btn_option_bs);
        btnOptionGg = (Button) view.findViewById(R.id.btn_option_gg);
        btnOptionGw = (Button) view.findViewById(R.id.btn_option_gw);
        btnOptionCc = (Button) view.findViewById(R.id.btn_option_cc);
        btnOptionJl = (Button) view.findViewById(R.id.btn_option_jl);
        btnOptionGs = (Button) view.findViewById(R.id.btn_option_gs);

        //이 중앙 코드는 리사이클러뷰 적용 코드
        DataInit(view);

        //아래는 필터 애니메이션
        translateLeftAnim =AnimationUtils.loadAnimation(getActivity(),R.anim.left);
        translateRightAnim =AnimationUtils.loadAnimation(getActivity(),R.anim.right);

        translateLeftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(isFilterPageOpen){
                    slidingPanel.setVisibility(View.INVISIBLE);
                    isFilterPageOpen=false;
                }else{
                    isFilterPageOpen=true;
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
                if(isFilterPageOpen){
                    slidingPanel.setVisibility(View.INVISIBLE);
                    isFilterPageOpen=false;
                }else{

                    isFilterPageOpen=true;
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        // 아래는 옵션 애니메이션
        translateUpAnim =AnimationUtils.loadAnimation(getActivity(),R.anim.up);
        translateDownAnim =AnimationUtils.loadAnimation(getActivity(),R.anim.down);

        translateUpAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(isOptionPageOpen){
                    slidingdownPanel.setVisibility(View.INVISIBLE);
                    OptionButtonCheck();
                    isOptionPageOpen=false;
                }else{
                    isOptionPageOpen=true;
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
                if(isOptionPageOpen){
                    isOptionPageOpen=false;
                }else{
                    slidingdownPanel.setVisibility(View.VISIBLE);
                    isOptionPageOpen=true;
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // 버튼 클릭시 전환되는 화면
        slidingPanel=(ConstraintLayout)view.findViewById(R.id.activity_filter);
        backgroundView=(ConstraintLayout)view.findViewById(R.id.list_main);
        btnFilterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFilterPageOpen){
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                    backgroundView.setAlpha((float)1.0);
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
        slidingdownPanel=(ConstraintLayout)view.findViewById(R.id.option_select);
        btnOptionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOptionPageOpen){
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
                    setOptionSelect(optionSelect);
                    slidingdownPanel.startAnimation(translateUpAnim);
                    if(optionSelect==1){
                        btnOptionTotal.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 1;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.totalData.size();i++){
                            adapter.addItem(adapter.totalData.get(i));
                        }
                        Comparator<RecyclerListData> totalSelect = new Comparator<RecyclerListData>() {
                            @Override
                            public int compare(RecyclerListData item1, RecyclerListData item2) {
                                if(item1.getReviewCount() > item2.getReviewCount()){
                                    return -1;
                                }
                                else if(item1.getReviewCount() < item2.getReviewCount()){
                                    return 1;
                                }
                                else{
                                    return 0;
                                }
                            }
                        };
                        Collections.sort(adapter.listData, totalSelect) ;
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==2){
                        btnOptionJj.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 2;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.jjListData.size();i++){
                            adapter.addItem(adapter.jjListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==3){
                        btnOptionSl.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl =3;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.slListData.size();i++){
                            adapter.addItem(adapter.slListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==4){
                        btnOptionBs.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 4;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.bsListData.size();i++){
                            adapter.addItem(adapter.bsListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==5){
                        btnOptionGg.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 5;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.ggListData.size();i++){
                            adapter.addItem(adapter.ggListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==6){
                        btnOptionGw.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 6;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.gwListData.size();i++){
                            adapter.addItem(adapter.gwListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==7){
                        btnOptionCc.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 7;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.ccListData.size();i++){
                            adapter.addItem(adapter.ccListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==8){
                        btnOptionJl.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 8;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.jlListData.size();i++){
                            adapter.addItem(adapter.jlListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(optionSelect==9){
                        btnOptionGs.setVisibility(View.VISIBLE);
                        btnOption.setVisibility(View.INVISIBLE);
                        btnOptionControl = 9;
                        adapter.listData.clear();
                        for(int i=0;i<adapter.gsListData.size();i++){
                            adapter.addItem(adapter.gsListData.get(i));
                        }
                        adapter.notifyDataSetChanged() ;
                    }
                }
            }
        });
        // 옵션선택
        rbOptionTotal.setOnClickListener(l -> {
            optionSelect=1;
        });
        rbOptionJj.setOnClickListener(l -> {
            optionSelect=2;
        });
        rbOptionSl.setOnClickListener(l -> {
            optionSelect=3;
        });
        rbOptionBs.setOnClickListener(l -> {
            optionSelect=4;
        });
        rbOptionGg.setOnClickListener(l -> {
            optionSelect=5;
        });
        rbOptionGw.setOnClickListener(l -> {
            optionSelect=6;
        });
        rbOptionCc.setOnClickListener(l -> {
            optionSelect=7;
        });
        rbOptionJl.setOnClickListener(l -> {
            optionSelect=8;
        });
        rbOptionGs.setOnClickListener(l -> {
            optionSelect=9;
        });
        // 필터정렬
        filterTopSort.setOnClickListener(l -> {
            sortSelect=1;
        });
        filterDateSort.setOnClickListener(l -> {
            sortSelect=2;
        });
        filterRateHighSort.setOnClickListener(l -> {
            sortSelect=3;
        });
        filterRateLowSort.setOnClickListener(l -> {
            sortSelect=4;
        });
        filterBasicSort.setOnClickListener(l -> {
            sortSelect=5;
        });
        // 정렬버튼
        btnFilterAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilterPageOpen) {
                    setSortSelect(sortSelect);
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                    btnFilter.setVisibility(View.VISIBLE);
                    backgroundView.setAlpha((float) 1.0);
                    if(sortSelect==5){
                        Comparator<RecyclerListData> basicSort = new Comparator<RecyclerListData>() {
                            @Override
                            public int compare(RecyclerListData item1, RecyclerListData item2) {
                                if(item1.getReviewCount() > item2.getReviewCount()){
                                    return -1;
                                }
                                else if(item1.getReviewCount() < item2.getReviewCount()){
                                    return 1;
                                }
                                else{
                                    return 0;
                                }
                            }
                        };
                        Collections.sort(adapter.listData, basicSort) ;
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(sortSelect==3){
                        Comparator<RecyclerListData> highRateSort = new Comparator<RecyclerListData>() {
                            @Override
                            public int compare(RecyclerListData item1, RecyclerListData item2) {
                                return item2.getRateText().compareTo(item1.getRateText());
                            }
                        };
                        Collections.sort(adapter.listData, highRateSort) ;
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(sortSelect==4){
                        Comparator<RecyclerListData> lowRateSort = new Comparator<RecyclerListData>() {
                            @Override
                            public int compare(RecyclerListData item1, RecyclerListData item2) {
                                return item1.getRateText().compareTo(item2.getRateText());
                            }
                        };
                        Collections.sort(adapter.listData, lowRateSort) ;
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(sortSelect==2){
                        Comparator<RecyclerListData> dateSort = new Comparator<RecyclerListData>() {
                            @Override
                            public int compare(RecyclerListData item1, RecyclerListData item2) {
                                return item2.getDate().compareTo(item1.getDate());
                            }
                        };
                        Collections.sort(adapter.listData, dateSort) ;
                        adapter.notifyDataSetChanged() ;
                    }
                    else if(sortSelect==1){
                        Comparator<RecyclerListData> goodCountSort = new Comparator<RecyclerListData>() {
                            @Override
                            public int compare(RecyclerListData item1, RecyclerListData item2) {
                                if(item1.getGoodCount() > item2.getGoodCount()){
                                    return -1;
                                }
                                else if(item1.getGoodCount() < item2.getGoodCount()){
                                    return 1;
                                }
                                else{
                                    return 0;
                                }
                            }
                        };
                        Collections.sort(adapter.listData, goodCountSort) ;
                        adapter.notifyDataSetChanged() ;
                    }
                }
            }
        });
        // 8도 버튼 클릭
        btnOptionTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionJj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionSl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionGg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionGw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionJl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        btnOptionGs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOptionPageOpen) {
                    slidingdownPanel.startAnimation(translateDownAnim);
                    OptionControlCheck();
                }
            }
        });
        return view;
    }
    private void OptionButtonCheck(){
        if(btnOptionControl==1){
            btnOptionTotal.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==2){
            btnOptionJj.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==3){
            btnOptionSl.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==4){
            btnOptionBs.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==5){
            btnOptionGg.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==6){
            btnOptionGw.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==7){
            btnOptionCc.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==8){
            btnOptionJl.setVisibility(View.VISIBLE);
        }
        else if(btnOptionControl==9){
            btnOptionGs.setVisibility(View.VISIBLE);
        }
    }
    private void OptionControlCheck(){
        if(btnOptionControl==1){
            btnOptionTotal.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==2){
            btnOptionJj.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==3){
            btnOptionSl.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==4){
            btnOptionBs.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==5){
            btnOptionGg.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==6){
            btnOptionGw.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==7){
            btnOptionCc.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==8){
            btnOptionJl.setVisibility(View.INVISIBLE);
        }
        else if(btnOptionControl==9){
            btnOptionGs.setVisibility(View.INVISIBLE);
        }
    }

    // 리사이클러뷰 초기화
    private void DataInit(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerListAdapter();
        getData();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("국화", "사막", "수국", "해파리", "코알라", "등대", "펭귄", "튤립",
                "국화", "사막", "수국", "해파리", "코알라", "등대", "펭귄", "튤립");
        List<String> listContent = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다.",
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다."
        );
        List<String> listRate = Arrays.asList("4.5", "4.1", "1.0", "2.0", "2.2", "5.0", "2.0", "2.5",
                "3.5", "3.5", "3.5", "3.5", "3.5", "3.5", "3.5", "2.5");
        List<String> listDate = Arrays.asList("2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05",
                "2019.02.15", "2019.03.05", "2020.02.05", "2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05", "2019.02.05");
        List<String> listRegion = Arrays.asList("경기도", "경기도", "강원도", "제주도", "경상도", "경상도", "경상도", "경상도",
                "경기도", "경상도", "강원도", "강원도", "충청도", "충청도", "경상도", "경상도");
        List<Integer> listContentImageView = Arrays.asList(
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image,
                R.drawable.sample_image
        );
        List<Integer> listGoodCount = Arrays.asList(2,3,4,5,61,2,4,5,6,0,1,2,4,5,1,5);
        List<Integer> listReviewCount = Arrays.asList(20,31,45,5,61,21,14,52,6,0,1,2,4,5,1,5);
        int rateImageView = R.drawable.star;
        for (int i = 0; i < listTitle.size(); i++) {
            RecyclerListData data = new RecyclerListData();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setGetRateText(listRate.get(i));
            data.setDate(listDate.get(i));
            data.setRegion(listRegion.get(i));
            data.setGoodCount(listGoodCount.get(i));
            data.setReviewCount(listReviewCount.get(i));
            data.setRateImageView(rateImageView);
            data.setContentImageView(listContentImageView.get(i));
            adapter.addItem(data);
            adapter.addTotalItem(data);
            if(listRegion.get(i).compareTo("제주도")==0){
                adapter.addJjItem(data);
            }
            else if(listRegion.get(i).compareTo("서울특별시")==0){
                adapter.addSlItem(data);
            }
            else if(listRegion.get(i).compareTo("부산광역시")==0){
                adapter.addBsItem(data);
            }
            else if(listRegion.get(i).compareTo("경기도")==0){
                adapter.addGgItem(data);
            }
            else if(listRegion.get(i).compareTo("강원도")==0){
                adapter.addGwItem(data);
            }
            else if(listRegion.get(i).compareTo("충청도")==0){
                adapter.addCcItem(data);
            }
            else if(listRegion.get(i).compareTo("전라도")==0){
                adapter.addJlItem(data);
            }
            else if(listRegion.get(i).compareTo("경상도")==0){
                adapter.addGsItem(data);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
