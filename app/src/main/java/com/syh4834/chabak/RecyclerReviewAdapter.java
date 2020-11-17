package com.syh4834.chabak;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;


public class RecyclerReviewAdapter extends RecyclerView.Adapter<RecyclerReviewAdapter.ItemViewHolder> {

    private ArrayList<RecyclerReviewData> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerReviewAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(RecyclerReviewData recyclerReviewData) {
        listData.add(recyclerReviewData);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWriter;
        private TextView tvReviewDate;
        private TextView tvReviewContent;
        private TextView tvGoodCount;

        private ImageView imgPicture;
        private ImageView imgStar01;
        private ImageView imgStar02;
        private ImageView imgStar03;
        private ImageView imgStar04;
        private ImageView imgStar05;

        private CheckBox chbGood;

        private RecyclerView rvReviewPicture;
        private RecyclerReviewImageAdapter recyclerReviewImageAdapter;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvWriter = itemView.findViewById(R.id.tv_writer);
            tvReviewDate = itemView.findViewById(R.id.tv_review_date);
            tvReviewContent = itemView.findViewById(R.id.tv_review_content);
            tvGoodCount = itemView.findViewById(R.id.tv_good_count);

            imgPicture = itemView.findViewById(R.id.img_picture);
            imgStar01 = itemView.findViewById(R.id.img_star_01);
            imgStar02 = itemView.findViewById(R.id.img_star_02);
            imgStar03 = itemView.findViewById(R.id.img_star_03);
            imgStar04 = itemView.findViewById(R.id.img_star_04);
            imgStar05 = itemView.findViewById(R.id.img_star_05);

            chbGood = itemView.findViewById(R.id.chb_good);

            rvReviewPicture = itemView.findViewById(R.id.rv_review_picture);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvReviewPicture.getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvReviewPicture.setLayoutManager(linearLayoutManager);

            recyclerReviewImageAdapter = new RecyclerReviewImageAdapter();
            rvReviewPicture.setAdapter(recyclerReviewImageAdapter);
        }

        void onBind(RecyclerReviewData recyclerReviewData) {
            tvWriter.setText(recyclerReviewData.getWriter());
            tvReviewDate.setText(recyclerReviewData.getDate());
            tvReviewContent.setText(recyclerReviewData.getContent());
            tvGoodCount.setText(String.valueOf(recyclerReviewData.getLikeCnt()));

            switch(recyclerReviewData.getStar()) {
                case 5:
                    imgStar05.setImageResource(R.drawable.star_yellow);
                case 4:
                    imgStar04.setImageResource(R.drawable.star_yellow);
                case 3:
                    imgStar03.setImageResource(R.drawable.star_yellow);
                case 2:
                    imgStar02.setImageResource(R.drawable.star_yellow);
                case 1:
                    imgStar01.setImageResource(R.drawable.star_yellow);
            }

            if (recyclerReviewData.getUserLike()) {
                chbGood.setChecked(true);
            }

            String[] listPictures = recyclerReviewData.getPicture();
            if(listPictures.length > 0) {
                imgPicture.setVisibility(View.VISIBLE);
            }
            for(int i =0; i<listPictures.length; i++) {
                RecyclerReviewImageData recyclerReviewImageData = new RecyclerReviewImageData();
                recyclerReviewImageData.setPicture(listPictures[i]);

                recyclerReviewImageAdapter.addItem(recyclerReviewImageData);
            }
            recyclerReviewImageAdapter.notifyDataSetChanged();
        }
    }
}


