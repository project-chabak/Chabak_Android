package com.syh4834.chabak;

import android.content.Intent;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Button btnFilter;
    Button btnOption;
    Button btnFilterBack;

    Animation translateLeftAnim;
    Animation translateRightAnim;
    ConstraintLayout slidingPanel;
    ConstraintLayout backgroundView;
    boolean isPageOpen=false;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //아래 변수들은 버튼들
        btnFilter = (Button) view.findViewById(R.id.btn_fillter);
        btnOption = (Button) view.findViewById(R.id.btn_option);
        btnFilterBack = (Button) view.findViewById(R.id.btn_close);

        //이 중앙 코드는 리사이클러뷰 적용 코드
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerListAdapter();
        getData();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //아래는 애니메이션
        translateLeftAnim =AnimationUtils.loadAnimation(getActivity(),R.anim.down);
        translateRightAnim =AnimationUtils.loadAnimation(getActivity(),R.anim.up);

        translateLeftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(isPageOpen){
                    slidingPanel.setVisibility(View.INVISIBLE);
                    isPageOpen=false;
                }else{
                    isPageOpen=true;
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
                if(isPageOpen){
                    slidingPanel.setVisibility(View.INVISIBLE);
                    isPageOpen=false;
                }else{

                    isPageOpen=true;
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
                if(isPageOpen){
                    slidingPanel.startAnimation(translateRightAnim);
                    slidingPanel.setVisibility(View.GONE);
                    btnFilter.setVisibility(View.VISIBLE);
                    backgroundView.setAlpha((float)1.0);
                }
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPageOpen) {
                    slidingPanel.startAnimation(translateLeftAnim);
                    slidingPanel.setVisibility(View.VISIBLE);
                    btnFilter.setVisibility(View.GONE);
                    backgroundView.setAlpha((float) 0.2);
                }
            }
        });
        return view;
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
        int rateImageView = R.drawable.star;
        for (int i = 0; i < listTitle.size(); i++) {
            RecyclerListData data = new RecyclerListData();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setGetRateText(listRate.get(i));

            data.setRateImageView(rateImageView);
            data.setContentImageView(listContentImageView.get(i));

            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
}
