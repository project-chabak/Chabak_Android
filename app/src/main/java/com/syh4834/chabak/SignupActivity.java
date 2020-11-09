package com.syh4834.chabak;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    Button btnBack;
    Button btnSignup;
    EditText edtNickname;
    EditText edtId;
    EditText edtPw;
    EditText edtPwCheck;
    RadioGroup rgGender;
    EditText edtBirth;
    TextView tvPwCheckError;
    ImageView imgPwCheck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnBack = findViewById(R.id.btn_back);
        btnSignup = findViewById(R.id.btn_signup);
        edtNickname = findViewById(R.id.edt_nickname);
        edtId = findViewById(R.id.edt_id);
        edtPw = findViewById(R.id.edt_pw);
        edtPwCheck = findViewById(R.id.edt_pw_check);
        rgGender = findViewById(R.id.rg_gender);
        edtBirth = findViewById(R.id.edt_birth);
        tvPwCheckError = findViewById(R.id.tv_pw_check_error);
        imgPwCheck = findViewById(R.id.img_pw_check);

        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            finish();
        });

        edtPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtPw.getText().toString().equals(edtPwCheck.getText().toString())) {
                    tvPwCheckError.setVisibility(View.GONE);
                    imgPwCheck.setVisibility(View.VISIBLE);
                } else {
                    imgPwCheck.setVisibility(View.GONE);
                    tvPwCheckError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnSignup.setOnClickListener(l -> {
            int gender = 0;
            String nickname = edtNickname.getText().toString();
            String id = edtId.getText().toString();
            String pw = edtPw.getText().toString();
            String pwCheck = edtPwCheck.toString();
            gender = rgGender.getCheckedRadioButtonId();
            String birth = edtBirth.toString();

            if (nickname.isEmpty() || id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || gender == 0 || birth.isEmpty()) {
                Toast.makeText(this, "회원가입 조건에 맞게 모두 채워주세요", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
