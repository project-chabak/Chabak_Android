package com.syh4834.chabak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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

        init();
        getData();

    }

    private void init() {
        rvReviewTotal = findViewById(R.id.rv_review_total);

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(rvReviewTotal.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvReviewTotal.setLayoutManager(linearLayoutManager);



        recyclerReviewAdapter = new RecyclerReviewAdapter();
        rvReviewTotal.setAdapter(recyclerReviewAdapter);

        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(rvReviewTotal.getContext(),
                linearLayoutManager.getOrientation());
        //dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_seperator));
        rvReviewTotal.addItemDecoration(dividerItemDecoration);

    }

    private void getData() {
        List<String> listWriter = Arrays.asList("작성자1", "작성자2", "작성자3", "작성자4", "작성자5", "작성자6", "작성자7", "작성자8", "작성자9", "작성자10", "작성자11", "작성자12");

        for(int i = 0; i < listWriter.size();  i++) {
            RecyclerReviewData recyclerReviewData = new RecyclerReviewData();
            recyclerReviewData.setWriter(listWriter.get(i));

            recyclerReviewAdapter.addItem(recyclerReviewData);
        }

        recyclerReviewAdapter.notifyDataSetChanged();
    }
}