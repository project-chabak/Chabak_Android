package com.syh4834.chabak.home;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syh4834.chabak.R;

import java.util.ArrayList;

public class RecyclerLikeAdapter extends RecyclerView.Adapter<RecyclerLikeAdapter.ItemViewHolder> {

    private ArrayList<String> listRegion = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerLikeAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false);
        return new RecyclerLikeAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerLikeAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listRegion.get(position));
    }

    @Override
    public int getItemCount() {
        return listRegion.size();
    }

    public void addItem(String title) {
        listRegion.add(title);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLikeTitle;
        private ImageView imgLikePicture;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);

            tvLikeTitle = itemView.findViewById(R.id.tv_like_title);
            imgLikePicture = itemView.findViewById(R.id.img_like_picture);
            imgLikePicture.setClipToOutline(true);
        }

        void onBind(String recyclerReviewData) {
            tvLikeTitle.setText(recyclerReviewData);
        }
    }
}