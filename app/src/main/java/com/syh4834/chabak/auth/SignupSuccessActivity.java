package com.syh4834.chabak.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.syh4834.chabak.InitialActivity;
import com.syh4834.chabak.R;
import com.syh4834.chabak.auth.SigninActivity;

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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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