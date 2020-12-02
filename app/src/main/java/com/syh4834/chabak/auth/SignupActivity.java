package com.syh4834.chabak.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.syh4834.chabak.InitialActivity;
import com.syh4834.chabak.MainActivity;
import com.syh4834.chabak.R;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.request.RequestSignup;
import com.syh4834.chabak.api.response.ResponseSignup;
import com.syh4834.chabak.api.response.ResponseSignupCheckID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    Button btnBack;
    Button btnSignup;
    Button btnIdCheck;
    EditText edtNickname;
    EditText edtId;
    EditText edtPw;
    EditText edtPwCheck;
    RadioGroup rgGender;
    EditText edtBirth;
    TextView tvIdEmptyError;
    TextView tvPwCheckError;
    ImageView imgIdCheck;
    ImageView imgPwCheck;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnBack = findViewById(R.id.btn_back);
        btnSignup = findViewById(R.id.btn_signup);
        btnIdCheck = findViewById(R.id.btn_id_check);
        edtNickname = findViewById(R.id.edt_nickname);
        edtId = findViewById(R.id.edt_id);
        edtPw = findViewById(R.id.edt_pw);
        edtPwCheck = findViewById(R.id.edt_pw_check);
        rgGender = findViewById(R.id.rg_gender);
        edtBirth = findViewById(R.id.edt_birth);
        tvIdEmptyError = findViewById(R.id.tv_id_empty_error);
        tvPwCheckError = findViewById(R.id.tv_pw_check_error);
        imgIdCheck = findViewById(R.id.img_id_check);
        imgPwCheck = findViewById(R.id.img_pw_check);

        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            finish();
        });

        btnIdCheck.setOnClickListener(l -> {
            String id = edtId.getText().toString();
            Log.e("버튼", id);

            if(!id.isEmpty()) {
                chabakService.getCheckID(id).enqueue(new Callback<ResponseSignupCheckID>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseSignupCheckID> call, @NonNull Response<ResponseSignupCheckID> response) {
                        if (response.body().getSuccess()) {
                            Log.e("통신", "사용가능");
                            CheckIdSuccessDialog checkIdSuccessDialog = new CheckIdSuccessDialog(SignupActivity.this);
                            checkIdSuccessDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            checkIdSuccessDialog.setDialogListener(new CheckIdSuccessDialog.CheckIdSuccessDialogListener() {
                                @Override
                                public void onUseClicked() {
                                    edtId.setEnabled(false);
                                    btnIdCheck.setEnabled(false);
                                    tvIdEmptyError.setVisibility(View.INVISIBLE);
                                    imgIdCheck.setVisibility(View.VISIBLE);
                                    edtPw.requestFocus();
                                }

                                @Override
                                public void onCancleClicked() {
                                    tvIdEmptyError.setVisibility(View.INVISIBLE);
                                    edtId.setText("");
                                    edtId.requestFocus();
                                }
                            });
                            checkIdSuccessDialog.show();
                        } else {
                            CheckIdFailDialog checkIdFailDialog = new CheckIdFailDialog(SignupActivity.this);
                            checkIdFailDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            checkIdFailDialog.setDialogListener(new CheckIdFailDialog.CheckIdFailDialogListener() {
                                @Override
                                public void onOkClicked() {
                                    edtId.setText("");
                                    edtId.requestFocus();
                                }
                            });
                            checkIdFailDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseSignupCheckID> call, @NonNull Throwable t) {
                        Log.e("통신", "실패");
                    }
                });
            } else {
                tvIdEmptyError.setVisibility(View.VISIBLE);
            }
        });

        edtPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtPw.getText().toString().equals(edtPwCheck.getText().toString())) {
                    tvPwCheckError.setVisibility(View.INVISIBLE);
                    imgPwCheck.setVisibility(View.VISIBLE);
                } else {
                    imgPwCheck.setVisibility(View.INVISIBLE);
                    tvPwCheckError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnSignup.setOnClickListener(l -> {
            int genderID = 0;
            String gender = "";
            String nickname = edtNickname.getText().toString();
            String id = edtId.getText().toString();
            String pw = edtPw.getText().toString();
            String pwCheck = edtPwCheck.toString();
            genderID = rgGender.getCheckedRadioButtonId();
            RadioButton genderValue = findViewById(genderID);

            if(genderValue.getText().toString().equals("남성")) {
                gender = "M";
            } else {
                gender = "F";
            }
            String birth = edtBirth.getText().toString();
            Log.e("birth", birth);

            if (nickname.isEmpty() || id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || genderID == -1 || birth.isEmpty()) {
                Toast.makeText(this, "회원가입 조건에 맞게 모두 채워주세요", Toast.LENGTH_SHORT).show();
            } else {
                if(edtId.isInEditMode() == true) {
                    edtId.requestFocus();
                    Toast.makeText(this, "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                } else if(imgPwCheck.getVisibility() == View.INVISIBLE) {
                    edtPwCheck.requestFocus();
                    Toast.makeText(this, "비밀번호 확인을 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    chabakService.signup(new RequestSignup(id, pw, nickname, gender, birth)).enqueue(new Callback<ResponseSignup>() {
                        @Override
                        public void onResponse(Call<ResponseSignup> call, Response<ResponseSignup> response) {
                            if (response.body().getSuccess()) {
                                Intent intent = new Intent(SignupActivity.this, SignupSuccessActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseSignup> call, Throwable t) {

                        }
                    });
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
