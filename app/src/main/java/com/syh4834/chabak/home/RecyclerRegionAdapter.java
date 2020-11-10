package com.syh4834.chabak.home;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syh4834.chabak.R;

import java.util.ArrayList;

public class RecyclerRegionAdapter extends RecyclerView.Adapter<RecyclerRegionAdapter.ItemViewHolder> {

    private ArrayList<RecyclerRegionData> listRegion = new ArrayList<>();

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

    public void addItem(RecyclerRegionData recyclerReviewPictureData) {
        listRegion.add(recyclerReviewPictureData);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRegionName;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);

            tvRegionName = itemView.findViewById(R.id.tv_region_name);
            tvRegionName.setClipToOutline(true);
        }

        void onBind(RecyclerRegionData recyclerReviewData) {
            tvRegionName.setText(recyclerReviewData.getRegionName());
        }
    }
}