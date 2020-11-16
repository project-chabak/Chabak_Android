package com.syh4834.chabak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InitialActivity extends AppCompatActivity {
    Button btnSignup;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        btnSignup = findViewById(R.id.btn_signup);
        btnSignin = findViewById(R.id.btn_signin);

        btnSignup.setOnClickListener(l -> {
            Intent intent = new Intent(this, PlaceDetailActivity.class);
            startActivity(intent);
        });

        btnSignin.setOnClickListener(l -> {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        });
    }
}
