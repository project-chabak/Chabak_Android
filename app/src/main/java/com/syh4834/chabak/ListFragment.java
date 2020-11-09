package com.syh4834.chabak;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerListAdapter();
        getData();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        int likeImageView = R.drawable.like_unselected_btn;
        int rateImageView = R.drawable.star;
        for (int i = 0; i < listTitle.size(); i++) {
            RecyclerListData data = new RecyclerListData();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setGetRateText(listRate.get(i));

            data.setLikeImageView(likeImageView);
            data.setRateImageView(rateImageView);
            data.setContentImageView(listContentImageView.get(i));

            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
}
