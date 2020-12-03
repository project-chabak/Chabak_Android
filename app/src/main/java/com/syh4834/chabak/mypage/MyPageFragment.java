package com.syh4834.chabak.mypage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.syh4834.chabak.InitialActivity;
import com.syh4834.chabak.R;
import com.syh4834.chabak.report.ReportActivity;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.data.MypageData;
import com.syh4834.chabak.api.response.ResponseMypage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MyPageFragment extends Fragment {
    private TextView tvLogout;
    private TextView tvNickname;
    private TextView tvEmail;
    private TextView tvBirth;
    private TextView tvReport;

    private ImageView imgGender;

    private View mView;
    private Context context;

    private String token;

    private SharedPreferences sharedPreferences;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    public MyPageFragment() {
    }

    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_page, container, false);
        tvLogout = (TextView) mView.findViewById(R.id.tv_logout);
        tvNickname = (TextView) mView.findViewById(R.id.tv_nickname);
        tvReport = (TextView) mView.findViewById(R.id.tv_report);
        tvEmail = mView.findViewById(R.id.tv_email);
        tvBirth = mView.findViewById(R.id.tv_birth);

        imgGender = mView.findViewById(R.id.img_gender);

        context = getActivity();

        sharedPreferences = context.getSharedPreferences("chabak", MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
        Log.e("Mypage token", token);

        chabakService.getMypage(token).enqueue(new Callback<ResponseMypage>() {
            @Override
            public void onResponse(Call<ResponseMypage> call, Response<ResponseMypage> response) {
                if(response.body().getSuccess()) {
                    MypageData mypageData = response.body().getData();
                    tvNickname.setText(mypageData.getNickname() + "님");
                    tvEmail.setText("아이디 : " + mypageData.getId());
                    tvBirth.setText("생년월일 : " + mypageData.getBirthDate());

                    Log.e("mypage", mypageData.getBirthDate());

                    if(mypageData.getGender().equals("M")) {
                        imgGender.setImageResource(R.drawable.man);
                    } else {
                        imgGender.setImageResource(R.drawable.woman);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMypage> call, Throwable t) {

            }
        });

        tvReport.setOnClickListener(l -> {
            Intent intent = new Intent(context, ReportActivity.class);
            startActivity(intent);
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 팝업을 위한 AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("로그아웃 하시겠습니까?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");

                        editor.commit();

                        Intent intent = new Intent(context, InitialActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return mView;
    }
}