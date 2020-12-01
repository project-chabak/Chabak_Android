package com.syh4834.chabak.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.syh4834.chabak.R;
import com.syh4834.chabak.api.ChabakService;
import com.syh4834.chabak.api.request.RequestReport;
import com.syh4834.chabak.api.response.ResponseReport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {
    EditText edtReportPlace;
    EditText edtReportAddress;
    CheckBox chbReportToilet;
    CheckBox chbReportStore;
    CheckBox chbReportCooking;
    EditText edtReportDetail;

    Button btnReport;
    Button btnBack;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ChabakService.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ChabakService chabakService = retrofit.create(ChabakService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        edtReportPlace = findViewById(R.id.edt_report_place);
        edtReportAddress = findViewById(R.id.edt_report_address);
        edtReportDetail = findViewById(R.id.edt_report_detail);
        chbReportToilet = findViewById(R.id.chb_report_toilet);
        chbReportStore = findViewById(R.id.chb_report_store);
        chbReportCooking = findViewById(R.id.chb_report_cooking);

        btnReport = (Button) findViewById(R.id.btn_report);
        btnBack = (Button) findViewById(R.id.btn_back);

        btnBack.setOnClickListener(l -> {
            finish();
        });

        btnReport.setOnClickListener(l -> {
            String reportPlace = edtReportPlace.getText().toString();
            String reportAddress = edtReportAddress.getText().toString();
            String reportDetail = edtReportDetail.getText().toString();

            String reportToilet = "0";
            String reportStore = "0";
            String reportCooking = "0";

            if (chbReportToilet.isChecked()) {
                reportToilet = "1";
            }

            if (chbReportStore.isChecked()) {
                reportStore = "1";
            }

            if (chbReportCooking.isChecked()) {
                reportCooking = "1";
            }

            if (reportPlace.isEmpty() || reportAddress.isEmpty() || reportDetail.isEmpty()) {
                Toast.makeText(this, "제보하기 내용을 모두 채워주세요", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("chabak", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);
                chabakService.report(token, new RequestReport(reportPlace, reportAddress, reportDetail, reportToilet, reportStore, reportCooking)).enqueue(new Callback<ResponseReport>() {
                    @Override
                    public void onResponse(Call<ResponseReport> call, Response<ResponseReport> response) {
                        if (response.body().getSuccess()) {
                            Toast.makeText(ReportActivity.this, "성공적으로 제보되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ReportActivity.this, "죄송합니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseReport> call, Throwable t) {

                    }
                });
            }
        });
    }
}