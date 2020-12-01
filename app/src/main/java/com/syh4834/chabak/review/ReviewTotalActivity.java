package com.syh4834.chabak.review;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.syh4834.chabak.R;
import com.syh4834.chabak.review.recycler.RecyclerReviewAdapter;
import com.syh4834.chabak.review.recycler.RecyclerReviewData;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceReviewData;
import com.syh4834.chabak.api.response.ResponsePlaceReview;
import com.syh4834.chabak.placeDetail.PlaceDetailActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewTotalActivity extends AppCompatActivity {
    private int REQUEST_REVIEW_TOTAL_UPLOAD = 10012;

    private RecyclerView rvReviewTotal;
    private RecyclerReviewAdapter recyclerReviewAdapter;

    private Button btnBack;
    private Button btnEdit;

    private RadioButton rbRangeRec;
    private RadioButton rbRangeLatest;

    private TextView tvReviewCount;

    private PlaceReviewData[] placeReviewData;

    int placeIdx;
    int reviewCnt;
    int reviewStar = 0;

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
        btnEdit = findViewById(R.id.btn_edit);

        rbRangeRec = findViewById(R.id.rb_range_rec);
        rbRangeLatest = findViewById(R.id.rb_range_latest);

        placeIdx = getIntent().getIntExtra("placeIdx", 0);

        reviewCnt = getIntent().getIntExtra("reviewCnt", 0);
        tvReviewCount.setText(String.valueOf(reviewCnt));
        SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        getPlaceReviewData("like");

        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, PlaceDetailActivity.class);
            if(reviewStar > 0) {
                intent.putExtra("reviewStar", reviewStar);
                intent.putExtra("reviewCnt", reviewCnt);
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED, intent);
            }
            finish();
        });

        btnEdit.setOnClickListener(l -> {
            Intent intent = new Intent(this, ReviewUploadActivity.class);
            intent.putExtra("placeIdx",getIntent().getIntExtra("placeIdx", 0));
            intent.putExtra("placeTitle", getIntent().getStringExtra("placeTitle"));
            intent.putExtra("placeName", getIntent().getStringExtra("placeName"));
            intent.putExtra("placeImg", getIntent().getStringExtra("placeImg"));
            startActivityForResult(intent, REQUEST_REVIEW_TOTAL_UPLOAD);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {
            reviewCnt++;
            reviewStar = data.getIntExtra("reviewStar", 0);
            tvReviewCount.setText(String.valueOf(reviewCnt));
            rbRangeLatest.performClick();

            UploadReviewSuccessDialog uploadReviewSuccessDialog = new UploadReviewSuccessDialog(ReviewTotalActivity.this);
            uploadReviewSuccessDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            uploadReviewSuccessDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}