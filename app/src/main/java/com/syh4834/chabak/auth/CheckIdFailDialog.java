package com.syh4834.chabak.auth;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.syh4834.chabak.R;

public class CheckIdFailDialog extends Dialog {
    private Button btnOk;
    private Context context;

    private CheckIdFailDialog checkIdFailDialog;
    private CheckIdFailDialogListener checkIdFailDialogListener;

    public CheckIdFailDialog(Context context) {
        super(context);
        this.context = context;
    }

    //인터페이스 설정
    interface CheckIdFailDialogListener {
        void onOkClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CheckIdFailDialogListener checkIdFailDialogListener){
        this.checkIdFailDialogListener = checkIdFailDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_id_fail);

        btnOk = (Button) findViewById(R.id.btn_ok);


        //버튼 클릭 리스너 등록
        btnOk.setOnClickListener(l -> {
            dismiss();
            checkIdFailDialogListener.onOkClicked();
        });
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
}


