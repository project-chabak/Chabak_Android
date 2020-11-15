package com.syh4834.chabak;


import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> {

    public ArrayList<RecyclerListData> listData = new ArrayList<>(); // 어뎁터데이터
    public ArrayList<RecyclerListData> totalData = new ArrayList<>(); // 전체데이터
    public ArrayList<RecyclerListData> jjListData = new ArrayList<>(); //  제주
    public ArrayList<RecyclerListData> slListData = new ArrayList<>(); // 서울
    public ArrayList<RecyclerListData> bsListData = new ArrayList<>(); // 부산
    public ArrayList<RecyclerListData> ggListData = new ArrayList<>(); // 경기
    public ArrayList<RecyclerListData> gwListData = new ArrayList<>(); // 강원
    public ArrayList<RecyclerListData> ccListData = new ArrayList<>(); // 충청
    public ArrayList<RecyclerListData> jlListData = new ArrayList<>(); // 전라
    public ArrayList<RecyclerListData> gsListData = new ArrayList<>(); // 경상

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerListAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(RecyclerListData recyclerListData) {
        listData.add(recyclerListData);
    }

    void addTotalItem(RecyclerListData recyclerListData) {
        totalData.add(recyclerListData);
    }

    void addJjItem(RecyclerListData recyclerListData) {
        jjListData.add(recyclerListData);
    }

    void addSlItem(RecyclerListData recyclerListData) {
        slListData.add(recyclerListData);
    }

    void addBsItem(RecyclerListData recyclerListData) {
        bsListData.add(recyclerListData);
    }

    void addGgItem(RecyclerListData recyclerListData) {
        ggListData.add(recyclerListData);
    }

    void addGwItem(RecyclerListData recyclerListData) {
        gwListData.add(recyclerListData);
    }

    void addCcItem(RecyclerListData recyclerListData) {
        ccListData.add(recyclerListData);
    }

    void addJlItem(RecyclerListData recyclerListData) {
        jlListData.add(recyclerListData);
    }

    void addGsItem(RecyclerListData recyclerListData) {
        gsListData.add(recyclerListData);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView rateText;

        private CheckBox likeImageView;
        private ImageView rateImageView;
        private ImageView contentImageView;


        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
            rateText = itemView.findViewById(R.id.rate_text);

            likeImageView = itemView.findViewById(R.id.like_image_view);
            rateImageView = itemView.findViewById(R.id.rate_image_view);
            contentImageView = itemView.findViewById(R.id.content_image_view);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void onBind(RecyclerListData listData) {
            textView1.setText(listData.getTitle());
            textView2.setText(listData.getContent());
            rateText.setText(listData.getRateText());
            rateImageView.setImageResource(listData.getRateImageView());
            contentImageView.setImageResource(listData.getContentImageView());
            contentImageView.setClipToOutline(true);
        }
    }
}
