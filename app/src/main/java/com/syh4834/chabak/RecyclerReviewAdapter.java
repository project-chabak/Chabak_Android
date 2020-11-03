package com.syh4834.chabak;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        private ImageView imgStar01;
        private RecyclerView rvReviewPicture;
        private RecyclerReviewPictureAdapter recyclerReviewPictureAdapter;


        ItemViewHolder(View itemView) {
            super(itemView);

            tvWriter = itemView.findViewById(R.id.tv_writer);
            imgStar01 = itemView.findViewById(R.id.img_star_01);

            RecyclerView rvReviewPicture = itemView.findViewById(R.id.rv_review_picture);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvReviewPicture.getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvReviewPicture.setLayoutManager(linearLayoutManager);

            recyclerReviewPictureAdapter = new RecyclerReviewPictureAdapter();
            rvReviewPicture.setAdapter(recyclerReviewPictureAdapter);

            List<Integer> listPictures = Arrays.asList(R.drawable.place_image_01, R.drawable.place_image_02, R.drawable.place_image_01);

            for(int i =0; i<listPictures.size(); i++) {
                RecyclerReviewPictureData recyclerReviewPictureData = new RecyclerReviewPictureData();
                recyclerReviewPictureData.setPicture(listPictures.get(i));

                recyclerReviewPictureAdapter.addItem(recyclerReviewPictureData);
            }
            recyclerReviewPictureAdapter.notifyDataSetChanged();

        }

        void onBind(RecyclerReviewData recyclerReviewData) {
            tvWriter.setText(recyclerReviewData.getWriter());
            imgStar01.setImageResource(R.drawable.star_yellow);
        }
    }
}


