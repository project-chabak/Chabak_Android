package com.syh4834.chabak;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerReviewPictureAdapter extends RecyclerView.Adapter<RecyclerReviewPictureAdapter.ItemViewHolder> {

    private ArrayList<RecyclerReviewPictureData> listPicture = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerReviewPictureAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_image, parent, false);
        return new RecyclerReviewPictureAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerReviewPictureAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listPicture.get(position));
    }

    @Override
    public int getItemCount() {
        return listPicture.size();
    }

    void addItem(RecyclerReviewPictureData recyclerReviewPictureData) {
        listPicture.add(recyclerReviewPictureData);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgReviewPicture;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);

            imgReviewPicture = itemView.findViewById(R.id.img_review_picture);
            imgReviewPicture.setClipToOutline(true);
        }

        void onBind(RecyclerReviewPictureData recyclerReviewData) {
            imgReviewPicture.setImageResource(recyclerReviewData.getPicture());
        }
    }
}


