package com.syh4834.chabak;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.PlaceLikeData;
import com.syh4834.chabak.api.data.PlaceListData;
import com.syh4834.chabak.api.response.ResponsePlaceLike;
import com.syh4834.chabak.api.response.ResponsePlaceList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeFragment extends Fragment {
    private PlaceListData placeListData;
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ImageView empty;

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
        empty = (ImageView)view.findViewById(R.id.empty_image);
// sharedPreferences 값으로 사용자의 토큰을 얻어온다.
//        SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
//        token = sharedPreferences.getString("token", null);
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJpZCI6ImlkIiwibmlja25hbWUiOiIxMjMiLCJpYXQiOjE2MDQ5NzMxMDN9.80OjSRBho8176t0BgYu5tuEZ5pJGBh_tCjVn_Nsic_I"; // 임시 토큰
        DataInit(view);
        return view;
    }
    private void DataInit(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.like_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerListAdapter();
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

        int rateImageView = R.drawable.star;
        for (int i = 0; i < placeLikeData.length; i++) {
            RecyclerListData data = new RecyclerListData();
            data.setTitle(placeLikeData[i].getPlaceTitle());
            data.setContent(placeLikeData[i].getPlaceAddress());
            data.setGetRateText(placeLikeData[i].getPlaceAvgStar());
            data.setRateImageView(rateImageView);
            data.setContentImageView(placeLikeData[i].getPlaceThumbnail());
            adapter.addItem(data);
        }
        adapter.notifyDataSetChanged();
    }
}