package com.syh4834.chabak;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.request.RequestLikePlace;
import com.syh4834.chabak.api.response.ResponseLike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> {

    public ArrayList<RecyclerListData> listData = new ArrayList<>();
    private RecyclerReviewUploadImgAdapter.OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

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

    public void setOnItemClickListener(RecyclerReviewUploadImgAdapter.OnItemClickListener listner) {
        this.listener = listner;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
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
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if(listener != null) {
                        listener.onItemClick(v, pos);
                    }
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void onBind(RecyclerListData listData) {
            textView1.setText(listData.getTitle());
            textView2.setText(listData.getContent());
            rateText.setText(String.valueOf(listData.getRateText()));
            rateImageView.setImageResource(listData.getRateImageView());
            Glide.with(itemView).load(listData.getContentImageView()).into(contentImageView);
            contentImageView.setClipToOutline(true);
        }
    }
}
