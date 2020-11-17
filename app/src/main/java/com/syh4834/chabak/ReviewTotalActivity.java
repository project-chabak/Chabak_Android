package com.syh4834.chabak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceReviewData;
import com.syh4834.chabak.api.response.ResponsePlaceReview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewTotalActivity extends AppCompatActivity {

    private RecyclerView rvReviewTotal;
    private RecyclerReviewAdapter recyclerReviewAdapter;

    private Button btnBack;

    private RadioButton rbRangeRec;
    private RadioButton rbRangeLatest;

    private TextView tvReviewCount;

    private PlaceReviewData[] placeReviewData;

    int placeIdx;
    String token;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_total);

        rvReviewTotal = findViewById(R.id.rv_review_total);

        tvReviewCount = findViewById(R.id.tv_review_count);

        btnBack = findViewById(R.id.btn_back);

        rbRangeRec = findViewById(R.id.rb_range_rec);
        rbRangeLatest = findViewById(R.id.rb_range_latest);

        placeIdx = getIntent().getIntExtra("placeIdx", 0);
//        SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
//        token = sharedPreferences.getString("token", null);
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJpZCI6ImlkIiwibmlja25hbWUiOiIxMjMiLCJpYXQiOjE2MDQ5NzMxMDN9.80OjSRBho8176t0BgYu5tuEZ5pJGBh_tCjVn_Nsic_I";

        getPlaceReviewData("like");

        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, PlaceDetailActivity.class);
            finish();
        });

        rbRangeRec.setOnClickListener(l -> {
            getPlaceReviewData("like");
        });

        rbRangeLatest.setOnClickListener(l -> {
            getPlaceReviewData("new");
        });
        //ArrayList<PlaceReviewData> reviewList= getIntent().getParcelableArrayListExtra("reviews");

        //setPlaceReview(reviewList);
    }

    private void getPlaceReviewData(String order) {
        chabakService.getPlaceReview(token, placeIdx, order).enqueue(new Callback<ResponsePlaceReview>() {
            @Override
            public void onResponse(Call<ResponsePlaceReview> call, Response<ResponsePlaceReview> response) {
                if(response.body().getSuccess()) {
                    placeReviewData = response.body().getData();
                    setPlaceReview(placeReviewData);
                }
            }

            @Override
            public void onFailure(Call<ResponsePlaceReview> call, Throwable t) {

            }
        });
    }


    private void setPlaceReview(PlaceReviewData[] placeReviewData) {
        tvReviewCount.setText(String.valueOf(placeReviewData.length));
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
        rvReviewTotal.addItemDecoration(dividerItemDecoration);

        for(int i = 0; i < placeReviewData.length; i++) {
            RecyclerReviewData recyclerReviewData = new RecyclerReviewData();
            recyclerReviewData.setWriter(placeReviewData[i].getNickname());
            recyclerReviewData.setReviewIdx(placeReviewData[i].getReviewIdx());
            recyclerReviewData.setContent(placeReviewData[i].getReviewContent());
            recyclerReviewData.setDate(placeReviewData[i].getReviewDate());
            recyclerReviewData.setStar(placeReviewData[i].getReviewStar());
            recyclerReviewData.setLikeCnt(placeReviewData[i].getReviewLikeCnt());
            recyclerReviewData.setPicture(placeReviewData[i].getReviewImg());
            recyclerReviewData.setUserLike(placeReviewData[i].getUserLike());

            recyclerReviewAdapter.addItem(recyclerReviewData);
        }
        recyclerReviewAdapter.notifyDataSetChanged();
    }
}