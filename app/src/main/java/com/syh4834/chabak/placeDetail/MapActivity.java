package com.syh4834.chabak.placeDetail;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.syh4834.chabak.R;
import com.syh4834.chabak.api.data.PlaceToiletData;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(l -> {
            finish();
        });

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this::onMapReady);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
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
        UiSettings uiSettings = naverMap.getUiSettings();

        uiSettings.setLocationButtonEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);

        setMap(naverMap);
    }

    protected void setMap(NaverMap naverMap) {
        double placeLatitude = getIntent().getDoubleExtra("placeLatitude", 0);
        double placeLongitude = getIntent().getDoubleExtra("placeLongitude", 0);
        ArrayList<PlaceToiletData> toiletArray = getIntent().getParcelableArrayListExtra("toilets");

        Log.e("toilets1", String.valueOf(toiletArray));
        naverMap.setCameraPosition(new CameraPosition(new LatLng(placeLatitude, placeLongitude), 17));

        //화장실 마커
        Marker[] toilets = new Marker[toiletArray.size()];
        for(int i = 0; i < toiletArray.size(); i++) {
            toilets[i] = new Marker();
            toilets[i].setPosition(new LatLng(toiletArray.get(i).getToiletLatitude(),toiletArray.get(i).getToiletLongitude()));
            toilets[i].setIcon(OverlayImage.fromResource(R.drawable.marker_toilet));
            toilets[i].setWidth(50);
            toilets[i].setHeight(50);
            toilets[i].setMap(naverMap);
        }

        //여행지 마커
        Marker place = new Marker();
        place.setPosition(new LatLng(placeLatitude, placeLongitude));
        place.setIcon(OverlayImage.fromResource(R.drawable.marker_place));
        place.setWidth(120);
        place.setHeight(120);
        place.setMap(naverMap);
    }
}