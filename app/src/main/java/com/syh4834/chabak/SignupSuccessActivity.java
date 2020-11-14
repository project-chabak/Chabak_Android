package com.syh4834.chabak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignupSuccessActivity extends AppCompatActivity {
    Button btnGoSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);

        btnGoSignin = findViewById(R.id.btn_go_signin);

        btnGoSignin.setOnClickListener(l -> {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
            finish();
        });
    }
}