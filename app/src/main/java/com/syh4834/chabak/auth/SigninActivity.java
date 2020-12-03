package com.syh4834.chabak.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.syh4834.chabak.InitialActivity;
import com.syh4834.chabak.MainActivity;
import com.syh4834.chabak.R;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.request.RequestSignin;
import com.syh4834.chabak.api.response.ResponseSignin;
import com.syh4834.chabak.api.data.SigninData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninActivity extends AppCompatActivity {
    EditText edtID;
    EditText edtPW;
    Button btnBack;
    Button btnSignin;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtID = findViewById(R.id.edt_id);
        edtPW = findViewById(R.id.edt_pw);
        btnBack = findViewById(R.id.btn_back);
        btnSignin = findViewById(R.id.btn_signin);


        btnBack.setOnClickListener(l -> {
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
            finish();
        });

        btnSignin.setOnClickListener(l -> {
            String id = edtID.getText().toString();
            String pw = edtPW.getText().toString();
            if(id.isEmpty()) {
                Toast.makeText(SigninActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else if(pw.isEmpty()) {
                Toast.makeText(SigninActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                chabakService.signin(new RequestSignin(id, pw)).enqueue(new Callback<ResponseSignin>() {
                    @Override
                    public void onResponse(Call<ResponseSignin> call, Response<ResponseSignin> response) {
                        Log.e("success", String.valueOf(response.body().getSuccess()));
                        if (response.body().getSuccess()) {
                            SigninData data = response.body().getData();
                            String token = data.getToken();

                            SharedPreferences sharedPreferences = getSharedPreferences("chabak", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.commit();

                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            switch (response.body().getMessage()) {
                                case "존재하지 않는 아이디입니다":
                                    Toast.makeText(SigninActivity.this, "존재하지 않는 아이디입니다", Toast.LENGTH_SHORT).show();
                                    edtID.setText("");
                                    edtID.requestFocus();
                                    break;
                                case "비밀번호가 일치하지 않습니다":
                                    Toast.makeText(SigninActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                    edtPW.setText("");
                                    edtPW.requestFocus();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSignin> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}