package com.syh4834.chabak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.syh4834.chabak.api.data.PlaceReviewData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewTotalActivity extends AppCompatActivity {

    private RecyclerView rvReviewTotal;
    private RecyclerReviewAdapter recyclerReviewAdapter;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_total);

        rvReviewTotal = findViewById(R.id.rv_review_total);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, PlaceDetailActivity.class);
            finish();
        });

        ArrayList<PlaceReviewData> reviewList= getIntent().getParcelableArrayListExtra("reviews");

        setPlaceReview(reviewList);

    }

    private void setPlaceReview(ArrayList<PlaceReviewData> reviewList) {
        rvReviewTotal = findViewById(R.id.rv_review_total);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvReviewTotal.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvReviewTotal.setLayoutManager(linearLayoutManager);

        recyclerReviewAdapter = new RecyclerReviewAdapter();
        rvReviewTotal.setAdapter(recyclerReviewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvReviewTotal.getContext(), linearLayoutManager.getOrientation());
        //dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_seperator));
        rvReviewTotal.addItemDecoration(dividerItemDecoration);

        //List<String> listWriter = Arrays.asList("작성자1", "작성자2", "작성자3", "작성자4");

        for(int i = 0; i < reviewList.size(); i++) {
            Log.e("size", String.valueOf(reviewList.size()));
            Log.e("reviewList2", reviewList.get(1).getNickname());

            RecyclerReviewData recyclerReviewData = new RecyclerReviewData();
            recyclerReviewData.setWriter(reviewList.get(i).getNickname());
            recyclerReviewData.setReviewIdx(reviewList.get(i).getReviewIdx());
            recyclerReviewData.setContent(reviewList.get(i).getReviewContent());
            recyclerReviewData.setDate(reviewList.get(i).getReviewDateString());
            recyclerReviewData.setStar(reviewList.get(i).getReviewStar());
            recyclerReviewData.setLikeCnt(reviewList.get(i).getReviewLikeCnt());
            recyclerReviewData.setPicture(reviewList.get(i).getReviewImg());
            recyclerReviewData.setUserLike(reviewList.get(i).getUserLikeInt());

            recyclerReviewAdapter.addItem(recyclerReviewData);
        }

        recyclerReviewAdapter.notifyDataSetChanged();
    }
}