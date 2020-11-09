package com.syh4834.chabak;


import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> {

    private ArrayList<RecyclerListData> listData = new ArrayList<>();


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

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView rateText;

        private ImageView likeImageView;
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
            likeImageView.setImageResource(listData.getLikeImageView());
            rateImageView.setImageResource(listData.getRateImageView());
            contentImageView.setImageResource(listData.getContentImageView());
            contentImageView.setClipToOutline(true);
        }
    }
}
