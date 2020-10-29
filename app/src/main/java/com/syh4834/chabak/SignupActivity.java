package com.syh4834.chabak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    ImageView imgBack;
    Button btnSignup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imgBack = findViewById(R.id.img_back);
        btnSignup = findViewById(R.id.btn_signup);

        imgBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            finish();
        });

        btnSignup.setOnClickListener(l -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}