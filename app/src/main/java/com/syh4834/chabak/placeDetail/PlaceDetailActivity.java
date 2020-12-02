package com.syh4834.chabak.placeDetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.syh4834.chabak.R;
import com.syh4834.chabak.review.recycler.RecyclerReviewAdapter;
import com.syh4834.chabak.review.recycler.RecyclerReviewData;
import com.syh4834.chabak.review.ReviewTotalActivity;
import com.syh4834.chabak.review.ReviewUploadActivity;
import com.syh4834.chabak.review.UploadReviewSuccessDialog;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceDetailData;
import com.syh4834.chabak.api.data.PlaceReviewData;
import com.syh4834.chabak.api.data.PlaceToiletData;
import com.syh4834.chabak.api.request.RequestLikePlace;
import com.syh4834.chabak.api.response.ResponsePlaceDetail;
import com.syh4834.chabak.api.response.ResponseLike;
import com.syh4834.chabak.api.response.ResponsePlaceReview;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private int REQUEST_REVIEW_UPLOAD = 10011;
    private int REQUEST_REVIEW_TOTAL = 10013;

    private PlaceDetailData placeDetailData;
    private PlaceReviewData[] placeReviewData;
    private PlaceToiletData[] placeToiletData;

    private PlaceImagePageAdapter placeImagePageAdapter;
    private ViewPager vpPlaceImage;

    private TextView tvToolbarTitle;
    private TextView tvImageNum;
    private TextView tvTitle;
    private TextView tvPlaceName;
    private TextView tvStar;
    private TextView tvReviewCount;
    private TextView tvLike;
    private TextView tvAddress;
    private TextView tvPlaceIntroContent;
    private TextView tvLocationDetail;
    private TextView tvReview;

    private CheckBox chbLike;
    private CheckBox chbToolbarLike;

    private ImageView imgToiletBanned;
    private ImageView imgCookingBanned;
    private ImageView imgStoreBanned;

    private LinearLayout linearNoReview;
    private ConstraintLayout clToolbar;
    private NestedScrollView nsvPlaceDetail;

    private Button btnReviewMore;
    private Button btnBackWhite;
    private Button btnBack;
    private Button btnEdit;
    private Button btnGoReview;

    private RadioButton rbRangeRec;
    private RadioButton rbRangeLatest;

    private RecyclerView rvReview;
    private RecyclerReviewAdapter recyclerReviewAdapter;

    private MapView mapView;

    int likeCnt;
    int reviewCnt;
    int placeIdx;
    int placeStar;

    double reviewAvg;

    String token;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        rvReview = findViewById(R.id.rv_review);

        vpPlaceImage = (ViewPager) findViewById(R.id.vp_place_image);
        nsvPlaceDetail = findViewById(R.id.nsv_place_detail);
        tvImageNum = findViewById(R.id.tv_image_num);
        clToolbar = findViewById(R.id.cl_toolbar);
        linearNoReview = findViewById(R.id.linear_no_review);

        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        tvTitle = findViewById(R.id.tv_title);
        tvPlaceName = findViewById(R.id.tv_place_name);
        tvStar = findViewById(R.id.tv_star);
        tvReviewCount = findViewById(R.id.tv_review_count);
        tvLike = findViewById(R.id.tv_like);
        tvAddress = findViewById(R.id.tv_address);
        tvPlaceIntroContent = findViewById(R.id.tv_place_intro_content);
        tvLocationDetail = findViewById(R.id.tv_location_detail);
        tvReview = findViewById(R.id.tv_review);

        chbLike = findViewById(R.id.chb_like);
        chbToolbarLike = findViewById(R.id.chb_toolbar_like);

        imgToiletBanned = findViewById(R.id.img_toilet_banned);
        imgCookingBanned = findViewById(R.id.img_cooking_banned);
        imgStoreBanned = findViewById(R.id.img_store_banned);

        btnReviewMore = findViewById(R.id.btn_review_more);
        btnBackWhite = findViewById(R.id.btn_back_white);
        btnBack = findViewById(R.id.btn_back);
        btnEdit = findViewById(R.id.btn_edit);
        btnGoReview = findViewById(R.id.btn_go_review);

        rbRangeRec = findViewById(R.id.rb_range_rec);
        rbRangeLatest = findViewById(R.id.rb_range_latest);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.setClipToOutline(true);

        placeIdx = getIntent().getIntExtra("PlaceIdx", 0);
        SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        getPlaceData();
        getPlaceReviewData("like");

        mapView.getMapAsync(this::onMapReady);

        nsvPlaceDetail.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = nsvPlaceDetail.getScrollY();
                if(scrollY > 120) {
                    clToolbar.setVisibility(View.VISIBLE);
                } else {
                    clToolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        chbLike.setOnClickListener(l -> {
            if(chbLike.isChecked()) {
                chabakService.likePlace(token, new RequestLikePlace(placeIdx)).enqueue(new Callback<ResponseLike>() {
                    @Override
                    public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                        if (response.body().getSuccess()) {
                            likeCnt++;
                            tvLike.setText(String.valueOf(likeCnt) +"명이 저장한 차박여행지");
                            chbToolbarLike.setChecked(true);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLike> call, Throwable t) {
                        Log.e("fail","fail");
                    }
                });
            } else {
                chabakService.cancleLikedPlace(token, new RequestLikePlace(placeIdx)).enqueue(new Callback<ResponseLike>() {
                    @Override
                    public void onResponse(Call<ResponseLike> call, Response<ResponseLike> response) {
                        if (response.body().getSuccess()) {
                            likeCnt--;
                            tvLike.setText(String.valueOf(likeCnt) +"명이 저장한 차박여행지");
                            chbToolbarLike.setChecked(false);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLike> call, Throwable t) {

                    }
                });
            }
        });

        chbToolbarLike.setOnClickListener(l -> {
            chbLike.performClick();
        });

        btnEdit.setOnClickListener(l -> {
            Intent intent = new Intent(this, ReviewUploadActivity.class);
            intent.putExtra("placeIdx",placeIdx);
            intent.putExtra("placeTitle", placeDetailData.getPlaceTitle());
            intent.putExtra("placeName", placeDetailData.getPlaceName());
            intent.putExtra("placeImg", placeImagePageAdapter.getThumbnail());
            startActivityForResult(intent, REQUEST_REVIEW_UPLOAD);
        });

        btnGoReview.setOnClickListener(l -> {
            btnEdit.performClick();
        });

        rbRangeRec.setOnClickListener(l -> {
            getPlaceReviewData("like");
        });

        rbRangeLatest.setOnClickListener(l -> {
            getPlaceReviewData("new");
        });

        btnBack.setOnClickListener(l -> {
            finish();
        });

        btnBackWhite.setOnClickListener(l -> {
            finish();
        });

        vpPlaceImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvImageNum.setText(position+1 + " / " + placeDetailData.getPlaceImg().length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getPlaceData() {
        chabakService.getPlaceDetail(token, placeIdx).enqueue(new Callback<ResponsePlaceDetail>() {
            @Override
            public void onResponse(Call<ResponsePlaceDetail> call, Response<ResponsePlaceDetail> response) {
                if(response.body().getSuccess()) {
                    Log.e("성공!","성공!");
                    placeDetailData = response.body().getData();
                    setPlaceDetail(placeDetailData);
                }
            }

            @Override
            public void onFailure(Call<ResponsePlaceDetail> call, Throwable t) {
                Log.e("fail", "fail");
            }
        });
    }

    private void getPlaceReviewData(String order) {
        chabakService.getPlaceReview(token, placeIdx, order).enqueue(new Callback<ResponsePlaceReview>() {
            @Override
            public void onResponse(Call<ResponsePlaceReview> call, Response<ResponsePlaceReview> response) {
                if(response.body().getSuccess()) {
                    placeReviewData = response.body().getData();

                    if(placeReviewData.length > 0) {
                        if(placeReviewData.length > 3) {
                            btnReviewMore.setText(String.valueOf(placeDetailData.getPlaceReviewCnt() -3 + "개 리뷰더보기"));

                            btnReviewMore.setVisibility(View.VISIBLE);
                            btnReviewMore.setOnClickListener(l -> {
                                //ArrayList<PlaceReviewData> reviewList = new ArrayList<PlaceReviewData>(Arrays.asList(placeReviewData));

                                Intent intent = new Intent(PlaceDetailActivity.this, ReviewTotalActivity.class);
                                //intent.putParcelableArrayListExtra("reviews", (ArrayList<? extends Parcelable>) reviewList);
                                intent.putExtra("placeIdx", placeIdx);
                                intent.putExtra("placeTitle", placeDetailData.getPlaceTitle());
                                intent.putExtra("placeName", placeDetailData.getPlaceName());
                                intent.putExtra("placeImg", placeImagePageAdapter.getThumbnail());
                                intent.putExtra("reviewCnt", placeReviewData.length);
                                startActivityForResult(intent, REQUEST_REVIEW_TOTAL);
                            });
                        }
                        linearNoReview.setVisibility(View.GONE);
                        rvReview.setVisibility(View.VISIBLE);

                        setPlaceReview(placeReviewData);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePlaceReview> call, Throwable t) {

            }
        });
    }

    private void setPlaceDetail(PlaceDetailData placeDetailData) {
        placeImagePageAdapter = new PlaceImagePageAdapter(this, placeDetailData.getPlaceImg());
        vpPlaceImage.setAdapter(placeImagePageAdapter);

        if(placeDetailData.getUserLike() == true) {
            chbToolbarLike.setChecked(true);
            chbLike.setChecked(true);
        }
        reviewCnt = placeDetailData.getPlaceReviewCnt();
        reviewAvg = placeDetailData.getPlaceAvgStar();
        placeStar = placeDetailData.getPlaceStar();

        tvToolbarTitle.setText(placeDetailData.getPlaceName());
        tvImageNum.setText("1 / " + placeDetailData.getPlaceImg().length);
        tvTitle.setText(placeDetailData.getPlaceTitle());
        tvPlaceName.setText(placeDetailData.getPlaceName());
        placeStar = placeDetailData.getPlaceStar();
        tvStar.setText(String.valueOf(placeDetailData.getPlaceAvgStar()));
        reviewCnt = placeDetailData.getPlaceReviewCnt();
        tvReviewCount.setText("("+String.valueOf(placeDetailData.getPlaceReviewCnt())+")");
        likeCnt = placeDetailData.getPlaceLikeCnt();
        tvLike.setText(String.valueOf(placeDetailData.getPlaceLikeCnt()) +"명이 저장한 차박여행지");
        tvAddress.setText(placeDetailData.getPlaceAddress());
        tvPlaceIntroContent.setText(placeDetailData.getPlaceContent());
        tvLocationDetail.setText(placeDetailData.getPlaceAddress());
        reviewAvg = placeDetailData.getPlaceAvgStar();
        tvReview.setText(String.valueOf(placeDetailData.getPlaceAvgStar())+"("+String.valueOf(placeDetailData.getPlaceReviewCnt()+")"));

        if(placeDetailData.getPlaceToilet().length == 0) {
            imgToiletBanned.setVisibility(View.VISIBLE);
        }

        if(placeDetailData.getPlaceCooking() == 0) {
            imgCookingBanned.setVisibility(View.VISIBLE);
        }

        if(placeDetailData.getPlaceStore() == 0) {
            imgStoreBanned.setVisibility(View.VISIBLE);
        }
    }

    private void setPlaceReview(PlaceReviewData[] placeReviewData) {
        rvReview = findViewById(R.id.rv_review);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvReview.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvReview.setLayoutManager(linearLayoutManager);

        recyclerReviewAdapter = new RecyclerReviewAdapter();
        rvReview.setAdapter(recyclerReviewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvReview.getContext(), linearLayoutManager.getOrientation());
        rvReview.addItemDecoration(dividerItemDecoration);

        recyclerReviewAdapter.setToken(token);

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

            if(i == 2) {
                break;
            }
        }

        recyclerReviewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_REVIEW_UPLOAD) {
            if (resultCode == RESULT_OK) {
                reviewCnt++;
                placeStar = placeStar + data.getIntExtra("reviewStar", 0);
                reviewAvg = (double) placeStar / (double) reviewCnt;
                reviewAvg = Math.round(reviewAvg * 10) / 10.0;

                tvReviewCount.setText("(" + String.valueOf(reviewCnt) + ")");
                tvStar.setText(String.valueOf(reviewAvg));
                tvReview.setText(String.valueOf(reviewAvg) + "(" + String.valueOf(reviewCnt) + ")");
                rbRangeLatest.performClick();

                UploadReviewSuccessDialog uploadReviewSuccessDialog = new UploadReviewSuccessDialog(PlaceDetailActivity.this);
                uploadReviewSuccessDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                uploadReviewSuccessDialog.show();
            }
        } else if(requestCode == REQUEST_REVIEW_TOTAL) {
            if(resultCode == RESULT_OK) {
                placeStar = placeStar + data.getIntExtra("reviewStar", 0);
                reviewCnt = data.getIntExtra("reviewCnt", 0);
                reviewAvg = (double) placeStar / (double) reviewCnt;
                reviewAvg = Math.round(reviewAvg * 10) / 10.0;

                tvReviewCount.setText("(" + String.valueOf(reviewCnt) + ")");
                tvStar.setText(String.valueOf(reviewAvg));
                tvReview.setText(String.valueOf(reviewAvg) + "(" + String.valueOf(reviewCnt) + ")");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        //현재 위치 버튼 안보이게 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setAllGesturesEnabled(false);

        setMap(naverMap);

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            ArrayList<PlaceToiletData> toiletArray = new ArrayList<PlaceToiletData>(Arrays.asList(placeToiletData));
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                Intent intent = new Intent(mapView.getContext(), MapActivity.class);
                intent.putExtra("placeLatitude", placeDetailData.getPlaceLatitude());
                intent.putExtra("placeLongitude", placeDetailData.getPlaceLongitude());
                intent.putParcelableArrayListExtra("toilets", (ArrayList<? extends Parcelable>) toiletArray);
                startActivity(intent);
            }
        });
    }

    protected void setMap(NaverMap naverMap) {
        double placeLatitude = placeDetailData.getPlaceLatitude();
        double placeLongitude = placeDetailData.getPlaceLongitude();
        placeToiletData = placeDetailData.getPlaceToilet();

        naverMap.setCameraPosition(new CameraPosition(new LatLng(placeLatitude, placeLongitude), 16));

        //화장실 마커
        Marker[] toilets = new Marker[placeToiletData.length];
        for(int i = 0; i<placeToiletData.length; i++) {
            toilets[i] = new Marker();
            toilets[i].setPosition(new LatLng(placeToiletData[i].getToiletLatitude(), placeToiletData[i].getToiletLongitude()));
            toilets[i].setIcon(OverlayImage.fromResource(R.drawable.marker_toilet));
            toilets[i].setWidth(30);
            toilets[i].setHeight(30);
            toilets[i].setMap(naverMap);
        }

        //여행지 마커
        Marker place = new Marker();
        place.setPosition(new LatLng(placeLatitude, placeLongitude));
        place.setIcon(OverlayImage.fromResource(R.drawable.marker_place));
        place.setWidth(90);
        place.setHeight(90);
        place.setMap(naverMap);
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
