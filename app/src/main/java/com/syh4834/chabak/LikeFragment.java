package com.syh4834.chabak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceLikeData;
import com.syh4834.chabak.api.data.PlaceListData;
import com.syh4834.chabak.api.response.ResponsePlaceLike;
import com.syh4834.chabak.placeDetail.PlaceDetailActivity;
import com.syh4834.chabak.review.recycler.RecyclerReviewUploadImgAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeFragment extends Fragment {
    private PlaceListData placeListData;
    private RecyclerView recyclerView;
    private RecyclerLikeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<Integer> placeIdxList = new ArrayList<>();

    ConstraintLayout empty;

    // retrofit 객체 생성
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        empty = (ConstraintLayout) view.findViewById(R.id.empty_image);
// sharedPreferences 값으로 사용자의 토큰을 얻어온다.
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("chabak", getContext().MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
        DataInit(view);
        adapter.setOnItemClickListener(new RecyclerReviewUploadImgAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                int pos = placeIdxList.get(position);
                //Log.e("포지션",String.valueOf(pos));
                Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
                intent.putExtra("PlaceIdx", pos); // position부분을 수정해야 함
                startActivityForResult(intent,0);
            }
        });
        return view;
    }
    private void DataInit(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.like_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerLikeAdapter();
        chabakService.getPlaceLike(token).enqueue(new Callback<ResponsePlaceLike>() {
            @Override
            public void onResponse(Call<ResponsePlaceLike> call, Response<ResponsePlaceLike> response) {
                if(response.body().getSuccess()) {
                    Log.e("성공!","성공!");
                    if(response.body().getData()==null){
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                    else{
                        empty.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.listData.clear();
                        PlaceLikeData[] placeLikeData = response.body().getData();
                        getData(placeLikeData);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponsePlaceLike> call, Throwable t) {
                Log.e("fail", "fail");
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getData(PlaceLikeData[] placeLikeData) {
        placeIdxList.clear();
        int rateImageView = R.drawable.star;
        for (int i = 0; i < placeLikeData.length; i++) {
            RecyclerListData data = new RecyclerListData();
            data.setTitle(placeLikeData[i].getPlaceTitle());
            data.setContent(placeLikeData[i].getPlaceAddress());
            data.setGetRateText(placeLikeData[i].getPlaceAvgStar());
            data.setRateImageView(rateImageView);
            data.setContentImageView(placeLikeData[i].getPlaceThumbnail());
            placeIdxList.add(placeLikeData[i].getPlaceIdx());
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if (resultCode == 0) {
                DataInit(getView());
                adapter.setOnItemClickListener(new RecyclerReviewUploadImgAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View v, int position) {
                        int pos = placeIdxList.get(position);
                        //Log.e("포지션",String.valueOf(pos));
                        Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
                        intent.putExtra("PlaceIdx", pos); // position부분을 수정해야 함
                        startActivityForResult(intent,0);
                    }
                });
            }
        }
    }
}