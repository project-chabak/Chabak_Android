package com.syh4834.chabak.review.recycler;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syh4834.chabak.R;

import java.util.ArrayList;

public class RecyclerReviewImageAdapter extends RecyclerView.Adapter<RecyclerReviewImageAdapter.ItemViewHolder> {

    private ArrayList<RecyclerReviewImageData> listPicture = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerReviewImageAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_image, parent, false);
        return new RecyclerReviewImageAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerReviewImageAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listPicture.get(position));
    }

    @Override
    public int getItemCount() {
        return listPicture.size();
    }

    void addItem(RecyclerReviewImageData recyclerReviewImageData) {
        listPicture.add(recyclerReviewImageData);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgReviewPicture;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);

            imgReviewPicture = itemView.findViewById(R.id.img_review_picture);
            imgReviewPicture.setClipToOutline(true);
        }

        void onBind(RecyclerReviewImageData recyclerReviewImageData) {
            Glide.with(itemView).load(recyclerReviewImageData.getPicture()).into(imgReviewPicture);
        }
    }
}


