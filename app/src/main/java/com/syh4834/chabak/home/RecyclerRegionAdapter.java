package com.syh4834.chabak.home;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syh4834.chabak.MainActivity;
import com.syh4834.chabak.R;
import com.syh4834.chabak.api.data.Category;

import java.util.ArrayList;

public class RecyclerRegionAdapter extends RecyclerView.Adapter<RecyclerRegionAdapter.ItemViewHolder> {

    private ArrayList<Category> listRegion = new ArrayList<>();
    Context context;

    public RecyclerRegionAdapter(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerRegionAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region_circle, parent, false);
        return new RecyclerRegionAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRegionAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listRegion.get(position));
    }

    @Override
    public int getItemCount() {
        return listRegion.size();
    }

    public void addItem(Category recyclerReviewPictureData) {
        listRegion.add(recyclerReviewPictureData);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRegionName;
        private ImageView imgRegionBg;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);

            tvRegionName = itemView.findViewById(R.id.tv_region_name);
            imgRegionBg = itemView.findViewById(R.id.img_region_bg);
        }

        void onBind(Category recyclerReviewData) {
            tvRegionName.setText(recyclerReviewData.getPlaceCategoryName());
            Glide.with(itemView).load(recyclerReviewData.getPlaceCategoryImg()).into(imgRegionBg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)context).setPlaceCategory(recyclerReviewData.placeCategoryIdx());
                }
            });
        }
    }
}