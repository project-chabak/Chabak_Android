package com.syh4834.chabak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class SignupSuccessActivity extends AppCompatActivity {
    Button btnGoSignin;
    ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);

        btnGoSignin = findViewById(R.id.btn_go_signin);
        btnClose = findViewById(R.id.btn_close);

        btnClose.setOnClickListener(l -> {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            finishAffinity();
        });
        btnGoSignin.setOnClickListener(l -> {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        btnClose.performClick();
    }
}