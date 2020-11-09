package com.syh4834.chabak;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class ReviewRegisterActivity extends AppCompatActivity {
    private int GALLERY_REQUEST_CODE = 10010;

    ImageView imgPlace;

    private RecyclerView rvReviewUploadImg;
    RecyclerReviewUploadImgData recyclerReviewUploadImgData;
    private RecyclerReviewUploadImgAdapter recyclerReviewUploadImgAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_register);

        imgPlace = findViewById(R.id.img_place);
        imgPlace.setClipToOutline(true);

        recyclerReviewUploadImgAdapter = new RecyclerReviewUploadImgAdapter();

        recyclerInit();

        recyclerReviewUploadImgAdapter.setOnItemClickListener(new RecyclerReviewUploadImgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
                }
            }
        });
    }

    private void recyclerInit() {
        rvReviewUploadImg = findViewById(R.id.rv_review_upload_img);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvReviewUploadImg.getContext(),
                LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };


        rvReviewUploadImg.setLayoutManager(linearLayoutManager);

        recyclerReviewUploadImgAdapter = new RecyclerReviewUploadImgAdapter();
        rvReviewUploadImg.setAdapter(recyclerReviewUploadImgAdapter);

        Uri uri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.camera);
        recyclerReviewUploadImgData = new RecyclerReviewUploadImgData();
        recyclerReviewUploadImgData.setUploadImg(uri);
        recyclerReviewUploadImgAdapter.addItem(recyclerReviewUploadImgData);
        recyclerReviewUploadImgAdapter.notifyItemChanged(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("data값", String.valueOf(data));

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            Uri uri = data.getData();
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri urione = clipData.getItemAt(i).getUri();
                    recyclerReviewUploadImgData = new RecyclerReviewUploadImgData();
                    recyclerReviewUploadImgData.setUploadImg(urione);
                    recyclerReviewUploadImgAdapter.addItem(recyclerReviewUploadImgData);
                }
            }
            recyclerReviewUploadImgAdapter.notifyDataSetChanged();
        }
    }
}