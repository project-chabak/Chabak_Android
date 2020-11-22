package com.syh4834.chabak;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.response.ResponseUploadReview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReviewUploadActivity extends AppCompatActivity {
    private int GALLERY_REQUEST_CODE = 10010;

    private ImageView imgPlace;

    private TextView tvPlaceTitle;
    private TextView tvPlaceName;

    private RadioGroup rgReview;

    private EditText edtReviewComment;

    private Button btnReviewUpload;
    private Button btnBack;

    private RecyclerView rvReviewUploadImg;
    private RecyclerReviewUploadImgData recyclerReviewUploadImgData;
    private RecyclerReviewUploadImgAdapter recyclerReviewUploadImgAdapter;

    private String token;
    private int placeIdx;
    private int reviewStar;

    private Bitmap img;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_upload);

        imgPlace = findViewById(R.id.img_place);
        imgPlace.setClipToOutline(true);

        tvPlaceTitle = findViewById(R.id.tv_place_title);
        tvPlaceName = findViewById(R.id.tv_place_name);

        rgReview = findViewById(R.id.rg_review);

        edtReviewComment = findViewById(R.id.edt_review_comment);

        btnReviewUpload = findViewById(R.id.btn_review_upload);
        btnBack = findViewById(R.id.btn_back);

        recyclerReviewUploadImgAdapter = new RecyclerReviewUploadImgAdapter();

        placeIdx = getIntent().getIntExtra("placeIdx", 0);
//        SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
//        token = sharedPreferences.getString("token", null);
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJpZCI6ImlkVGVzdCIsIm5pY2tuYW1lIjoibmlja25hbWUiLCJpYXQiOjE2MDU2NzcyNDZ9.fKU9vfikGOMQ158oW7kyLhTR4ZRZPDMvvmpseFhK8sA";

        tvPlaceTitle.setText(getIntent().getStringExtra("placeTitle"));
        tvPlaceName.setText(getIntent().getStringExtra("placeName"));
        Glide.with(this).load(getIntent().getStringExtra("placeImg")).into(imgPlace);

        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

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

        btnReviewUpload.setOnClickListener(l -> {
            reviewStar = 0;
            switch(rgReview.getCheckedRadioButtonId()) {
                case 2131362146:
                    reviewStar = 1;
                    break;
                case 2131362142:
                    reviewStar = 2;
                    break;
                case 2131362143:
                    reviewStar = 3;
                    break;
                case 2131362144:
                    reviewStar = 4;
                    break;
                case 2131362145:
                    reviewStar = 5;
            }

            if(reviewStar == 0) {
                Toast.makeText(this, "차박여행지에 대한 점수를 표시해주세요", Toast.LENGTH_SHORT).show();
            } else if(edtReviewComment.getText().length() == 0) {
                Toast.makeText(this, "리뷰를 적어주세요", Toast.LENGTH_SHORT).show();
            } else {
                MultipartBody.Part[] imgs = new MultipartBody.Part[recyclerReviewUploadImgAdapter.getItemCount()];
                ArrayList<RecyclerReviewUploadImgData> recyclerReviewUploadImgArray = recyclerReviewUploadImgAdapter.getItem();

                for (int i = 1; i < recyclerReviewUploadImgAdapter.getItemCount(); i++) {
                    File uploadFile = new File(recyclerReviewUploadImgArray.get(i).getUploadImg());
                    InputStream in = null;
                    try {
                        in = getContentResolver().openInputStream(Uri.fromFile(uploadFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    img = BitmapFactory.decodeStream(in);
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, 20, baos);

                    RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"),  baos.toByteArray());

                    imgs[i] = MultipartBody.Part.createFormData("imgs", uploadFile.getName(), imgBody);
                }

                chabakService.uploadReview(token,
                        RequestBody.create(MediaType.parse("text.plain"), String.valueOf(placeIdx)),
                        RequestBody.create(MediaType.parse("text.plain"), edtReviewComment.getText().toString()),
                        RequestBody.create(MediaType.parse("text.plain"), String.valueOf(reviewStar)),
                        imgs).enqueue(new Callback<ResponseUploadReview>() {
                    @Override
                    public void onResponse(Call<ResponseUploadReview> call, Response<ResponseUploadReview> response) {
                        if(response.body().getSuccess()) {
                            Intent intent = new Intent();
                            intent.putExtra("reviewStar", reviewStar);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUploadReview> call, Throwable t) {
                        Log.e("upload", "fail");
                    }
                });
            }
        });
    }


    private String getRealPathFromURI(Uri contentURI) {
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentURI,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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

        String uri = String.format("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.camera);
        recyclerReviewUploadImgData = new RecyclerReviewUploadImgData();
        recyclerReviewUploadImgData.setUploadImg(uri);
        recyclerReviewUploadImgAdapter.addItem(recyclerReviewUploadImgData);
        recyclerReviewUploadImgAdapter.notifyItemChanged(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    String urione = getRealPathFromURI(clipData.getItemAt(i).getUri());

                    recyclerReviewUploadImgData = new RecyclerReviewUploadImgData();
                    recyclerReviewUploadImgData.setUploadImg(urione);
                    recyclerReviewUploadImgAdapter.addItem(recyclerReviewUploadImgData);
                }
            }
            recyclerReviewUploadImgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}



//backPressed 필요하면 추가하기