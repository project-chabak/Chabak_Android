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

import com.bumptech.glide.Glide;
import com.syh4834.chabak.review.recycler.RecyclerReviewUploadImgAdapter;

import java.util.ArrayList;

public class RecyclerLikeAdapter extends RecyclerView.Adapter<RecyclerLikeAdapter.ItemViewHolder> {
    public ArrayList<RecyclerListData> listData = new ArrayList<>();
    private RecyclerReviewUploadImgAdapter.OnItemClickListener listener = null;

    private static String token;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    @NonNull
    @Override
    public RecyclerLikeAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_like_item, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerLikeAdapter.ItemViewHolder holder, int position) {
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

        private ImageView rateImageView;
        private ImageView contentImageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.text_view_like1);
            textView2 = itemView.findViewById(R.id.text_view_like2);
            rateText = itemView.findViewById(R.id.rate_text_like);

            rateImageView = itemView.findViewById(R.id.rate_image_view_like);
            contentImageView = itemView.findViewById(R.id.content_image_view_like);
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

