package com.syh4834.chabak.auth;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.syh4834.chabak.R;

public class CheckIdSuccessDialog extends Dialog {
    private Button btnUse;
    private Button btnCancle;
    private Context context;

    private CheckIdSuccessDialog checkIdSuccessDialog;
    private CheckIdSuccessDialogListener checkIdSuccessDialogListener;

    public CheckIdSuccessDialog(Context context) {
        super(context);
        this.context = context;
    }

    //인터페이스 설정
    interface CheckIdSuccessDialogListener {
        void onUseClicked();
        void onCancleClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CheckIdSuccessDialog.CheckIdSuccessDialogListener checkIdSuccessDialogListener){
        this.checkIdSuccessDialogListener = checkIdSuccessDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_id_success);

        btnUse = (Button) findViewById(R.id.btn_use);
        btnCancle = (Button) findViewById(R.id.btn_cancle);

        //버튼 클릭 리스너 등록
        btnUse.setOnClickListener(l -> {
            checkIdSuccessDialogListener.onUseClicked();
            dismiss();
        });
        btnCancle.setOnClickListener(l -> {
            checkIdSuccessDialogListener.onCancleClicked();
            dismiss();
        });
    }

    @Override
    public void onBackPressed() {
        checkIdSuccessDialogListener.onCancleClicked();
    }
}


