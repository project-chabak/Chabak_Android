package com.syh4834.chabak;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.syh4834.chabak.auth.SigninActivity;
import com.syh4834.chabak.auth.SignupActivity;

public class InitialActivity extends AppCompatActivity {
    Button btnSignup;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_initial);

        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_signup);

        btnSignin.setOnClickListener(l -> {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        });

        btnSignup.setOnClickListener(l -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
