package com.syh4834.chabak.review.recycler;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syh4834.chabak.R;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.request.RequestLikeReview;
import com.syh4834.chabak.api.response.ResponseLike;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecyclerReviewAdapter extends RecyclerView.Adapter<RecyclerReviewAdapter.ItemViewHolder> {

    private ArrayList<RecyclerReviewData> listData = new ArrayList<>();
    private static String token;


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

    public void addItem(RecyclerReviewData recyclerReviewData) {
        listData.add(recyclerReviewData);
    }

    public void setToken(String token) {
        this.token = token;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWriter;
        private TextView tvReviewDate;
        private TextView tvReviewContent;
        private TextView tvGoodCount;
        private TextView tvGood;

        private ImageView imgPicture;
        private ImageView imgStar01;
        private ImageView imgStar02;
        private ImageView imgStar03;
        private ImageView imgStar04;
        private ImageView imgStar05;

        private CheckBox chbGood;

        private RecyclerView rvReviewPicture;
        private RecyclerReviewImageAdapter recyclerReviewImageAdapter;

        int reviewGoodCnt;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvWriter = itemView.findViewById(R.id.tv_writer);
            tvReviewDate = itemView.findViewById(R.id.tv_review_date);
            tvReviewContent = itemView.findViewById(R.id.tv_review_content);
            tvGood = itemView.findViewById(R.id.tv_good);
            tvGoodCount = itemView.findViewById(R.id.tv_good_count);

            imgPicture = itemView.findViewById(R.id.img_picture);
            imgStar01 = itemView.findViewById(R.id.img_star_01);
            imgStar02 = itemView.findViewById(R.id.img_star_02);
            imgStar03 = itemView.findViewById(R.id.img_star_03);
            imgStar04 = itemView.findViewById(R.id.img_star_04);
            imgStar05 = itemView.findViewById(R.id.img_star_05);

            chbGood = itemView.findViewById(R.id.chb_good);

            rvReviewPicture = itemView.findViewById(R.id.rv_review_picture);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvReviewPicture.getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvReviewPicture.setLayoutManager(linearLayoutManager);

            recyclerReviewImageAdapter = new RecyclerReviewImageAdapter();
            rvReviewPicture.setAdapter(recyclerReviewImageAdapter);
        }

        void onBind(RecyclerReviewData recyclerReviewData) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ChabakService.BASE_RUL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ChabakService chabakService = retrofit.create(ChabakService.class);

            tvWriter.setText(recyclerReviewData.getWriter());
            tvReviewDate.setText(recyclerReviewData.getDate());
            tvReviewContent.setText(recyclerReviewData.getContent());
            reviewGoodCnt = recyclerReviewData.getLikeCnt();
            tvGoodCount.setText(String.valueOf(reviewGoodCnt));

            switch(recyclerReviewData.getStar()) {
                case 5:
                    imgStar05.setImageResource(R.drawable.star_yellow);
                case 4:
                    imgStar04.setImageResource(R.drawable.star_yellow);
                case 3:
                    imgStar03.setImageResource(R.drawable.star_yellow);
                case 2:
                    imgStar02.setImageResource(R.drawable.star_yellow);
                case 1:
                    imgStar01.setImageResource(R.drawable.star_yellow);
            }

            if (recyclerReviewData.getUserLike()) {
                chbGood.setChecked(true);
                tvGood.setTextColor(Color.parseColor("#3C836E"));
                tvGoodCount.setTextColor(Color.parseColor("#3C836E"));
            }

            chbGood.setOnClickListener(l -> {
                if(chbGood.isChecked()) {
                    chabakService.likeReview(token, new RequestLikeReview(recyclerReviewData.getReviewIdx())).enqueue(new Callback<ResponseLike>() {
                        @Override
                        public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                            if (response.body().getSuccess()) {
                                chbGood.setChecked(true);
                                reviewGoodCnt++;
                                tvGoodCount.setText(String.valueOf(reviewGoodCnt));
                                tvGood.setTextColor(Color.parseColor("#3C836E"));
                                tvGoodCount.setTextColor(Color.parseColor("#3C836E"));
                                Log.e("reviewLike", "success");
                            } else {
                                Log.e("reviewLike", "fail");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseLike> call, Throwable t) {
                            Log.e("fail","fail");

                        }
                    });
                } else {
                    chabakService.cancleLikedReview(token, new RequestLikeReview(recyclerReviewData.getReviewIdx())).enqueue(new Callback<ResponseLike>() {
                        @Override
                        public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                            if (response.body().getSuccess()) {
                                chbGood.setChecked(false);
                                reviewGoodCnt--;
                                tvGoodCount.setText(String.valueOf(reviewGoodCnt));
                                tvGood.setTextColor(Color.parseColor("#656565"));
                                tvGoodCount.setTextColor(Color.parseColor("#656565"));

                                Log.e("cancleLikedReview", "success");
                            } else {
                                Log.e("cancleLikedReview", "fail");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseLike> call, Throwable t) {
                            Log.e("fail","fail");

                        }
                    });
                }
            });

            String[] listPictures = recyclerReviewData.getPicture();
            if(listPictures.length > 0) {
                imgPicture.setVisibility(View.VISIBLE);
            }
            // 이미지 첨부
            for(int i =0; i<listPictures.length; i++) {
                RecyclerReviewImageData recyclerReviewImageData = new RecyclerReviewImageData();
                recyclerReviewImageData.setPicture(listPictures[i]);

                recyclerReviewImageAdapter.addItem(recyclerReviewImageData);
            }
            recyclerReviewImageAdapter.notifyDataSetChanged();
        }
    }
}


