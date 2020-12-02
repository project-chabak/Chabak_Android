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

import static android.view.View.INVISIBLE;

public class RecyclerReviewUploadImgAdapter extends RecyclerView.Adapter<RecyclerReviewUploadImgAdapter.ItemViewHolder> {

    private ArrayList<RecyclerReviewUploadImgData> listUploadImg = new ArrayList<>();
    private OnItemClickListener listener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerReviewUploadImgAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_upload_img, parent, false);
        return new RecyclerReviewUploadImgAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerReviewUploadImgAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listUploadImg.get(position));
        holder.itemView.findViewById(R.id.btn_cancle).setOnClickListener(l -> {
            removeItem(position);
        });
        if(position == 0) {
            holder.itemView.findViewById(R.id.btn_cancle).setVisibility(INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listUploadImg.size();
    }

    public void addItem(RecyclerReviewUploadImgData recyclerReviewUploadImgData) {
        listUploadImg.add(recyclerReviewUploadImgData);
    }

    void removeItem(int position) {
        listUploadImg.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public ArrayList<RecyclerReviewUploadImgData> getItem() {
        return listUploadImg;
    }

    public void setOnItemClickListener(OnItemClickListener listner) {
        this.listener = listner;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgReviewUpload;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if(listener != null) {
                        listener.onItemClick(v, pos);
                    }
                }
            });
            imgReviewUpload = itemView.findViewById(R.id.img_review_upload);
            imgReviewUpload.setClipToOutline(true);
        }

        void onBind(RecyclerReviewUploadImgData recyclerReviewUploadImgData) {
            Glide.with(itemView).load(recyclerReviewUploadImgData.getUploadImg()).into(imgReviewUpload);
//            imgReviewUpload.setImageURI(Uri.parse(recyclerReviewUploadImgData.getUploadImg()));
        }
    }
}


