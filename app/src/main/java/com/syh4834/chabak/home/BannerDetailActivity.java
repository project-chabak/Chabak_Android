package com.syh4834.chabak.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.syh4834.chabak.R;

public class BannerDetailActivity extends AppCompatActivity {

    private NestedScrollView nsvPlaceDetail;

    private Button btnBackWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_detail);

        // 객체 초기화
        nsvPlaceDetail = findViewById(R.id.nsv_place_detail);
        btnBackWhite = findViewById(R.id.btn_back_white);

        ImageView imgBannerImage = (ImageView) findViewById(R.id.img_banner_image);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        // 선택한 배너 position 값
        int bannerPosition = getIntent().getIntExtra("position", 0);

        // 선택한 배너 position 에 따른 값 세팅
        if(bannerPosition == 0) {
            imgBannerImage.setBackgroundResource(R.drawable.home_banner_image_01);
            tvTitle.setText("차박 여행 시 알아두면 좋은 꿀팁");
            tvContent.setText("1. 2명 이상이 가는 캠핑일 경우 공간을 더 넓게 사용할 수 있는 도킹텐트를 마련해 놓는 것이 좋습니다.\n" +
                    "\n" +
                    "2. 땅이 평평해 보여도 누워보면 기울임이 느껴질 수 있기 때문에 잠자리가 예민하신 분들은 발포 매트나 에어 매트 등을 이용하여 평평하게 만들어 주는 것이 좋습니다.\n" +
                    "\n" +
                    "3. 차박을 할 경우 대부분 텐트를 차량 뒷부분과 도킹하기 위해 트렁크를 올려 놓아야 하는데 이럴 경우 트렁크 보조등은 수동으로 끌 수 없기 때문에 불이 계속 들어오게 됩니다. 이때 등산고리라고 불리는 카라비너를 트렁크 잠김 뭉치 부분에 밀어 넣으면 트렁크 보조등을 끌 수 있습니다. 카라비너를 밀어 넣으면 ‘딱’하고 걸리는 소리가 나는데 이 소리가 두 번 날 때까지 밀어넣으면 됩니다.");
        } else if(bannerPosition == 1) {
            imgBannerImage.setBackgroundResource(R.drawable.home_banner_image_02);
            tvTitle.setText("차박 여행 시 주의사항");
            tvContent.setText("1. 밀폐된 차 안에서 불을 피우거나 에어컨·히터를 과도하게 사용하지 말고, 공기 순환을 위해 차창을 조금 열어 둔다.\n" +
                    "\n" +
                    "2. 강이나 호수 주변 등 침수 위험이 있거나 경사진 곳에 주차하지 않는다. \n" +
                    "\n" +
                    "3. 불을 피울 때에는 불이 옮겨 붙기 쉬운 마른풀, 잔가지, 의류, 일회용품 등이 너무 가까이 있지 않은지 확인하고 화재에 대비하여 소화기를 구비한다.\n" +
                    "\n" +
                    "4. 차 문을 활짝 열어놓고 불을 피울 경우에는 연기가 모두 차 안으로 들어와 악취가 밸 수 있으니 이 점도 유의해야 한다. 이런 경우 사전에 풍향을 체크하고 바람의 영향을 덜 받는 쪽으로 창문을 열어두는 것이 좋다.");
        } else if(bannerPosition == 2) {
            imgBannerImage.setBackgroundResource(R.drawable.home_banner_image_03);
            tvTitle.setText("차박 여행 시 챙겨야 할 준비물");
            tvContent.setText("1.텐트\n" +
                    "\n" +
                    "차박 캠핑은 차 안에서 잠을 자는 캠핑이기 때문에, 텐트가 필수품은 아니지만 한층 더 안락한 캠핑을 원하는 분들이라면 텐트를 준비하시는 게 좋습니다.\n" +
                    "\n" +
                    "2. 화장실 텐트\n" +
                    "\n" +
                    "인근 화장실을 사용하지 못하게 될 경우를 대비하여 간이 화장실을 준비하는 것이 좋습니다.\n" +
                    "\n" +
                    "3. 생활용품\n" +
                    "\n" +
                    "LED 랜턴, 보조배터리, 물티슈, 치약/칫솔, 휴지, 구급약품 등\n" +
                    "\n" +
                    "4. 조리용품\n" +
                    "\n" +
                    "화로대, 스토브, 장갑, 라이터/토치, 집게, 가위, 숯, 테이블, 숟가락/젓가락, 코펠, 다용도 나이프, 물, 쓰레기봉투 등");
        }


        btnBackWhite.setOnClickListener(l -> {
            finish();
        });
    }
}